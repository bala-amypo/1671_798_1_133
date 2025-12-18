package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.InventoryLevel;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.InventoryLevelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryLevelServiceImpl implements InventoryLevelService {
    
    private final InventoryLevelRepository inventoryLevelRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    
    public InventoryLevelServiceImpl(
            InventoryLevelRepository inventoryLevelRepository,
            StoreRepository storeRepository,
            ProductRepository productRepository) {
        this.inventoryLevelRepository = inventoryLevelRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }
    
    @Override
    public InventoryLevel updateInventory(Long storeId, Long productId, Integer quantity) {
        if (quantity < 0) {
            throw new BadRequestException("Quantity must be >= 0");
        }
        
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        InventoryLevel inventoryLevel = inventoryLevelRepository
            .findByStoreAndProduct(store, product)
            .orElse(new InventoryLevel(store, product, quantity));
        
        inventoryLevel.setQuantity(quantity);
        
        return inventoryLevelRepository.save(inventoryLevel);
    }
    
    @Override
    public InventoryLevel getInventory(Long storeId, Long productId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        return inventoryLevelRepository.findByStoreAndProduct(store, product)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory level not found"));
    }
    
    @Override
    public List<InventoryLevel> getInventoryByStore(Long storeId) {
        storeRepository.findById(storeId)
            .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        
        return inventoryLevelRepository.findByStoreId(storeId);
    }
}