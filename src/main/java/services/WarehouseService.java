package services;

import models.Warehouse;
import repositories.InMemoryWarehouseRepository;

import java.util.List;

public class WarehouseService {
    private InMemoryWarehouseRepository inMemoryWarehouseRepository;

    public WarehouseService(InMemoryWarehouseRepository inMemoryWarehouseRepository) {
        this.inMemoryWarehouseRepository = inMemoryWarehouseRepository;
    }

    public Warehouse createWarehouse(String name, String address) {
        return inMemoryWarehouseRepository.createWarehouse(name, address);
    }

    public Warehouse findById(int id) {
        return inMemoryWarehouseRepository.findById(id);
    }

    public List<Warehouse> findAll() {
        return inMemoryWarehouseRepository.findAll();
    }
}