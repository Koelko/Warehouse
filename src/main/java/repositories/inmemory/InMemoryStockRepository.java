package repositories.inmemory;

import models.*;
import repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryStockRepository implements StockRepository {
    private List<StockItem> stockItems = new CopyOnWriteArrayList<>();
    private int nextId = 1;
    public InMemoryStockRepository() {

    }
    public StockItem createStockItem(int count, int productId, int supplierId, int warehouseId){
        StockItem stockItem =  new StockItem(nextId++, count, productId, supplierId, warehouseId);
        stockItems.add(stockItem);
        return stockItem;
    }
    public void remove(int id){
        stockItems.removeIf(stockItem-> stockItem.getId() == id);
    }
    public Optional<StockItem> findById(int id){
        for (StockItem stockItem : stockItems) {
            if (stockItem.getId() == id){
                return Optional.of(stockItem);
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
        throw new IllegalArgumentException("StockItem с id" + stockItem.getId() + " не найден");
    }
    public List<StockItem> findAll() {
        return new ArrayList<>(stockItems);
    }
}
