package validation;

import models.Customer;
import repositories.CustomerRepository;

public class CustomerValidator extends Validator{
    private CustomerRepository customerRepository;
    public CustomerValidator(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    void check(Context context){
        if(context == null){
            throw new IllegalArgumentException("Контекст не может быть пустым");
        }
        Integer customerId = context.getCustomerId();
        if(customerId == null){
            throw new IllegalArgumentException("customerId не может быть пустым");
        }
        Customer customer = customerRepository.findById(customerId);
        if(customer == null){
            throw new IllegalArgumentException("Нет такого клиента");
        }
        context.setCustomer(customer);
    }
}
