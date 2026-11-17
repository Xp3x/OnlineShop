package com.example.shop_backend.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name= "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String email;

    @Column(nullable=false, length=100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    private Boolean onlineStatus;

    @Column(name="client_id",  nullable=false, unique=true)
    private String clientId;

    @Column(name="admin")
    private Boolean admin;

    public User(){}

    public User(Long id, String name, String email, String password, Boolean onlineStatus, String clientId, Boolean admin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.onlineStatus = onlineStatus;
        this.clientId = clientId;
        this.admin = admin;

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getOnlineStatus() {
        return onlineStatus;
    }
    public void setOnlineStatus(Boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public Boolean getAdmin() {
        return admin;
    }
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

}
