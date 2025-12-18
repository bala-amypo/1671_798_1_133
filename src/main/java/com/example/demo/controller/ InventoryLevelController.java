package com.example.demo.controller;

import com.example.demo.model.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory Management", description = "Inventory level operations")
public class InventoryLevelController {
    
    private final InventoryLevelService inventoryLevelService;
    
    public InventoryLevelController(InventoryLevelService inventoryLevelService) {
        this.inventoryLevelService = inventoryLevelService;
    }
    
    @PutMapping("/update")
    @Operation(summary = "Update inventory quantity", 
              description = "Updates inventory quantity for a specific product in a specific store")
    public ResponseEntity<InventoryLevel> updateInventory(
            @RequestParam Long storeId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        
        InventoryLevel inventoryLevel = inventoryLevelService.updateInventory(storeId, productId, quantity);
        return ResponseEntity.ok(inventoryLevel);
    }
    
    @GetMapping("/store/{storeId}")
    @Operation(summary = "Get inventory by store", 
              description = "Retrieves all inventory levels for a specific store")
    public ResponseEntity<List<InventoryLevel>> getInventoryByStore(@PathVariable Long storeId) {
        List<InventoryLevel> inventoryLevels = inventoryLevelService.getInventoryByStore(storeId);
        return ResponseEntity.ok(inventoryLevels);
    }
    
    @GetMapping("/item")
    @Operation(summary = "Get specific inventory item", 
              description = "Retrieves inventory level for a specific product in a specific store")
    public ResponseEntity<InventoryLevel> getInventory(
            @RequestParam Long storeId,
            @RequestParam Long productId) {
        
        InventoryLevel inventoryLevel = inventoryLevelService.getInventory(storeId, productId);
        return ResponseEntity.ok(inventoryLevel);
    }
}