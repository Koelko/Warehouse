package repositories;

import models.Customer;
import models.Sale;
import models.SaleItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SaleRepository {
    Sale createSale(int customerId, List<SaleItem> saleItems);
    Optional<Sale> findById(int id);
    List<Sale> findAll();
    List<Sale> findByDate(LocalDate date);
    List<Sale> findByDateRange(LocalDate from, LocalDate to);
}
