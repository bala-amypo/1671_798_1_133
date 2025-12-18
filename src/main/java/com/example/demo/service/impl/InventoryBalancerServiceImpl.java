package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventoryBalancerServiceImpl implements InventoryBalancerService {
    
    private final TransferSuggestionRepository transferSuggestionRepository;
    private final InventoryLevelRepository inventoryLevelRepository;
    private final DemandForecastRepository demandForecastRepository;
    private final StoreRepository storeRepository;
    
    // IMPORTANT: Constructor arguments must be in this exact order
    public InventoryBalancerServiceImpl(
            TransferSuggestionRepository transferSuggestionRepository,
            InventoryLevelRepository inventoryLevelRepository,
            DemandForecastRepository demandForecastRepository,
            StoreRepository storeRepository) {
        this.transferSuggestionRepository = transferSuggestionRepository;
        this.inventoryLevelRepository = inventoryLevelRepository;
        this.demandForecastRepository = demandForecastRepository;
        this.storeRepository = storeRepository;
    }
    
    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {
        Product product = storeRepository.findById(productId) // Using storeRepository temporarily, will be replaced
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        // Get all stores and their inventory for this product
        List<InventoryLevel> allInventory = inventoryLevelRepository.findByProductId(productId);
        
        // Get demand forecasts for all stores
        List<DemandForecast> allForecasts = demandForecastRepository.findByProductId(productId)
            .stream()
            .filter(f -> f.getForecastDate().isAfter(LocalDate.now()))
            .toList();
        
        if (allForecasts.isEmpty()) {
            throw new ResourceNotFoundException("No forecast found");
        }
        
        List<TransferSuggestion> suggestions = new ArrayList<>();
        
        // Identify surplus and deficit stores
        List<StoreSurplus> surplusStores = new ArrayList<>();
        List<StoreDeficit> deficitStores = new ArrayList<>();
        
        for (InventoryLevel inventory : allInventory) {
            Store store = inventory.getStore();
            
            // Find forecast for this store
            DemandForecast forecast = allForecasts.stream()
                .filter(f -> f.getStore().getId().equals(store.getId()))
                .findFirst()
                .orElse(null);
            
            if (forecast == null) {
                // Store without forecast
                continue;
            }
            
            int surplus = inventory.getQuantity() - forecast.getPredictedDemand();
            
            if (surplus > 0) {
                surplusStores.add(new StoreSurplus(store, surplus));
            } else if (surplus < 0) {
                deficitStores.add(new StoreDeficit(store, Math.abs(surplus)));
            }
        }
        
        // Generate transfer suggestions
        for (StoreSurplus surplus : surplusStores) {
            for (StoreDeficit deficit : deficitStores) {
                if (surplus.remainingSurplus <= 0) break;
                
                int transferAmount = Math.min(surplus.remainingSurplus, deficit.remainingDeficit);
                
                if (transferAmount > 0) {
                    TransferSuggestion suggestion = new TransferSuggestion();
                    suggestion.setSourceStore(surplus.store);
                    suggestion.setTargetStore(deficit.store);
                    suggestion.setProduct(product);
                    suggestion.setQuantity(transferAmount);
                    
                    // Determine priority
                    double urgencyRatio = (double) deficit.remainingDeficit / deficit.totalDeficit;
                    if (urgencyRatio > 0.7) {
                        suggestion.setPriority(TransferSuggestion.PriorityLevel.HIGH);
                    } else if (urgencyRatio > 0.3) {
                        suggestion.setPriority(TransferSuggestion.PriorityLevel.MEDIUM);
                    } else {
                        suggestion.setPriority(TransferSuggestion.PriorityLevel.LOW);
                    }
                    
                    suggestion.setStatus(TransferSuggestion.SuggestionStatus.PENDING);
                    
                    transferSuggestionRepository.save(suggestion);
                    suggestions.add(suggestion);
                    
                    // Update remaining amounts
                    surplus.remainingSurplus -= transferAmount;
                    deficit.remainingDeficit -= transferAmount;
                }
            }
        }
        
        return suggestions;
    }
    
    @Override
    public List<TransferSuggestion> getSuggestionsForStore(Long storeId) {
        List<TransferSuggestion> suggestions = new ArrayList<>();
        suggestions.addAll(transferSuggestionRepository.findBySourceStoreId(storeId));
        suggestions.addAll(transferSuggestionRepository.findByTargetStoreId(storeId));
        return suggestions;
    }
    
    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return transferSuggestionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transfer suggestion not found"));
    }
    
    // Helper classes for suggestion generation
    private static class StoreSurplus {
        Store store;
        int totalSurplus;
        int remainingSurplus;
        
        StoreSurplus(Store store, int surplus) {
            this.store = store;
            this.totalSurplus = surplus;
            this.remainingSurplus = surplus;
        }
    }
    
    private static class StoreDeficit {
        Store store;
        int totalDeficit;
        int remainingDeficit;
        
        StoreDeficit(Store store, int deficit) {
            this.store = store;
            this.totalDeficit = deficit;
            this.remainingDeficit = deficit;
        }
    }
}