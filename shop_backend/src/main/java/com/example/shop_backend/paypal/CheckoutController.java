package com.example.shop_backend.paypal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;


@RestController
@RequestMapping("/api/checkout/paypal")
public class CheckoutController {

    private final PaymentService payments;

    public CheckoutController(PaymentService payments) { this.payments = payments; }

    @PostMapping("/create")
    public Map<String,String> create(@RequestHeader("X-Client-Id") String clientId) {
        String url = payments.startPayPalCheckout(clientId);
        return Map.of("url", url);
    }

    @GetMapping("/return")
    public ResponseEntity<Void> onReturn(@RequestParam("token") String paypalOrderId,
                                         @RequestParam("oid") long orderId) {
        URI redirect = payments.finalizePayPalReturn(paypalOrderId, orderId);
        HttpHeaders h = new HttpHeaders();
        h.setLocation(redirect);
        return new ResponseEntity<>(h, HttpStatus.FOUND);
    }

    @GetMapping("/cancel")
    public ResponseEntity<Void> onCancel(@RequestParam("oid") long orderId) {
        HttpHeaders h = new HttpHeaders();
        h.setLocation(URI.create("/cancel?orderId=" + orderId));
        return new ResponseEntity<>(h, HttpStatus.FOUND);
    }
}
