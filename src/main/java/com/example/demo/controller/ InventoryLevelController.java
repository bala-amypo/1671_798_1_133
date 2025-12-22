package com.example.demo.controller;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.service.InventoryLevelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Inventory")
public class InventoryLevelController {

    private final InventoryLevelService inventoryService;

    public InventoryLevelController(InventoryLevelService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping("/update")
    public InventoryLevel updateInventory(
            @RequestParam Long storeId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ) {
        return inventoryService.updateInventory(storeId, productId, quantity);
    }

    @GetMapping("/store/{storeId}")
    public List<InventoryLevel> getByStore(@PathVariable Long storeId) {
        return inventoryService.getInventoryByStore(storeId);
    }

    @GetMapping("/product/{productId}")
    public List<InventoryLevel> getByProduct(@PathVariable Long productId) {
        return inventoryService.getInventoryByProduct(productId);
    }
}
