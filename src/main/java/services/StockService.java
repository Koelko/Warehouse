package services;

import models.*;
import repositories.*;

import java.util.List;
import java.util.stream.Collectors;

public class StockService {
    private StockRepository stockRepository;
    private StockQueryRepository stockQueryRepository;
    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;
    private CustomerRepository customerRepository;
    private WarehouseRepository warehouseRepository;

    public StockService(StockRepository stockRepository, StockQueryRepository stockQueryRepository, ProductRepository productRepository, SupplierRepository supplierRepository, CustomerRepository customerRepository, WarehouseRepository warehouseRepository) {
        this.stockRepository = stockRepository;
        this.stockQueryRepository = stockQueryRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.customerRepository = customerRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public void acceptGoods(int productId, int supplierId, int warehouseId, int count){
        Product product = productRepository.findById(productId);
        if (product == null){
            throw new IllegalArgumentException("Нет такого продукта");
        }
        Supplier supplier = supplierRepository.findById(supplierId);
        if (supplier == null){
            throw new IllegalArgumentException("Нет такого поставщика");
        }
        Warehouse warehouse = warehouseRepository.findById(warehouseId);
        if (warehouse == null){
            throw new IllegalArgumentException("Нет такого склада");
        }
        for (StockItem stockItem : stockRepository.findAll()){
            if (stockItem.getProductId() == productId && stockItem.getSupplierId() == supplierId && stockItem.getWarehouseId() == warehouseId){
                stockItem.setCount(stockItem.getCount() + count);
                return;
            }
        }
        stockRepository.createStockItem(count, productId, supplierId, warehouseId);
    }
    public void sell(int productId, int customerId, int warehouseId, int count){
        Product product = productRepository.findById(productId);
        if (product == null){
            throw new IllegalArgumentException("Нет такого продукта");
        }
        Customer customer = customerRepository.findById(customerId);
        if (customer == null){
            throw new IllegalArgumentException("Нет такого покупателя");
        }
        Warehouse warehouse = warehouseRepository.findById(warehouseId);
        if (warehouse == null){
            throw new IllegalArgumentException("Нет такого склада");
        }
        List<StockItem> batches = stockRepository.findAll().stream()
                .filter(item -> item.getProductId() == productId)
                .filter(item -> item.getWarehouseId() == warehouseId)
                .collect(Collectors.toList());
        int total = batches.stream().mapToInt(StockItem::getCount).sum();
        if (total < count) {
            throw new IllegalArgumentException("Недостаточно товара на складе");
        }
        int remaining = count;
        for (StockItem batch : batches) {
            if (remaining <= 0) break;
            int available = batch.getCount();
            int toSell = Math.min(available, remaining);
            batch.setCount(available - toSell);
            remaining -= toSell;
        }
    }
    public List<StockItem> getAvailable(int productId){
        Product product = productRepository.findById(productId);
        if (product == null){
            throw new IllegalArgumentException("Товар не найден");
        }
        return stockQueryRepository.findByProductId(productId);
    }
    public String getStockStatus(int productId){
        Product product = productRepository.findById(productId);
        if (product == null){
            throw new IllegalArgumentException("Товар не найден");
        }
        int total = stockQueryRepository.getTotalCount(productId);
        if (total == 0){
            return "Товар отсутствует на складе";
        }else{
            return "Товар в наличии. Общее количество: " + total;
        }
    }
    public List<StockItem> getAllStock() {
        return stockRepository.findAll();
    }
}
