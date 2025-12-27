package com.example.demo.service;

import com.example.demo.entity.InventoryLevel;

public interface InventoryService {

    /**
     * Create a new inventory level or update an existing one.
     * @param storeId ID of the store
     * @param productId ID of the product
     * @param quantity Quantity of product
     * @return saved or updated InventoryLevel
     */
    InventoryLevel createOrUpdateInventory(Long storeId, Long productId, int quantity);
}
