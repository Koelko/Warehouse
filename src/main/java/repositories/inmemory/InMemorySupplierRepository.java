package repositories.inmemory;
import models.Supplier;
import repositories.SupplierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemorySupplierRepository implements SupplierRepository {
    private List<Supplier> suppliers = new CopyOnWriteArrayList<>();
    private int nextId = 1;
    public Supplier createSupplier(String name, String contactInfo){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя поставщика не может быть пустым");
        }
        Supplier supplier =  new Supplier(nextId++, name, contactInfo);
        suppliers.add(supplier);
        return supplier;
    }
    public void remove(int id){
        suppliers.removeIf(supplier -> supplier.getId() == id);
    }
    public Optional<Supplier> findById(int id){
        for (Supplier supplier : suppliers) {
            if (supplier.getId() == id){
                return Optional.of(supplier);
            }
        }
        return null;
    }
    @Override
    public Supplier update(Supplier supplier) {
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getId() == supplier.getId()) {
                suppliers.set(i, supplier);
                return supplier;
            }
        }
        throw new IllegalArgumentException("Поставщик с id" + supplier.getId() + " не найден");
    }
    public List<Supplier> findAll() {
        return new ArrayList<>(suppliers);
    }

}
