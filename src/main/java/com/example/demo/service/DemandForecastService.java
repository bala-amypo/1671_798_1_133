package com.example.demo.service;

import com.example.demo.entity.DemandForecast;

import java.util.List;

public interface DemandForecastService {

    DemandForecast createForecast(DemandForecast forecast);

    DemandForecast getForecast(Long storeId, Long productId);

    List<DemandForecast> getForecastsForStore(Long storeId);
}
