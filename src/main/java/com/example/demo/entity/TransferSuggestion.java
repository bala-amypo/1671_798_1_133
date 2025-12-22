package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer_suggestions")
public class TransferSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Store sourceStore;

    @ManyToOne
    private Store targetStore;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private String priority;
    private String status;

    private LocalDateTime suggestedAt;

    @PrePersist
    public void prePersist() {
        suggestedAt = LocalDateTime.now();
        if (status == null) {
            status = "PENDING";
        }
    }

    // Getters and Setters
}
