package com.example.shop_backend.paypal;


import jakarta.persistence.*;

@Entity
@Table(name="shop_order_item")
public class ShopOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_id")
    private Long orderId;

    @Column(name="product_id")
    private Long productId;

    @Column(name="name_snapshot")
    private String nameSnapshot;

    @Column(name="unit_amount_cents")
    private Long unitAmountCents;

    private Integer quantity;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getNameSnapshot() {
        return nameSnapshot;
    }
    public void setNameSnapshot(String nameSnapshot) {
        this.nameSnapshot = nameSnapshot;
    }
    public Long getUnitAmountCents() {
        return unitAmountCents;
    }
    public void setUnitAmountCents(Long unitAmountCents) {
        this.unitAmountCents = unitAmountCents;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
