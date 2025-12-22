package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stores", uniqueConstraints = {
        @UniqueConstraint(columnNames = "storeName")
})
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String storeName;

    private String address;
    private String region;

    private Boolean active = true;

    // Getters and Setters
}
