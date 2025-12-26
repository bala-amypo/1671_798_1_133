package com.example.demo.service.impl;

import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.InventoryService;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public InventoryServiceImpl(
            InventoryRepository inventoryRepository,
            StoreRepository storeRepository,
            ProductRepository productRepository
    ) {
        this.inventoryRepository = inventoryRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    @Override
    public InventoryLevel createOrUpdateInventory(
            Long storeId,
            Long productId,
            int quantity
    ) {

        if (quantity < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BadRequestException("Store not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException("Product not found"));

        return inventoryRepository
                .findByStoreIdAndProductId(storeId, productId)
                .map(existing -> {
                    // ✅ UPDATE EXISTING INVENTORY
                    existing.setQuantity(quantity);
                    return inventoryRepository.save(existing);
                })
                .orElseGet(() -> {
                    // ✅ CREATE NEW INVENTORY
                    InventoryLevel inventory = new InventoryLevel();
                    inventory.setStore(store);
                    inventory.setProduct(product);
                    inventory.setQuantity(quantity);
                    return inventoryRepository.save(inventory);
                });
    }
}
