package com.example.shop_backend.paypal;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name="shop_order")
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="client_id")
    private String clientId;

    private String status;

    private String currency;

    @Column(name="total_amount_cents")
    private Long totalAmountCents;

    @Column(name="paypal_order_id")
    private String paypalOrderId;

    @Column(name="paypal_capture_id")
    private String paypalCaptureId;


    @Column(columnDefinition ="timestamp with time zone", name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(columnDefinition ="timestamp with time zone", name = "updated_at")
    private Instant updatedAt = Instant.now();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Long getTotalAmountCents() {
        return totalAmountCents;
    }
    public void setTotalAmountCents(Long totalAmountCents) {
        this.totalAmountCents = totalAmountCents;
    }
    public String getPaypalOrderId() {
        return paypalOrderId;
    }
    public void setPaypalOrderId(String paypalOrderId) {
        this.paypalOrderId = paypalOrderId;
    }
    public String getPaypalCaptureId() {
        return paypalCaptureId;
    }
    public void setPaypalCaptureId(String paypalCaptureId) {
        this.paypalCaptureId = paypalCaptureId;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }


}

