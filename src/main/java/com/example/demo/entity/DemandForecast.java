package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "demand_forecasts")
public class DemandForecast {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
    
    @Column(nullable = false)
    private LocalDate forecastDate;
    
    @Column(nullable = false)
    private Integer predictedDemand;
    
    private Double confidenceScore;
    
    // Constructors
    public DemandForecast() {}
    
    public DemandForecast(Product product, Store store, LocalDate forecastDate, Integer predictedDemand) {
        this.product = product;
        this.store = store;
        this.forecastDate = forecastDate;
        this.predictedDemand = predictedDemand;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    
    public Store getStore() { return store; }
    public void setStore(Store store) { this.store = store; }
    
    public LocalDate getForecastDate() { return forecastDate; }
    public void setForecastDate(LocalDate forecastDate) { this.forecastDate = forecastDate; }
    
    public Integer getPredictedDemand() { return predictedDemand; }
    public void setPredictedDemand(Integer predictedDemand) { this.predictedDemand = predictedDemand; }
    
    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }
}