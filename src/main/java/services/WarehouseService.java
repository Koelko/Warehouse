package services;

import models.Warehouse;
import repositories.WarehouseRepository;

import java.util.List;
import java.util.Optional;

public class WarehouseService {
    private WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse createWarehouse(String name, String address) {
        return warehouseRepository.createWarehouse(name, address);
    }

    public Optional<Warehouse> findById(int id) {
        return warehouseRepository.findById(id);
    }

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }
    public Warehouse update(Warehouse warehouse){
        return warehouseRepository.update(warehouse);
    }
}