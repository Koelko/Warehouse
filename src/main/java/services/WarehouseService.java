package services;

import models.Warehouse;
import repositories.WarehouseRepository;

import java.util.List;

public class WarehouseService {
    private WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse createWarehouse(String name, String address) {
        return warehouseRepository.createWarehouse(name, address);
    }

    public Warehouse findById(int id) {
        return warehouseRepository.findById(id);
    }

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }
}