package com.example.demo.service;

import com.example.demo.entity.TransferSuggestion;
import java.util.List;

public interface InventoryBalancerService {
    List<TransferSuggestx   ion> generateSuggestions(Long productId);
    TransferSuggestion getSuggestionById(Long id);
}
