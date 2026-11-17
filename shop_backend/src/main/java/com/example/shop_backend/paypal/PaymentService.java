

package com.example.shop_backend.paypal;

import com.example.shop_backend.product.ProductRepository;

import org.springframework.stereotype.Service;
import com.example.shop_backend.paypal.CheckoutController;
import java.math.BigDecimal;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    private final CartService cart;
    private final ShopOrderRepository orderRepo;
    private final ShopOrderItemRepository itemRepo;
    private final ProductRepository productRepo;
    private final PayPalClient paypal;
    private final PaypalProperties props;

    public PaymentService(CartService cart,ShopOrderRepository orderRepo,  ShopOrderItemRepository itemRepo, ProductRepository productRepo, PayPalClient paypal, PaypalProperties props ) {
        this.cart=cart;
        this.orderRepo=orderRepo;
        this.itemRepo=itemRepo;
        this.productRepo=productRepo;
        this.paypal=paypal;
        this.props=props;
    }

    public String startPayPalCheckout(String clientId) {
        List<CartService.CartItem> items = cart.getCartItemsForClient(clientId);
        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Warenkorb ist leer.");
        }

        java.util.List<ShopOrderItem> preparedItems = new java.util.ArrayList<>();
        long totalCents = 0L;

        for (var it : items) {
            var p = productRepo.findById(it.productId()).orElseThrow();


            long unitCents = toCents(p.getPrice());
            totalCents += unitCents * it.quantity();

            var oi = new ShopOrderItem();
            oi.setProductId(p.getId());
            oi.setNameSnapshot(p.getName());
            oi.setUnitAmountCents(unitCents);
            oi.setQuantity(it.quantity());
            preparedItems.add(oi);
        }

        ShopOrder o = new ShopOrder();
        o.setClientId(clientId);
        o.setStatus("CREATED");
        o.setCurrency("EUR");
        o.setTotalAmountCents(totalCents);
        o.setCreatedAt(Instant.now());
        o.setUpdatedAt(Instant.now());
        orderRepo.save(o);

        for (var oi : preparedItems) {
            oi.setOrderId(o.getId());
            itemRepo.save(oi);
        }

        String value = java.math.BigDecimal
                .valueOf(totalCents, 2)
                .setScale(2, java.math.RoundingMode.HALF_UP)
                .toPlainString();

        String returnUrl = paypal.getAppBaseUrl() + "/api/checkout/paypal/return?oid=" + o.getId();
        String cancelUrl = paypal.getAppBaseUrl() + "/api/checkout/paypal/cancel?oid=" + o.getId();

        Map<String, Object> resp = paypal.createOrder(returnUrl, cancelUrl, "EUR", value, "ORDER" + o.getId());
        String ppOrderId = String.valueOf(resp.get("id"));

        o.setPaypalOrderId(ppOrderId);
        o.setUpdatedAt(Instant.now());
        orderRepo.save(o);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> links = (List<Map<String, Object>>) resp.get("links");
        return links.stream()
                .filter(l -> "approve".equals(l.get("rel")))
                .findFirst()
                .map(l -> String.valueOf(l.get("href")))
                .orElseThrow();
    }


    private static long toCents(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException("Preis darf nicht null sein");
        return amount
                .movePointRight(2)                     // *100
                .setScale(0, java.math.RoundingMode.HALF_UP)
                .longValueExact();
    }



    public URI finalizePayPalReturn(String paypalOrderId, long orderId) {
        var capture = paypal.captureOrder(paypalOrderId);
        String status = String.valueOf(capture.get("status"));
        String captureId = extractCaptureId(capture);

        var o = orderRepo.findById(orderId).orElseThrow();
        if ("COMPLETED".equalsIgnoreCase(status)) {
            o.setStatus("PAID");
            o.setPaypalCaptureId(captureId);
            o.setUpdatedAt(Instant.now());
            orderRepo.save(o);
            cart.clearClientCart(o.getClientId());

            return URI.create(props.getUiBaseUrl() + "/success?orderId=" + orderId);
        } else {
            o.setStatus("FAILED");
            o.setUpdatedAt(Instant.now());
            orderRepo.save(o);
            return URI.create(props.getUiBaseUrl() + "/cancel?orderId=" + orderId);
        }
    }

    @SuppressWarnings("unchecked")
    private String extractCaptureId(Map<String,Object> capture) {
        try {
            var pu       = (Map<String,Object>) ((java.util.List<?>) capture.get("purchase_units")).get(0);
            var payments = (Map<String,Object>) pu.get("payments");
            var caps     = (java.util.List<java.util.Map<String,Object>>) payments.get("captures");
            return String.valueOf(caps.get(0).get("id"));
        } catch (Exception e) {
            return null;
        }
    }


}
