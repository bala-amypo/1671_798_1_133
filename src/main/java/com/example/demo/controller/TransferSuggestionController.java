package com.example.demo.controller;

import com.example.demo.model.TransferSuggestion;
import com.example.demo.service.InventoryBalancerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
@Tag(name = "Transfer Suggestions", description = "Inventory transfer suggestion operations")
public class TransferSuggestionController {
    
    private final InventoryBalancerService inventoryBalancerService;
    
    public TransferSuggestionController(InventoryBalancerService inventoryBalancerService) {
        this.inventoryBalancerService = inventoryBalancerService;
    }
    
    @PostMapping("/generate/{productId}")
    @Operation(summary = "Generate transfer suggestions", 
              description = "Generates transfer suggestions for a specific product based on inventory and forecast")
    public ResponseEntity<List<TransferSuggestion>> generateSuggestions(@PathVariable Long productId) {
        List<TransferSuggestion> suggestions = inventoryBalancerService.generateSuggestions(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(suggestions);
    }
    
    @GetMapping("/store/{storeId}")
    @Operation(summary = "Get suggestions for store", 
              description = "Retrieves all transfer suggestions for a specific store (both as source and target)")
    public ResponseEntity<List<TransferSuggestion>> getSuggestionsForStore(@PathVariable Long storeId) {
        List<TransferSuggestion> suggestions = inventoryBalancerService.getSuggestionsForStore(storeId);
        return ResponseEntity.ok(suggestions);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get suggestion by ID", 
              description = "Retrieves a specific transfer suggestion by its ID")
    public ResponseEntity<TransferSuggestion> getSuggestionById(@PathVariable Long id) {
        TransferSuggestion suggestion = inventoryBalancerService.getSuggestionById(id);
        return ResponseEntity.ok(suggestion);
    }
}