package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.DemandForecast;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.repository.DemandForecastRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.DemandForecastService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DemandForecastServiceImpl implements DemandForecastService {
    
    private final DemandForecastRepository demandForecastRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    
    public DemandForecastServiceImpl(
            DemandForecastRepository demandForecastRepository,
            StoreRepository storeRepository,
            ProductRepository productRepository) {
        this.demandForecastRepository = demandForecastRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }
    
    @Override
    public DemandForecast createForecast(DemandForecast forecast) {
        // Validate forecast date is in the future
        if (forecast.getForecastDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new BadRequestException("Forecast date must be in the future");
        }
        
        // Validate predicted demand
        if (forecast.getPredictedDemand() < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0");
        }
        
        return demandForecastRepository.save(forecast);
    }
    
    @Override
    public DemandForecast getForecast(Long storeId, Long productId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        List<DemandForecast> forecasts = demandForecastRepository.findByStoreAndProduct(store, product);
        
        if (forecasts.isEmpty()) {
            throw new ResourceNotFoundException("No forecast found");
        }
        
        // Return the most recent forecast
        return forecasts.stream()
            .filter(f -> f.getForecastDate().isAfter(LocalDate.now()))
            .max((f1, f2) -> f1.getForecastDate().compareTo(f2.getForecastDate()))
            .orElseThrow(() -> new ResourceNotFoundException("No forecast found"));
    }
}