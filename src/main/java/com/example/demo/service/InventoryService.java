package com.example.demo.service;

import com.example.demo.entity.InventoryLevel;

public interface InventoryService {

    InventoryLevel createOrUpdateInventory(
            Long storeId,
            Long productId,
            int quantity
    );
}
