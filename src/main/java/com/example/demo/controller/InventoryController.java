package com.example.demo.controller;

import com.example.demo.dto.InventoryRequest;
import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
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
