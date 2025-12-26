package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.InventoryLevelRepository;
import com.example.demo.entity.InventoryLevel;
import com.example.demo.exception.BadRequestException;
import java.util.List;

@Service
public class InventoryLevelService {

    @Autowired
    private InventoryLevelRepository repo;

    public InventoryLevel createOrUpdateInventory(InventoryLevel inv) {

        if (inv.getQuantity() < 0)
            throw new BadRequestException("Negative quantity");

        return repo.findByStore_IdAndProduct_Id(
                inv.getStore().getId(),
                inv.getProduct().getId()
        ).map(e -> {
            e.setQuantity(inv.getQuantity());
            return repo.save(e);
        }).orElse(repo.save(inv));
    }

    public List<InventoryLevel> getInventoryForStore(Long id) {
        return repo.findByStore_Id(id);
    }

    public List<InventoryLevel> getInventoryForProduct(Long id) {
        return repo.findByProduct_Id(id);
    }
}
