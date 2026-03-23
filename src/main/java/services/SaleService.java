package services;

import models.*;
import repositories.*;

import java.util.ArrayList;
import java.util.List;

public class SaleService {
    private InMemorySaleRepository inMemorySaleRepository;
    private StockService stockService;
    private InMemoryProductRepository inMemoryProductRepository;
    private InMemoryCustomerRepository inMemoryCustomerRepository;
    private InMemoryWarehouseRepository inMemoryWarehouseRepository;

    public SaleService(InMemorySaleRepository inMemorySaleRepository,
                       StockService stockService,
                       InMemoryProductRepository inMemoryProductRepository,
                       InMemoryCustomerRepository inMemoryCustomerRepository,
                       InMemoryWarehouseRepository inMemoryWarehouseRepository) {
        this.inMemorySaleRepository = inMemorySaleRepository;
        this.stockService = stockService;
        this.inMemoryProductRepository = inMemoryProductRepository;
        this.inMemoryCustomerRepository = inMemoryCustomerRepository;
        this.inMemoryWarehouseRepository = inMemoryWarehouseRepository;
    }

    public Sale sell(int productId, int customerId, int warehouseId, int count) {
        Product product = inMemoryProductRepository.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Товар не найден");
        }

        Customer customer = inMemoryCustomerRepository.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Покупатель не найден");
        }
        stockService.sell(productId, customerId, warehouseId, count);
        return inMemorySaleRepository.createSale(customer, createSaleItems(product, count));
    }

    private List<SaleItem> createSaleItems(Product product, int count) {
        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(0, product, count, product.getPrice()));
        return items;
    }
    public List<Sale> getAll(){return inMemorySaleRepository.findAll();}
}