package services;

import models.Customer;
import repositories.InMemoryCustomerRepository;

import java.util.List;

public class CustomerService {
    private InMemoryCustomerRepository inMemoryCustomerRepository;

    public CustomerService(InMemoryCustomerRepository inMemoryCustomerRepository) {
        this.inMemoryCustomerRepository = inMemoryCustomerRepository;
    }
    public Customer createCustomer(String name, String contactInfo){
        return inMemoryCustomerRepository.createCustomer(name, contactInfo);
    }
    public Customer findById(int id){
        return inMemoryCustomerRepository.findById(id);
    }
    public List<Customer> findAll(){
        return inMemoryCustomerRepository.findAll();
    }
    public void remove(int id){
        inMemoryCustomerRepository.remove(id);
    }
}
