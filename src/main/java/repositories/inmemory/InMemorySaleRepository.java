package repositories.inmemory;

import models.Customer;
import models.Sale;
import models.SaleItem;
import repositories.SaleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class InMemorySaleRepository implements SaleRepository {
    private List<Sale> sales = new CopyOnWriteArrayList<>();
    private int nextId = 1;

    public Sale createSale( int customerId, List<SaleItem> saleItems){
        Sale sale = new Sale(nextId++, LocalDateTime.now(), customerId, saleItems);
        sales.add(sale);
        return sale;
    }
    public Optional<Sale> findById(int id){
        for (Sale sale : sales){
            if (sale.getId() == id){
                return Optional.of(sale);
            }
        }
        return null;
    }
    public List<Sale> findAll(){
        return new ArrayList<>(sales);
    }
    public List<Sale> findByCustomer(int customerId){
        List<Sale> result = new ArrayList<>();
        for (Sale sale : sales){
            if (sale.getCustomerId() == customerId){
                result.add(sale);
            }
        }
        return result;
    }
    public List<Sale> findByDate(LocalDate date){
        return sales.stream().filter(sale -> sale.getDate().toLocalDate().equals(date)).collect(Collectors.toList());
    }
    public List<Sale> findByDateRange(LocalDate from, LocalDate to){
        return sales.stream().filter(sale -> {
            LocalDate saleDate = sale.getDate().toLocalDate();
            return !saleDate.isBefore(from) && !saleDate.isAfter(to);}).collect(Collectors.toList());
    }
}
