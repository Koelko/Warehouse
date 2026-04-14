package repositories;

import models.Product;
import models.StockItem;
import models.Supplier;
import models.Warehouse;

import java.util.List;
import java.util.Optional;

public interface StockRepository {
    StockItem createStockItem(int count, int productId, int supplierId, int warehouseId);
    StockItem update(StockItem stockItem);
    Optional<StockItem> findById(int id);
    List<StockItem> findAll();
}
