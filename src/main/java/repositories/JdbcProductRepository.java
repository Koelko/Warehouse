package repositories;

import models.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdbcProductRepository implements ProductRepository {
    public Product createProduct(String name, String category, BigDecimal price, String manufacturer) {
        if (name == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (category == null) {
            throw new IllegalArgumentException("Ошибка пустая категория");
        }
        if (price == null) {
            throw new IllegalArgumentException("Цена не может быть null");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена должна быть положительной");
        }
        if (manufacturer == null) {
            throw new IllegalArgumentException("Ошибка пустой производитель");
        }
        String sql = "select id from product where name = ? and category = ? and manufacturer = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, manufacturer);
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");

                    String updateSql = "update product set price = ? WHERE id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                        updateStmt.setBigDecimal(1, price);
                        updateStmt.setInt(2, id);
                        updateStmt.executeUpdate();
                    }

                    return new Product(id, name, category, price, manufacturer);
                }
            }
            String sql1 = "insert into product(name, category, price, manufacturer) values (?, ?, ?, ?)";
            try (PreparedStatement pS = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
                pS.setString(1, name);
                pS.setString(2, category);
                pS.setBigDecimal(3, price);
                pS.setString(4, manufacturer);
                pS.executeUpdate();
                ResultSet generatedKeys = pS.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new RuntimeException("Не удалось получить id созданного продукта");
                }
                int id = generatedKeys.getInt(1);
                return new Product(id, name, category, price, manufacturer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product findById(int id) {
        String sql = "select * from product where id = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement pS = connection.prepareStatement(sql)) {
            pS.setInt(1, id);
            try (ResultSet result = pS.executeQuery()) {
                if (!result.next()) {
                    throw new IllegalArgumentException("Ошибка, продукта нет");
                }
                int foundId = result.getInt(1);
                String name = result.getString(2);
                String category = result.getString(3);
                BigDecimal price = result.getBigDecimal(4);
                String manufacturer = result.getString(5);
                Product product = new Product(foundId, name, category, price, manufacturer);
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product update(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (product.getId() <= 0) {
            throw new IllegalArgumentException("ID покупателя не может быть 0 или отрицательным");
        }
        if (product.getName() == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Ошибка пустая категория");
        }
        if (product.getPrice() == null) {
            throw new IllegalArgumentException("Ошибка пустая цена");
        }
        if (product.getManufacturer() == null) {
            throw new IllegalArgumentException("Ошибка пустой производитель");
        }
        String sql = "select * from product where id = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, product.getId());
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (!result.next()) {
                    throw new IllegalArgumentException("Продукт не найден");
                }
            }
            String sql1 = "select id from product where name = ? and category = ? and manufacturer = ? and id != ?";
            try (PreparedStatement pre = connection.prepareStatement(sql1)) {
                pre.setString(1, product.getName());
                pre.setString(2, product.getCategory());
                pre.setString(3, product.getManufacturer());
                try (ResultSet result = pre.executeQuery()) {
                    if (result.next()) {
                        throw new IllegalArgumentException("Ошибка, товар с такими данными существует с другим id");
                    }
                }
            }

            String sql2 = "update product set name = ?, category = ?, price = ?, manufacturer = ? WHERE id = ?";
            try (PreparedStatement pS = connection.prepareStatement(sql2)) {
                pS.setString(1, product.getName());
                pS.setString(2, product.getCategory());
                pS.setBigDecimal(3, product.getPrice());
                pS.setString(4, product.getManufacturer());
                pS.setInt(5, product.getId());
                int rowsUpdated = pS.executeUpdate();
                if (rowsUpdated != 1) {
                    throw new RuntimeException("Не удалось обновить товар");
                }
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> findAll() {
        String sql = "select * from product";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet result = preparedStatement.executeQuery()) {
            List<Product> products = new CopyOnWriteArrayList<>();
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                String category = result.getString(3);
                BigDecimal price = result.getBigDecimal(4);
                String manufacturer = result.getString(5);
                Product p = new Product(id, name, category, price, manufacturer);
                products.add(p);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
