package examples.validation;

import models.Customer;
import repositories.CustomerRepository;

public class CustomerValidator extends Validator{
    private CustomerRepository customerRepository;
    public CustomerValidator(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    void check(Context context){
        Customer customer = customerRepository.findById(context.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("Покупатель не найден"));
        context.setCustomer(customer);
    }
}
