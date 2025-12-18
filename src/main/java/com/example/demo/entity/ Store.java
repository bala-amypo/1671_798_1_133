package com.example.demo.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stores", uniqueConstraints = {
    @UniqueConstraint(columnNames = "storeName")
})
public class Store {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String storeName;
    
    private String address;
    
    private String region;
    
    private Boolean active = true;
    
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<InventoryLevel> inventoryLevels = new HashSet<>();
    
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private Set<DemandForecast> demandForecasts = new HashSet<>();
    
    @OneToMany(mappedBy = "sourceStore", cascade = CascadeType.ALL)
    private Set<TransferSuggestion> sourceSuggestions = new HashSet<>();
    
    @OneToMany(mappedBy = "targetStore", cascade = CascadeType.ALL)
    private Set<TransferSuggestion> targetSuggestions = new HashSet<>();
    
    // Constructors
    public Store() {}
    
    public Store(String storeName, String address, String region) {
        this.storeName = storeName;
        this.address = address;
        this.region = region;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public Set<InventoryLevel> getInventoryLevels() { return inventoryLevels; }
    public void setInventoryLevels(Set<InventoryLevel> inventoryLevels) { this.inventoryLevels = inventoryLevels; }
    
    public Set<DemandForecast> getDemandForecasts() { return demandForecasts; }
    public void setDemandForecasts(Set<DemandForecast> demandForecasts) { this.demandForecasts = demandForecasts; }
}