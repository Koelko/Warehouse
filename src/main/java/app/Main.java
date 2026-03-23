package app;
import repositories.*;

import services.*;
import ui.ConsoleUI;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();
        InMemorySupplierRepository inMemorySupplierRepository = new InMemorySupplierRepository();
        InMemoryCustomerRepository inMemoryCustomerRepository = new InMemoryCustomerRepository();
        InMemoryWarehouseRepository inMemoryWarehouseRepository = new InMemoryWarehouseRepository();
        InMemoryProductQueryRepository inMemoryProductQueryRepository = new InMemoryProductQueryRepository(inMemoryProductRepository);
        InMemorySaleRepository inMemorySaleRepository = new InMemorySaleRepository();
        InMemoryStockRepository inMemoryStockRepository = new InMemoryStockRepository(inMemoryProductRepository, inMemorySupplierRepository, inMemoryWarehouseRepository);
        InMemoryStockQueryRepository inMemoryStockQueryRepository = new InMemoryStockQueryRepository(inMemoryStockRepository);
        StockService stockService = new StockService(inMemoryStockRepository, inMemoryStockQueryRepository, inMemoryProductRepository, inMemorySupplierRepository, inMemoryCustomerRepository, inMemoryWarehouseRepository);
        ProductService productService = new ProductService(inMemoryProductRepository, inMemoryProductQueryRepository);
        CustomerService customerService = new CustomerService(inMemoryCustomerRepository);
        SupplierService supplierService = new SupplierService(inMemorySupplierRepository);
        WarehouseService warehouseService = new WarehouseService(inMemoryWarehouseRepository);
        SaleService saleService = new SaleService(inMemorySaleRepository, stockService, inMemoryProductRepository, inMemoryCustomerRepository, inMemoryWarehouseRepository);
        ConsoleUI ui = new ConsoleUI(customerService, productService,stockService, supplierService, warehouseService, saleService);
        ui.start();
    }
}
