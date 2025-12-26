package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"store_id", "product_id"})
})
public class InventoryLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void updateTime() {
        lastUpdated = LocalDateTime.now();
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }
 
    public void setStore(Store store) {
        this.store = store;
    }
 
    public Product getProduct() {
        return product;
    }
 
    public void setProduct(Product product) {
        this.product = product;
    }
 
    public Integer getQuantity() {
        return quantity;
    }
 
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
 
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
}
