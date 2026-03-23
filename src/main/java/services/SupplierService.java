package services;

import models.Supplier;
import repositories.InMemorySupplierRepository;

import java.util.List;

public class SupplierService {
    private InMemorySupplierRepository inMemorySupplierRepository;

    public SupplierService(InMemorySupplierRepository inMemorySupplierRepository) {
        this.inMemorySupplierRepository = inMemorySupplierRepository;
    }
    public Supplier createSupplier(String name, String contactInfo){
        return inMemorySupplierRepository.createSupplier(name, contactInfo);
    }
    public Supplier findById(int id){
        return inMemorySupplierRepository.findById(id);
    }
    public List<Supplier> findAll() {
       return inMemorySupplierRepository.findAll();
    }
    public void remove(int id){
        inMemorySupplierRepository.remove(id);
    }
}
