package app;
import repositories.*;

import services.*;
import ui.ConsoleUI;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new JdbcProductRepository();
        SupplierRepository supplierRepository = new JdbcSupplierRepository();
        CustomerRepository customerRepository = new JdbcCustomerRepository();
        WarehouseRepository warehouseRepository = new JdbcWarehouseRepository();
        ProductQueryRepository productQueryRepository = new JdbcProductQueryRepository();
        SaleRepository saleRepository = new JdbcSaleRepository();
        StockRepository stockRepository = new JdbcStockRepository();
        StockQueryRepository stockQueryRepository = new JdbcStockQueryRepository();
        StockService stockService = new StockService(stockRepository, stockQueryRepository, productRepository, supplierRepository, customerRepository, warehouseRepository);
        ProductService productService = new ProductService(productRepository, productQueryRepository);
        CustomerService customerService = new CustomerService(customerRepository);
        SupplierService supplierService = new SupplierService(supplierRepository);
        WarehouseService warehouseService = new WarehouseService(warehouseRepository);
        SaleService saleService = new SaleService(saleRepository, stockService, productRepository, customerRepository, warehouseRepository);
        ConsoleUI ui = new ConsoleUI(customerService, productService,stockService, supplierService, warehouseService, saleService);
        ui.start();
    }
}
