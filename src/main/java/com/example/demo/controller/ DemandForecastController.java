package com.example.demo.controller;

import com.example.demo.entity.DemandForecast;
import com.example.demo.service.DemandForecastService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forecasts")
@Tag(name = "Demand Forecasts")
public class DemandForecastController {

    private final DemandForecastService forecastService;

    public DemandForecastController(DemandForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @PostMapping
    public DemandForecast create(@RequestBody DemandForecast forecast) {
        return forecastService.createForecast(forecast);
    }

    @GetMapping("/store/{storeId}/product/{productId}")
    public DemandForecast getForecast(
            @PathVariable Long storeId,
            @PathVariable Long productId
    ) {
        return forecastService.getForecast(storeId, productId);
    }

    @GetMapping("/store/{storeId}")
    public List<DemandForecast> getByStore(@PathVariable Long storeId) {
        return forecastService.getForecastsForStore(storeId);
    }
}
