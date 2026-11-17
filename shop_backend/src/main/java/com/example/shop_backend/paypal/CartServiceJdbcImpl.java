package com.example.shop_backend.paypal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CartServiceJdbcImpl implements CartService {

    private final JdbcTemplate jdbcTemplate;

    public CartServiceJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<CartItem> getCartItemsForClient(String clientId) {

        String sql= """
                SELECT product_id, COUNT(*) AS quantity
                FROM warenkorb
                where client_id = ?
                GROUP BY product_id
                
                
                """;

        return jdbcTemplate.query(sql, (rs, i) -> new CartItem(
                rs.getLong("product_id"),
                rs.getInt("quantity")
        ), clientId);
    }


    public void clearClientCart(String clientId) {
        jdbcTemplate.update("DELETE FROM warenkorb where client_id = ?", clientId);
    }
}
