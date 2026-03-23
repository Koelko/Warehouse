package repositories;

import models.StockItem;

import java.util.List;

public interface StockQueryRepository {
    List<StockItem> findByProductId(int productId);
    List<StockItem> findBySupplierId (int supplierId);
    int getTotalCount(int productId);

}
