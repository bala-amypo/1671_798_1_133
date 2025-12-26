package com.example.demo.service.impl;

import com.example.demo.entity.Store;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepo;

    public StoreServiceImpl(StoreRepository storeRepo) {
        this.storeRepo = storeRepo;
    }

    @Override
    public Store createStore(Store store) {
        return storeRepo.save(store);
    }

    @Override
    public Store updateStore(Long id, Store store) {
        Store existing = getStoreById(id);
        existing.setStoreName(store.getStoreName());
        existing.setAddress(store.getAddress());
        existing.setRegion(store.getRegion());
        existing.setActive(store.isActive());
        return storeRepo.save(existing);
    }

    @Override
    public void deactivateStore(Long id) {
        Store store = getStoreById(id);
        store.setActive(false);
        storeRepo.save(store);
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepo.findAll();
    }
}
