package com.example.shop_backend.paypal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderItemRepository extends JpaRepository<ShopOrderItem, Long> {
}
