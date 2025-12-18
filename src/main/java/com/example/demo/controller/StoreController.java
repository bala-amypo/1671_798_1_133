package com.example.demo.controller;

import com.example.demo.model.Store;
import com.example.demo.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@Tag(name = "Store Management", description = "Store CRUD operations")
public class StoreController {
    
    private final StoreService storeService;
    
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new store")
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        Store createdStore = storeService.createStore(store);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStore);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get store by ID")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Store store = storeService.getStoreById(id);
        return ResponseEntity.ok(store);
    }
    
    @GetMapping
    @Operation(summary = "Get all stores")
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }
}