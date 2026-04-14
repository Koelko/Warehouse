package services;

import models.Supplier;
import repositories.SupplierRepository;

import java.util.List;
import java.util.Optional;

public class SupplierService {
    private SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
    public Supplier createSupplier(String name, String contactInfo){
        return supplierRepository.createSupplier(name, contactInfo);
    }
    public Optional<Supplier> findById(int id){
        return supplierRepository.findById(id);
    }
    public List<Supplier> findAll() {
       return supplierRepository.findAll();
    }
    public Supplier update(Supplier supplier){
        return supplierRepository.update(supplier);
    }
}
