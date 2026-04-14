package repositories.jdbc;

import models.Warehouse;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import repositories.DatabaseConfig;
import repositories.WarehouseRepository;

public class JdbcWarehouseRepository implements WarehouseRepository {
    public Warehouse createWarehouse(String name, String address) {
        if (name == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (address == null) {
            throw new IllegalArgumentException("Ошибка пустой адрес");
        }
        String sql1 = "select id from warehouse where name = ? and address = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement pS = connection.prepareStatement(sql1);) {

            pS.setString(1, name);
            pS.setString(2, address);
            try (ResultSet r = pS.executeQuery()) {
                if (r.next()) {
                    throw new IllegalArgumentException("Такой склад уже есть");
                }
            }
            String sql = "insert into warehouse(name, address) values (?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new RuntimeException("Не удалось получить id созданного склада");
                }
                int id = generatedKeys.getInt(1);
                Warehouse warehouse = new Warehouse(id, name, address);
                return warehouse;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при созданиии склада", e);
        }
    }


    @Override
    public Warehouse update(Warehouse warehouse){
        if (warehouse == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (warehouse.getId() <= 0) {
            throw new IllegalArgumentException("ID проставщика не может быть 0 или отрицательным");
        }
        if (warehouse.getName() == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (warehouse.getAddress() == null) {
            throw new IllegalArgumentException("Ошибка пустая контактная информация");
        }
        String sql = "select * from warehouse where id = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, warehouse.getId());
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (!result.next()) {
                    throw new IllegalArgumentException("Поставщик не найден");
                }
            }
            String sql1 = "select id from warehouse where name = ? and address = ? and id != ?";
            try (PreparedStatement pre = connection.prepareStatement(sql1)) {
                pre.setString(1, warehouse.getName());
                pre.setString(2, warehouse.getAddress());
                pre.setInt(3,warehouse.getId());
                try (ResultSet result = pre.executeQuery()) {
                    if (result.next()) {
                        throw new IllegalArgumentException("Ошибка, склад с такими данными существует с другим id");
                    }
                }
            }

            String sql2 = "update warehouse set name = ?, address = ? where id = ?";
            try (PreparedStatement pS = connection.prepareStatement(sql2)) {
                pS.setString(1, warehouse.getName());
                pS.setString(2, warehouse.getAddress());
                pS.setInt(3, warehouse.getId());
                int rowsUpdated = pS.executeUpdate();
                if (rowsUpdated != 1) {
                    throw new RuntimeException("Не удалось обновить склад");
                }
                return warehouse;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Warehouse> findById(int id) {
        String sql = "select * from warehouse where id = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (!result.next()) {
                    return Optional.empty();
                }
                int foundId = result.getInt(1);
                String name = result.getString(2);
                String address = result.getString(3);
                Warehouse warehouse = new Warehouse(foundId, name, address);
                return Optional.of(warehouse);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при поиске склада с id=" + id, e);
        }
    }

    public List<Warehouse> findAll() {
        String sql = "select * from warehouse";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet result = preparedStatement.executeQuery()) {
            List<Warehouse> warehouses = new CopyOnWriteArrayList<>();
            while(result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                String address = result.getString(3);
                Warehouse w = new Warehouse(id, name, address);
                warehouses.add(w);
            }
            return warehouses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
