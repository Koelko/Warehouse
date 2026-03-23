package repositories;
import models.Supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemorySupplierRepository implements SupplierRepository{
    private List<Supplier> suppliers = new CopyOnWriteArrayList<>();
    private int nextId = 1;
    public Supplier createSupplier(String name, String contactInfo){
        Supplier supplier =  new Supplier(nextId++, name, contactInfo);
        suppliers.add(supplier);
        return supplier;
    }
    public void remove(int id){
        suppliers.removeIf(supplier -> supplier.getId() == id);
    }
    public Supplier findById(int id){
        for (Supplier supplier : suppliers) {
            if (supplier.getId() == id){
                return supplier;
            }
        }
        return null;
    }
    public List<Supplier> findAll() {
        return new ArrayList<>(suppliers);
    }

}
