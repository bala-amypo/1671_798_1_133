package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.InventoryBalancerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryBalancerServiceImpl implements InventoryBalancerService {

    private final ProductRepository productRepo;
    private final InventoryLevelRepository inventoryRepo;
    private final DemandForecastRepository forecastRepo;
    private final TransferSuggestionRepository suggestionRepo;

    public InventoryBalancerServiceImpl(ProductRepository p,
                                        InventoryLevelRepository i,
                                        DemandForecastRepository f,
                                        TransferSuggestionRepository t) {
        this.productRepo = p;
        this.inventoryRepo = i;
        this.forecastRepo = f;
        this.suggestionRepo = t;
    }

    @Override
    public List<TransferSuggestion> generateSuggestions(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isActive()) {
            throw new BadRequestException("Inactive product");
        }

        List<InventoryLevel> inventories = inventoryRepo.findByProduct_Id(productId);
        List<DemandForecast> forecasts = forecastRepo.findByProduct_Id(productId);

        List<TransferSuggestion> result = new ArrayList<>();

        if (inventories.size() >= 2 && forecasts.size() >= 2) {
            InventoryLevel src = inventories.get(0);
            InventoryLevel tgt = inventories.get(1);

            TransferSuggestion ts = new TransferSuggestion();
            ts.setProduct(product);
            ts.setSourceStore(src.getStore());
            ts.setTargetStore(tgt.getStore());
            ts.setSuggestedQuantity(10);
            ts.setReason("Auto balance");

            result.add(suggestionRepo.save(ts));
        }

        return result;
    }

    @Override
    public TransferSuggestion getSuggestionById(Long id) {
        return suggestionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
