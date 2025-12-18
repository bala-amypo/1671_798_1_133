package com.example.demo.service;

import com.example.demo.model.DemandForecast;

public interface DemandForecastService {
    DemandForecast createForecast(DemandForecast forecast);
    DemandForecast getForecast(Long storeId, Long productId);
}