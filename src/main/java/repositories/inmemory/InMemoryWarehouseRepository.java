package repositories.inmemory;

import models.Warehouse;
import repositories.WarehouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryWarehouseRepository implements WarehouseRepository {
    private int nextId = 1;
    private List<Warehouse> warehouses = new CopyOnWriteArrayList<>();
    public Warehouse createWarehouse(String name, String address){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название склада не может быть пустым");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Адрес склада не может быть пустым");
        }
        Warehouse warehouse =  new Warehouse(nextId++, name, address);
        warehouses.add(warehouse);
        return warehouse;
    }
    public Optional<Warehouse> findById(int id){
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getId() == id){
                return Optional.of(warehouse);
            }
        }
        return null;
    }
    public Warehouse update(Warehouse warehouse){
        for (int i = 0; i < warehouses.size(); i++){
            if (warehouses.get(i).getId() == warehouse.getId()){
                warehouses.set(i, warehouse);
                return warehouse;
            }
        }
        throw new IllegalArgumentException("Склад с id " + warehouse.getId() + " не найден");
    }
    public List<Warehouse> findAll() {
        return new ArrayList<>(warehouses);
    }
}