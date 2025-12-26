package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransferSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Store sourceStore;

    @ManyToOne
    private Store targetStore;

    private Integer suggestedQuantity;
    private String reason;
    private LocalDateTime generatedAt;

    @PrePersist
    public void prePersist() {
        generatedAt = LocalDateTime.now();
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
 
    public void setProduct(Product product) {
        this.product = product;
    }
 
    public Store getSourceStore() {
        return sourceStore;
    }
 
    public void setSourceStore(Store sourceStore) {
        this.sourceStore = sourceStore;
    }
 
    public Store getTargetStore() {
        return targetStore;
    }
 
    public void setTargetStore(Store targetStore) {
        this.targetStore = targetStore;
    }
 
    public Integer getSuggestedQuantity() {
        return suggestedQuantity;
    }
 
    public void setSuggestedQuantity(Integer suggestedQuantity) {
        this.suggestedQuantity = suggestedQuantity;
    }
 
    public String getReason() {
        return reason;
    }
 
    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
}
