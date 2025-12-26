package com.example.demo.controller;

import com.example.demo.dto.InventoryRequest;
import com.example.demo.entity.InventoryLevel;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryLevelRepository inventoryRepo;

    public InventoryController(
            InventoryService inventoryService,
            InventoryLevelRepository inventoryRepo) {
        this.inventoryService = inventoryService;
        this.inventoryRepo = inventoryRepo;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<InventoryLevel> createInventory(
            @RequestBody InventoryRequest req) {

        InventoryLevel saved = inventoryService.createOrUpdateInventory(
                req.getStoreId(),
                req.getProductId(),
                req.getQuantity()
        );

        // 🔥 CRITICAL FIX:
        // Reload entity so store & product are fully initialized
        InventoryLevel reloaded =
                inventoryRepo.findById(saved.getId()).orElse(saved);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reloaded);
    }
}
