package repositories;

import models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryCustomerRepository implements CustomerRepository{
    private List<Customer> customers = new CopyOnWriteArrayList<>();
    private int nextId = 1;
    public Customer createCustomer(String name, String contactInfo){
        Customer customer =  new Customer(nextId++, name, contactInfo);
        customers.add(customer);
        return customer;
    }
    public void remove(int id) {
        customers.removeIf(customer -> customer.getId() == id);
    }

    public Customer findById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }
    public Customer update(Customer customer){
        for (int i = 0; i < customers.size(); i++){
            if (customers.get(i).getId() == customer.getId()){
                customers.set(i, customer);
                return customer;
            }
        }
        throw new IllegalArgumentException("Покупатель с id \" + customer.getId() + \" не найден");
    }
    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

}

