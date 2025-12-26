package com.example.demo.service;

import com.example.demo.entity.Store;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepo;

    public Store createStore(Store store) {
        return storeRepo.save(store);
    }

    public Store updateStore(Long id, Store update) {

        Store existing = storeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        existing.setStoreName(update.getStoreName());
        existing.setAddress(update.getAddress());
        existing.setRegion(update.getRegion());
        existing.setActive(update.isActive());

        return storeRepo.save(existing);
    }

    public void deactivateStore(Long id) {
        Store store = getStoreById(id);
        store.setActive(false);
        storeRepo.save(store);
    }

    public Store getStoreById(Long id) {
        return storeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));
    }

    public List<Store> getAllStores() {
        return storeRepo.findAll();
    }
}
