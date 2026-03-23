package repositories;

import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryStockRepository implements StockRepository{
    private List<StockItem> stockItems = new CopyOnWriteArrayList<>();
    private int nextId = 1;
    private InMemoryProductRepository inMemoryProductRepository;
    private InMemorySupplierRepository inMemorySupplierRepository;
    private InMemoryWarehouseRepository inMemoryWarehouseRepository;

    public InMemoryStockRepository(InMemoryProductRepository inMemoryProductRepository, InMemorySupplierRepository inMemorySupplierRepository, InMemoryWarehouseRepository inMemoryWarehouseRepository) {
        this.inMemoryProductRepository = inMemoryProductRepository;
        this.inMemorySupplierRepository = inMemorySupplierRepository;
        this.inMemoryWarehouseRepository = inMemoryWarehouseRepository;
    }
    public StockItem createStockItem(int count, Product product, Supplier supplier, Warehouse warehouse){
        StockItem stockItem =  new StockItem(nextId++, count, product, supplier, warehouse);
        stockItems.add(stockItem);
        return stockItem;
    }
    public void remove(int id){
        stockItems.removeIf(stockItem-> stockItem.getId() == id);
    }
    public StockItem findById(int id){
        for (StockItem stockItem : stockItems) {
            if (stockItem.getId() == id){
                return stockItem;
            }
        }
        return null;
    }
    public StockItem update(StockItem stockItem){
        for (int i = 0; i < stockItems.size(); i++){
            if(stockItems.get(i).getId() == stockItem.getId()){
                stockItems.set(i, stockItem);
                return stockItem;
            }
        }
        throw new IllegalArgumentException("StockItem с id \" + stockItem.getId() + \" не найден");
    }
    public List<StockItem> findAll() {
        return new ArrayList<>(stockItems);
    }
}
