package com.example.shop_backend.warenkorb;


import jakarta.persistence.*;

@Entity
public class Warenkorb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "size", nullable = false)
    private Integer size;



    @Column( name = "client_id")
    private String clientId;




    public Warenkorb() {}

    public Warenkorb(Long id) {
        this.id = id;
    }



    public Long getId() {
        return id;
    }
    public Long getProductId() {
        return productId;
    }
    public Integer getSize() {
        return size;
    }

    public String getClientId() {
        return clientId;
    }




    public void setSize(Integer size) {
        this.size = size;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }





}
