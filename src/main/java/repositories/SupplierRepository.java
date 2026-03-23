package repositories;

import models.Supplier;

import java.util.List;

public interface SupplierRepository {
    Supplier createSupplier(String name, String contactInfo);
    void remove(int id);
    List<Supplier> findAll();
    Supplier findById(int id);
}
