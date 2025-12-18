package com.example.demo.repository;

import com.example.demo.model.InventoryLevel;
import com.example.demo.model.Product;
import com.example.demo.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryLevelRepository extends JpaRepository<InventoryLevel, Long> {
    Optional<InventoryLevel> findByStoreAndProduct(Store store, Product product);
    List<InventoryLevel> findByStoreId(Long storeId);
    List<InventoryLevel> findByProductId(Long productId);
}