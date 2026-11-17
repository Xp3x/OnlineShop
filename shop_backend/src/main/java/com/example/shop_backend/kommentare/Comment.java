package com.example.shop_backend.kommentare;


import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="comment", columnDefinition = "text")
    private String comment;

    @Column( name = "client_id")
    private String clientId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name="username")
    private String username;

    public Comment() {}

    public Comment(Long id,String comment, String clientId, Long productId, String username) {
        this.comment = comment;
        this.clientId = clientId;
        this.id = id;
        this.productId = productId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}

