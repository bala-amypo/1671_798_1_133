package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "inventory_levels")
public class InventoryLevel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    
    // Lifecycle callbacks
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = new Date();
    }
    
    // Constructors
    public InventoryLevel() {}
    
    public InventoryLevel(Store store, Product product, Integer quantity) {
        this.store = store;
        this.product = product;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }
    
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Date getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }
}