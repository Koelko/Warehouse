package repositories;

import models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer createCustomer(String name, String contactInfo);
    Customer update(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(int id);
}
