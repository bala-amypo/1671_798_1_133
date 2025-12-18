package com.example.demo.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products", uniqueConstraints = {
    @UniqueConstraint(columnNames = "sku")
})
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String sku;
    
    @Column(nullable = false)
    private String name;
    
    private String category;
    
    private Boolean active = true;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<InventoryLevel> inventoryLevels = new HashSet<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<DemandForecast> demandForecasts = new HashSet<>();
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<TransferSuggestion> transferSuggestions = new HashSet<>();
    
    // Constructors
    public Product() {}
    
    public Product(String sku, String name, String category) {
        this.sku = sku;
        this.name = name;
        this.category = category;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}