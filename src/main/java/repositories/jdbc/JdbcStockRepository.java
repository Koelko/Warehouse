package repositories.jdbc;

import models.*;
import repositories.DatabaseConfig;
import repositories.StockRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdbcStockRepository implements StockRepository {

    @Override
    public StockItem createStockItem(int count, int productId, int supplierId, int warehouseId) {
        if (count <= 0) {
            throw new IllegalArgumentException("Ошибка неправильное количество");
        }
        if (productId <= 0) {
            throw new IllegalArgumentException("Ошибка, продукт не может быть пустым");
        }
        if (supplierId <= 0) {
            throw new IllegalArgumentException("Ошибка, поставщик не может не быть");
        }
        if (warehouseId <= 0) {
            throw new IllegalArgumentException("Ошибка, склад не может не быть");
        }
        String sql = "insert into stockitem(count, product_id, supplier_id, warehouse_id) values (?, ?, ?, ?)";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, supplierId);
            preparedStatement.setInt(4, warehouseId);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (!generatedKeys.next()) {
                throw new RuntimeException("Не удалось получить id созданной партии");
            }
            int id = generatedKeys.getInt(1);
            StockItem stockItem = new StockItem(id, count, productId, supplierId, warehouseId);
            return stockItem;
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public StockItem update(StockItem stockItem) {
        if (stockItem == null) {
            throw new IllegalArgumentException("StockItem не может быть null");
        }
        if (stockItem.getId() <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным");
        }
        if (stockItem.getCount() <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным");
        }

        String sql = "update stockitem set count = ? where id = ?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, stockItem.getCount());
            preparedStatement.setInt(2, stockItem.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated != 1) {
                throw new RuntimeException("Не удалось обновить партию с id " + stockItem.getId());
            }
            return stockItem;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<StockItem> findById(int id) {
        String sql = "select * from stockitem where id = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement pS = connection.prepareStatement(sql)) {
            pS.setInt(1, id);
            try (ResultSet result = pS.executeQuery()) {
                if (!result.next()) {
                    return Optional.empty();
                }
                int foundId = result.getInt(1);
                int count = result.getInt(2);
                int productId = result.getInt(3);
                int supplierId = result.getInt(4);
                int warehouseId = result.getInt(5);
                StockItem stockItem = new StockItem(foundId, count, productId, supplierId, warehouseId);
                return Optional.of(stockItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при поиске остатка с id=" + id, e);
        }
    }

    @Override
    public List<StockItem> findAll() {
        String sql = "select * from stockitem";
        List<StockItem> items = new CopyOnWriteArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet result = preparedStatement.executeQuery()) {

            while (result.next()) {
                int id = result.getInt("id");
                int count = result.getInt("count");
                int productId = result.getInt("product_id");
                int supplierId = result.getInt("supplier_id");
                int warehouseId = result.getInt("warehouse_id");
                items.add(new StockItem(id, count, productId, supplierId, warehouseId));
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
