package services;

import models.*;
import repositories.*;

import java.util.List;
import java.util.stream.Collectors;

public class StockService {
    private InMemoryStockRepository inMemoryStockRepository;
    private InMemoryStockQueryRepository inMemoryStockQueryRepository;
    private InMemoryProductRepository inMemoryProductRepository;
    private InMemorySupplierRepository inMemorySupplierRepository;
    private InMemoryCustomerRepository inMemoryCustomerRepository;
    private InMemoryWarehouseRepository inMemoryWarehouseRepository;

    public StockService(InMemoryStockRepository inMemoryStockRepository, InMemoryStockQueryRepository inMemoryStockQueryRepository, InMemoryProductRepository inMemoryProductRepository, InMemorySupplierRepository inMemorySupplierRepository, InMemoryCustomerRepository inMemoryCustomerRepository, InMemoryWarehouseRepository inMemoryWarehouseRepository) {
        this.inMemoryStockRepository = inMemoryStockRepository;
        this.inMemoryStockQueryRepository = inMemoryStockQueryRepository;
        this.inMemoryProductRepository = inMemoryProductRepository;
        this.inMemorySupplierRepository = inMemorySupplierRepository;
        this.inMemoryCustomerRepository = inMemoryCustomerRepository;
        this.inMemoryWarehouseRepository = inMemoryWarehouseRepository;
    }

    public void acceptGoods(int productId, int supplierId, int warehouseId, int count){
        Product product = inMemoryProductRepository.findById(productId);
        if (product == null){
            throw new IllegalArgumentException("Нет такого продукта");
        }
        Supplier supplier = inMemorySupplierRepository.findById(supplierId);
        if (supplier == null){
            throw new IllegalArgumentException("Нет такого поставщика");
        }
        Warehouse warehouse = inMemoryWarehouseRepository.findById(warehouseId);
        if (warehouse == null){
            throw new IllegalArgumentException("Нет такого склада");
        }
        for (StockItem stockItem : inMemoryStockRepository.findAll()){
            if (stockItem.getProductId() == productId && stockItem.getSupplierId() == supplierId && stockItem.getWarehouseId() == warehouseId){
                stockItem.setCount(stockItem.getCount() + count);
                return;
            }
        }
        inMemoryStockRepository.createStockItem(count, productId, supplierId, warehouseId);
    }
    public void sell(int productId, int customerId, int warehouseId, int count){
        Product product = inMemoryProductRepository.findById(productId);
        if (product == null){
            throw new IllegalArgumentException("Нет такого продукта");
        }
        Customer customer = inMemoryCustomerRepository.findById(customerId);
        if (customer == null){
            throw new IllegalArgumentException("Нет такого покупателя");
        }
        Warehouse warehouse = inMemoryWarehouseRepository.findById(warehouseId);
        if (warehouse == null){
            throw new IllegalArgumentException("Нет такого склада");
        }
        List<StockItem> batches = inMemoryStockRepository.findAll().stream()
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
        Product product = inMemoryProductRepository.findById(productId);
        if (product == null){
            throw new IllegalArgumentException("Товар не найден");
        }
        return inMemoryStockQueryRepository.findByProductId(productId);
    }
    public String getStockStatus(int productId){
        Product product = inMemoryProductRepository.findById(productId);
        if (product == null){
            throw new IllegalArgumentException("Товар не найден");
        }
        int total = inMemoryStockQueryRepository.getTotalCount(productId);
        if (total == 0){
            return "Товар отсутствует на складе";
        }else{
            return "Товар в наличии. Общее количество: " + total;
        }
    }
    public List<StockItem> getAllStock() {
        return inMemoryStockRepository.findAll();
    }
}
