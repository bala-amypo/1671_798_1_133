package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {

    private final TransferSuggestionRepository suggestionRepo;
    private final InventoryLevelRepository inventoryRepo;
    private final DemandForecastRepository forecastRepo;
    private final StoreRepository storeRepo;

    // 🚨 REQUIRED CONSTRUCTOR ORDER
    public InventoryBalancerServiceImpl(
            TransferSuggestionRepository suggestionRepo,
            InventoryLevelRepository inventoryRepo,
            DemandForecastRepository forecastRepo,
            StoreRepository storeRepo
    ) {
        this.suggestionRepo = suggestionRepo;
        this.inventoryRepo = inventoryRepo;
        this.forecastRepo = forecastRepo;
        this.storeRepo = storeRepo;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {

        List<InventoryLevel> inventories = inventoryRepo.findByProduct_Id(productId);
        List<TransferSuggestion> results = new ArrayList<>();

        for (InventoryLevel source : inventories) {

            List<DemandForecast> sourceForecasts =
                    forecastRepo.findByStoreAndProductAndForecastDateAfter(
                            source.getStore(),
                            source.getProduct(),
                            LocalDate.now()
                    );

            if (sourceForecasts.isEmpty()) {
                throw new BadRequestException("No forecast found");
            }

            int sourceDemand = sourceForecasts.get(0).getPredictedDemand();

            if (source.getQuantity() <= sourceDemand) continue;

            for (InventoryLevel target : inventories) {

                if (source.getStore().getId().equals(target.getStore().getId())) continue;

                List<DemandForecast> targetForecasts =
                        forecastRepo.findByStoreAndProductAndForecastDateAfter(
                                target.getStore(),
                                target.getProduct(),
                                LocalDate.now()
                        );

                if (targetForecasts.isEmpty()) continue;

                int targetDemand = targetForecasts.get(0).getPredictedDemand();

                if (target.getQuantity() < targetDemand) {

                    int transferable = Math.min(
                            source.getQuantity() - sourceDemand,
                            targetDemand - target.getQuantity()
                    );

                    if (transferable > 0) {
                        TransferSuggestion suggestion = new TransferSuggestion();
                        suggestion.setSourceStore(source.getStore());
                        suggestion.setTargetStore(target.getStore());
                        suggestion.setProduct(source.getProduct());
                        suggestion.setQuantity(transferable);
                        suggestion.setPriority("HIGH");

                        results.add(suggestionRepo.save(suggestion));
                    }
                }
            }
        }

        return results;
    }

    @Override
    public List<TransferSuggestion> getSuggestionsForStore(Long storeId) {
        return suggestionRepo.findBySourceStoreId(storeId);
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return suggestionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
