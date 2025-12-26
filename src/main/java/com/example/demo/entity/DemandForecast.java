package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DemandForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Product product;

    private Integer forecastedDemand;
    private LocalDate forecastDate;

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
 
    public Integer getForecastedDemand() {
        return forecastedDemand;
    }
 
    public void setForecastedDemand(Integer forecastedDemand) {
        this.forecastedDemand = forecastedDemand;
    }
 
    public LocalDate getForecastDate() {
        return forecastDate;
    }
 
    public void setForecastDate(LocalDate forecastDate) {
        this.forecastDate = forecastDate;
    }
}
