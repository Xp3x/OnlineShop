package com.example.shop_backend.product;


import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String brand;

    @Column(nullable=false)
    private BigDecimal price;


    @Column(name="image_url")
    private String imageUrl;

    @Column(nullable=false)
    private float stack_heel_mm;

    @Column(nullable=false)
    private float stack_forefoot_mm;

    @Column(nullable=false)
    private float drop_mm;

    @Column(nullable=false)
    private int weight_g;

    @Column(nullable=false)
    private String surface;

    @Column(name= "groesse", columnDefinition ="integer[]") //JPA sagen welche Spalte gemeint ist und erzwingt Java-Array
    @JdbcTypeCode(SqlTypes.ARRAY) //behandlung als SQL-Array
    private Integer[] groesse;

    @Column(name="beschreibung", columnDefinition = "text")
    private String beschreibung;

    @Column(name="angebot")
    private Boolean angebot;

    public Product() {}

    public Product(String name, String brand, BigDecimal price, String imageUrl,
                   float stack_heel_mm, float stack_forefoot_mm, float drop_mm, int weight_g, String surface,  Integer[] groesse, String beschreibung, Boolean angebot) {
        this.name = name;
        this.brand = brand;
        this.price = price;

        this.imageUrl = imageUrl;
        this.stack_heel_mm = stack_heel_mm;
        this.stack_forefoot_mm = stack_forefoot_mm;
        this.drop_mm = drop_mm;
        this.weight_g = weight_g;
        this.surface = surface;
        this.groesse = groesse;
        this.beschreibung = beschreibung;
        this.angebot = angebot;
    }



    // Getter & Setter
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public BigDecimal getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public float getStack_heel_mm() { return stack_heel_mm; }
    public float getStack_forefoot_mm() { return stack_forefoot_mm; }
    public float getDrop_mm() { return drop_mm; }
    public int getWeight_g() { return weight_g; }
    public String getSurface() { return surface; }
    public Integer[] getGroesse() { return groesse; }
    public String getBeschreibung() { return beschreibung; }
    public Boolean getAngebot() { return angebot; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setStack_heel_mm(float stack_heel_mm) {
        this.stack_heel_mm = stack_heel_mm;
    }
    public void setStack_forefoot_mm(float stack_forefoot_mm) {
        this.stack_forefoot_mm = stack_forefoot_mm;
    }
    public void setDrop_mm(float drop_mm) {
        this.drop_mm = drop_mm;
    }
    public void setWeight_g(int weight_g) { this.weight_g = weight_g; }
    public void setSurface(String surface) { this.surface = surface; }
    public void setGroesse(Integer[] groesse) { this.groesse = groesse; }
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    public void setAngebot(Boolean angebot) { this.angebot = angebot; }
}
