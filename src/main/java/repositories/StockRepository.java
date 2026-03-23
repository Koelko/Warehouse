package repositories;

import models.Product;
import models.StockItem;
import models.Supplier;
import models.Warehouse;

import java.util.List;

public interface StockRepository {
    StockItem createStockItem(int count, Product product, Supplier supplier, Warehouse warehouse);
    void remove(int id);
    StockItem update(StockItem stockItem);
    List<StockItem> findAll();
}
