package services;

import models.Customer;
import repositories.CustomerRepository;

import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Customer createCustomer(String name, String contactInfo){
        return customerRepository.createCustomer(name, contactInfo);
    }
    public Customer findById(int id){
        return customerRepository.findById(id);
    }
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }
}
