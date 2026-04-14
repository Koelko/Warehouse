package services;

import models.*;
import repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaleService {
    private SaleRepository saleRepository;
    private StockService stockService;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private WarehouseRepository warehouseRepository;

    public SaleService(SaleRepository saleRepository,
                       StockService stockService,
                       ProductRepository productRepository,
                       CustomerRepository customerRepository,
                       WarehouseRepository warehouseRepository) {
        this.saleRepository = saleRepository;
        this.stockService = stockService;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Sale sell(int productId, int customerId, int warehouseId, int count) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Товар с id=" + productId + " не найден"));;
        if (product == null) {
            throw new IllegalArgumentException("Товар не найден");
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Покупатель с id=" + customerId + " не найден"));;
        if (customer == null) {
            throw new IllegalArgumentException("Покупатель не найден");
        }
        stockService.sell(productId, customerId, warehouseId, count);
        return saleRepository.createSale(customerId, createSaleItems(product, count, warehouseId));
    }

    private List<SaleItem> createSaleItems(Product product, int count, int warehouseId) {
        List<SaleItem> items = new ArrayList<>();
        items.add(new SaleItem(0, product.getId(), count, product.getPrice(), warehouseId));
        return items;
    }
    public List<Sale> getAll(){return saleRepository.findAll();}
}