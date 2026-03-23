package repositories;

import models.Warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryWarehouseRepository implements WarehouseRepository{
    private int nextId = 1;
    private List<Warehouse> warehouses = new CopyOnWriteArrayList<>();
    public Warehouse createWarehouse(String name, String address){
        Warehouse warehouse =  new Warehouse(nextId++, name, address);
        warehouses.add(warehouse);
        return warehouse;
    }
    public Warehouse findById(int id){
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getId() == id){
                return warehouse;
            }
        }
        return null;
    }
    public List<Warehouse> findAll() {
        return new ArrayList<>(warehouses);
    }
}