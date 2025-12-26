package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.exception.*;

@Service
public class InventoryBalancerService {

    @Autowired private ProductRepository productRepo;
    @Autowired private InventoryLevelRepository invRepo;
    @Autowired private DemandForecastRepository forecastRepo;
    @Autowired private TransferSuggestionRepository tsRepo;

    public List<TransferSuggestion> generateSuggestions(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (!product.isActive())
            throw new BadRequestException("Inactive product");

        List<InventoryLevel> invs = invRepo.findByProduct_Id(productId);
        if (invs.size() < 2) return List.of();

        InventoryLevel max = Collections.max(invs, Comparator.comparingInt(InventoryLevel::getQuantity));
        InventoryLevel min = Collections.min(invs, Comparator.comparingInt(InventoryLevel::getQuantity));

        TransferSuggestion ts = new TransferSuggestion();
        ts.setProduct(product);
        ts.setSourceStore(max.getStore());
        ts.setTargetStore(min.getStore());
        ts.setSuggestedQuantity(10);
        ts.setReason("Auto-balance");

        return List.of(tsRepo.save(ts));
    }

    public TransferSuggestion getSuggestionById(Long id) {
        return tsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
