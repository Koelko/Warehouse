package repositories;

import models.StockItem;

import java.util.ArrayList;
import java.util.List;

public class InMemoryStockQueryRepository implements StockQueryRepository{
    private InMemoryStockRepository inMemoryStockRepository;
    public InMemoryStockQueryRepository(InMemoryStockRepository inMemoryStockRepository){
        this.inMemoryStockRepository = inMemoryStockRepository;
    }
    public List<StockItem> findByProductId(int productId){
        List<StockItem> result = new ArrayList<>();
        for (StockItem stockItem : inMemoryStockRepository.findAll()) {
            if (stockItem.getProductId() == productId){
                result.add(stockItem);
            }
        }
        return result;
    }
    public List<StockItem> findBySupplierId (int supplierId){
        List<StockItem> result = new ArrayList<>();
        for (StockItem stockItem : inMemoryStockRepository.findAll()) {
            if (stockItem.getSupplierId() == supplierId){
                result.add(stockItem);
            }
        }
        return result;
    }
    public int getTotalCount(int productId){
        int total = 0;
        for (StockItem stockItem : inMemoryStockRepository.findAll()) {
            if (stockItem.getProductId() == productId){
                total += stockItem.getCount();
            }
        }
        return total;
    }
}
