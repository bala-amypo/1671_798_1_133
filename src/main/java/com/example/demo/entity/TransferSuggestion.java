package com.example.demo.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transfer_suggestions")
public class TransferSuggestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "source_store_id", nullable = false)
    private Store sourceStore;
    
    @ManyToOne
    @JoinColumn(name = "target_store_id", nullable = false)
    private Store targetStore;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    private Integer quantity;
    
    @Enumerated(EnumType.STRING)
    private PriorityLevel priority;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date suggestedAt;
    
    @Enumerated(EnumType.STRING)
    private SuggestionStatus status = SuggestionStatus.PENDING;
    
    // Lifecycle callback
    @PrePersist
    protected void onCreate() {
        this.suggestedAt = new Date();
    }
    
    // Constructors
    public TransferSuggestion() {}
    
    public TransferSuggestion(Store sourceStore, Store targetStore, Product product, Integer quantity, PriorityLevel priority) {
        this.sourceStore = sourceStore;
        this.targetStore = targetStore;
        this.product = product;
        this.quantity = quantity;
        this.priority = priority;
    }
    
    // Enums
    public enum PriorityLevel {
        HIGH, MEDIUM, LOW
    }
    
    public enum SuggestionStatus {
        PENDING, APPROVED, REJECTED
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Store getSourceStore() { return sourceStore; }
    public void setSourceStore(Store sourceStore) { this.sourceStore = sourceStore; }
    
    public Store getTargetStore() { return targetStore; }
    public void setTargetStore(Store targetStore) { this.targetStore = targetStore; }
    
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public PriorityLevel getPriority() { return priority; }
    public void setPriority(PriorityLevel priority) { this.priority = priority; }
    
    public Date getSuggestedAt() { return suggestedAt; }
    public void setSuggestedAt(Date suggestedAt) { this.suggestedAt = suggestedAt; }
    
    public SuggestionStatus getStatus() { return status; }
    public void setStatus(SuggestionStatus status) { this.status = status; }
}