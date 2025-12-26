package com.example.demo.service;

import com.example.demo.entity.InventoryLevel;
import java.util.List;

public interface InventoryLevelService {

    // ✅ REQUIRED BY TESTS
    InventoryLevel createOrUpdateInventory(InventoryLevel inventory);

    // ✅ INTERNAL LOGIC METHOD
    InventoryLevel createOrUpdateInventory(Long storeId, Long productId, int quantity);

    List<InventoryLevel> getInventoryForStore(Long storeId);

    List<InventoryLevel> getInventoryForProduct(Long productId);
}
