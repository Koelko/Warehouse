package repositories;

import models.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer createCustomer(String name, String contactInfo);
    Customer update(Customer customer);
    List<Customer> findAll();
    Customer findById(int id);
}
