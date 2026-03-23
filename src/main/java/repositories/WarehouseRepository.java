package repositories;

import models.Warehouse;

import java.util.List;

public interface WarehouseRepository {
    Warehouse createWarehouse(String name, String address);
    Warehouse findById(int id);
    List<Warehouse> findAll();
}
