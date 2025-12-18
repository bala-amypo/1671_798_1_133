package com.example.demo.controller;

import com.example.demo.model.DemandForecast;
import com.example.demo.service.DemandForecastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/forecasts")
@Tag(name = "Demand Forecast", description = "Demand forecasting operations")
public class DemandForecastController {
    
    private final DemandForecastService demandForecastService;
    
    public DemandForecastController(DemandForecastService demandForecastService) {
        this.demandForecastService = demandForecastService;
    }
    
    @PostMapping
    @Operation(summary = "Create demand forecast", 
              description = "Creates a new demand forecast for a product in a store")
    public ResponseEntity<DemandForecast> createForecast(@Valid @RequestBody DemandForecast forecast) {
        DemandForecast createdForecast = demandForecastService.createForecast(forecast);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdForecast);
    }
    
    @GetMapping("/store/{storeId}/product/{productId}")
    @Operation(summary = "Get forecast for store and product", 
              description = "Retrieves the latest demand forecast for a specific product in a specific store")
    public ResponseEntity<DemandForecast> getForecast(
            @PathVariable Long storeId,
            @PathVariable Long productId) {
        
        DemandForecast forecast = demandForecastService.getForecast(storeId, productId);
        return ResponseEntity.ok(forecast);
    }
}