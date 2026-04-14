package servlets;

import repositories.*;
import repositories.jdbc.*;
import services.*;

public class AppConfig {
    private static ProductRepository productRepository = new JdbcProductRepository();
    private static ProductQueryRepository productQueryRepository = new JdbcProductQueryRepository();
    private static CustomerRepository customerRepository = new JdbcCustomerRepository();
    private static SupplierRepository supplierRepository = new JdbcSupplierRepository();
    private static WarehouseRepository warehouseRepository = new JdbcWarehouseRepository();
    private static StockRepository stockRepository = new JdbcStockRepository();
    private static StockQueryRepository stockQueryRepository = new JdbcStockQueryRepository();
    private static SaleRepository saleRepository = new JdbcSaleRepository();
    public static ProductService getProductService(){
        return new ProductService(productRepository, productQueryRepository);
    }
    public static CustomerService getCustomerService(){
        return new CustomerService(customerRepository);
    }
    public static SupplierService getSupplierService(){
        return new SupplierService(supplierRepository);
    }
    public static WarehouseService getWarehouseService(){
        return new WarehouseService(warehouseRepository);
    }
    public static StockService getStockService(){
        return new StockService(stockRepository, stockQueryRepository, productRepository, supplierRepository, customerRepository, warehouseRepository);
    }
    public static SaleService getSaleService(){
        return new SaleService(saleRepository, getStockService(), productRepository, customerRepository, warehouseRepository);
    }
    public static ProductRepository getProductRepository(){
        return productRepository;
    }
    public static CustomerRepository getCustomerRepository(){
        return customerRepository;
    }
    public static WarehouseRepository getWarehouseRepository(){
        return warehouseRepository;
    }
    public static SupplierRepository getSupplierRepository(){
        return supplierRepository;
    }
}
