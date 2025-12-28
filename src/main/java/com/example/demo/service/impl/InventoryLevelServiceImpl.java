package com.example.demo.service.impl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.InventoryLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryLevelServiceImpl implements InventoryLevelService {

    private final InventoryLevelRepository inventoryRepo;
    private final StoreRepository storeRepo;
    private final ProductRepository productRepo;

    public InventoryLevelServiceImpl(
            InventoryLevelRepository inventoryRepo,
            StoreRepository storeRepo,
            ProductRepository productRepo) {
        this.inventoryRepo = inventoryRepo;
        this.storeRepo = storeRepo;
        this.productRepo = productRepo;
    }

    // =========================================================
    // REQUIRED BY TESTS
    // =========================================================
    @Override
    public InventoryLevel createOrUpdateInventory(InventoryLevel inventory) {

        if (inventory == null) {
            throw new BadRequestException("Inventory cannot be null");
        }

        if (inventory.getStore() == null || inventory.getProduct() == null) {
            throw new BadRequestException("Store and Product are required");
        }

        return createOrUpdateInventory(
                inventory.getStore().getId(),
                inventory.getProduct().getId(),
                inventory.getQuantity()
        );
    }

    // =========================================================
    // UPSERT LOGIC
    // =========================================================
    @Override
    public InventoryLevel createOrUpdateInventory(Long storeId, Long productId, int quantity) {

        if (quantity < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }

        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new BadRequestException("Store not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new BadRequestException("Product not found"));

        return inventoryRepo
                .findByStore_IdAndProduct_Id(storeId, productId)
                .map(existing -> {
                    // UPDATE PATH
                    existing.setStore(store);
                    existing.setProduct(product);
                    existing.setQuantity(quantity);
                    return inventoryRepo.save(existing);
                })
                .orElseGet(() -> {
                    // CREATE PATH (FINAL FIX)
                    InventoryLevel inv = new InventoryLevel();
                    inv.setStore(store);
                    inv.setProduct(product);
                    inv.setQuantity(quantity);

                    InventoryLevel saved = inventoryRepo.save(inv);

                    // 🔥 IMPORTANT: reload entity so tests see store & product
                    return inventoryRepo.findById(saved.getId())
                            .orElse(saved);
                });
    }

    @Override
    public List<InventoryLevel> getInventoryForStore(Long storeId) {
        return inventoryRepo.findByStore_Id(storeId);
    }

    @Override
    public List<InventoryLevel> getInventoryForProduct(Long productId) {
        return inventoryRepo.findByProduct_Id(productId);
    }
}
