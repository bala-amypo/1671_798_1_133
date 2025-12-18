package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Store;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {
    
    private final StoreRepository storeRepository;
    
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }
    
    @Override
    public Store createStore(Store store) {
        // Check for duplicate store name
        storeRepository.findByStoreName(store.getStoreName())
            .ifPresent(s -> {
                throw new BadRequestException("Store name already exists");
            });
        
        if (store.getActive() == null) {
            store.setActive(true);
        }
        
        return storeRepository.save(store);
    }
    
    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }
    
    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
}