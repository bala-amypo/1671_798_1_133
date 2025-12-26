package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryLevelService inventoryService;

    public InventoryController(InventoryLevelService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public InventoryLevel createInventory(@RequestBody InventoryLevel inventory) {
        return inventoryService.createOrUpdateInventory(inventory);
    }
}
