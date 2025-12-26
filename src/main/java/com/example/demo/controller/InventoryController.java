package com.example.demo.controller;

import com.example.demo.dto.InventoryRequest;
import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryLevelService inventoryService;

    public InventoryController(InventoryLevelService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryLevel> createInventory(
            @RequestBody InventoryRequest req) {

        InventoryLevel saved = inventoryService.createOrUpdateInventory(
                req.getStoreId(),
                req.getProductId(),
                req.getQuantity()
        );

        return ResponseEntity.ok(saved);
    }
}
