package com.example.demo.repository;

import com.example.demo.model.DemandForecast;
import com.example.demo.model.Product;
import com.example.demo.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DemandForecastRepository extends JpaRepository<DemandForecast, Long> {
    Optional<DemandForecast> findByStoreAndProductAndForecastDateAfter(Store store, Product product, LocalDate date);
    List<DemandForecast> findByStoreAndProduct(Store store, Product product);
    List<DemandForecast> findByProductId(Long productId);
}