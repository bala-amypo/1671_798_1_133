package com.example.demo.repository;

import com.example.demo.entity.InventoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryLevel, Long> {

    Optional<InventoryLevel> findByStoreIdAndProductId(Long storeId, Long productId);
}
