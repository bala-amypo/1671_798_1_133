package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.DemandForecastRepository;
import com.example.demo.entity.DemandForecast;
import com.example.demo.exception.BadRequestException;
import java.time.LocalDate;
import java.util.List;

@Service
public class DemandForecastService {

    @Autowired
    private DemandForecastRepository repo;

    public DemandForecast createForecast(DemandForecast f) {
        if (f.getForecastDate().isBefore(LocalDate.now()))
            throw new BadRequestException("Past date");
        return repo.save(f);
    }

    public List<DemandForecast> getForecastsForStore(Long id) {
        return repo.findByStore_Id(id);
    }
}
