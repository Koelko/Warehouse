package repositories;

import models.StockItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdbcStockQueryRepository implements StockQueryRepository{
    @Override
    public List<StockItem> findByProductId(int productId) {
        if(productId <=0) {
            throw new IllegalArgumentException("Ошибка");
        }
        List<StockItem> result = new CopyOnWriteArrayList<>();
        String sql = "select * from stockitem where product_id = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,productId);
            try (ResultSet res = ps.executeQuery()){
                while (res.next()) {
                    int id = res.getInt("id");
                    int count = res.getInt("count");
                    int prodId = res.getInt("product_id");
                    int suppId = res.getInt("supplier_id");
                    int wareId = res.getInt("warehouse_id");
                    result.add(new StockItem(id, count, prodId, suppId, wareId));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StockItem> findBySupplierId(int supplierId) {
        if(supplierId <=0) {
            throw new IllegalArgumentException("Ошибка");
        }
        List<StockItem> result = new CopyOnWriteArrayList<>();
        String sql = "select * from stockitem where supplier_id = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, supplierId);
            try (ResultSet res = ps.executeQuery()){
                while (res.next()) {
                    int id = res.getInt("id");
                    int count = res.getInt("count");
                    int prodId = res.getInt("product_id");
                    int suppId = res.getInt("supplier_id");
                    int wareId = res.getInt("warehouse_id");
                    result.add(new StockItem(id, count, prodId, suppId, wareId));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalCount(int productId) {
        if(productId <=0) {
            throw new IllegalArgumentException("Ошибка");
        }
        String sql = "select sum(count) from stockitem where product_id = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, productId);
            try (ResultSet res = ps.executeQuery()){
                if (res.next()) {
                    return res.getInt(1);
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
