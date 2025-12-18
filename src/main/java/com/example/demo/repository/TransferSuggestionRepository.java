package com.example.demo.repository;

import com.example.demo.model.TransferSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferSuggestionRepository extends JpaRepository<TransferSuggestion, Long> {
    List<TransferSuggestion> findBySourceStoreId(Long storeId);
    List<TransferSuggestion> findByTargetStoreId(Long storeId);
    List<TransferSuggestion> findByProductId(Long productId);
}