package com.example.shop_backend.paypal;

import java.util.List;

public interface CartService {
    record CartItem(Long productId, Integer quantity) {
    }
    List<CartItem> getCartItemsForClient(String clientId);
    void clearClientCart(String clientId);
}
