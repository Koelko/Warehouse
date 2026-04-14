package repositories;

import models.Supplier;
import models.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository {
    Warehouse createWarehouse(String name, String address);
    Warehouse update(Warehouse warehouse);
    Optional<Warehouse> findById(int id);
    List<Warehouse> findAll();
}
