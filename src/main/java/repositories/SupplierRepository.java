package repositories;

import models.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository {
    Supplier createSupplier(String name, String contactInfo);
    Supplier update(Supplier supplier);
    List<Supplier> findAll();
    Optional<Supplier> findById(int id);
}
