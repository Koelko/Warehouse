package repositories.jdbc;

import models.Product;
import models.Supplier;
import repositories.DatabaseConfig;
import repositories.ProductQueryRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class JdbcProductQueryRepository implements ProductQueryRepository {


    @Override
    public List<Product> findByCategory(String category) {
        if (category == null){
            throw new IllegalArgumentException("Ошибка пустая категория");
        }
        String sql = "select * from product where category = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, category);
            try(ResultSet result = preparedStatement.executeQuery()){
                List<Product> products = new ArrayList<>();
                while(result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String cat = result.getString("category");
                    BigDecimal price = result.getBigDecimal("price");
                    String manufacturer = result.getString("manufacturer");
                    Product p = new Product(id, name, cat, price, manufacturer);
                    products.add(p);
                }
                return products;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findByManufacturer(String manufacturer) {
        if (manufacturer == null){
            throw new IllegalArgumentException("Ошибка пустой производитель");
        }
        String sql = "select * from product where manufacturer = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, manufacturer);
            try(ResultSet result = preparedStatement.executeQuery()){
                List<Product> products = new ArrayList<>();
                while(result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String category = result.getString("category");
                    BigDecimal price = result.getBigDecimal("price");
                    String manufactur = result.getString("manufacturer");
                    Product p = new Product(id, name, category, price, manufactur);
                    products.add(p);
                }
                return products;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if(minPrice == null || minPrice.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Цена не может быть отрицательной или пустой");
        }
        if(maxPrice == null || maxPrice.compareTo(minPrice) < 0){
            throw new IllegalArgumentException("Цена не может быть отрицательной или пустой");
        }
        String sql = "select * from product where price between ? and ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setBigDecimal(1, minPrice);
            preparedStatement.setBigDecimal(2,maxPrice);
            try(ResultSet res = preparedStatement.executeQuery()){
                List<Product> products = new ArrayList<>();
                while (res.next()){int id = res.getInt("id");
                    String name = res.getString("name");
                    String category = res.getString("category");
                    BigDecimal price = res.getBigDecimal("price");
                    String manufacturer = res.getString("manufacturer");
                    Product p = new Product(id, name, category, price, manufacturer);
                    products.add(p);
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findByName(String name) {
        if (name == null){
            throw new IllegalArgumentException("Ошибка пустой производитель");
        }
        String sql = "select * from product where name = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            try(ResultSet result = preparedStatement.executeQuery()){
                List<Product> products = new ArrayList<>();
                while(result.next()){
                    int id = result.getInt("id");
                    String nameDB = result.getString("name");
                    String category = result.getString("category");
                    BigDecimal price = result.getBigDecimal("price");
                    String manufactur = result.getString("manufacturer");
                    Product p = new Product(id, nameDB, category, price, manufactur);
                    products.add(p);
                }
                return products;
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findByFilters(String name, String category, String manufacturer, BigDecimal minPrice, BigDecimal maxPrice) {
        StringBuilder sql = new StringBuilder("select * from product where 1 = 1");
        List<Object> list = new ArrayList<>();
        if (name != null) {
            sql.append(" and name like ?");
            list.add("%" + name + "%");
        }
        if (category != null) {
            sql.append(" and category = ?");
            list.add(category);
        }
        if (manufacturer != null) {
            sql.append(" and manufacturer = ?");
            list.add(manufacturer);
        }
        if (minPrice != null && minPrice.compareTo(BigDecimal.ZERO) >= 0) {
            sql.append(" and price >= ?");
            list.add(minPrice);
        }

        if (maxPrice != null && maxPrice.compareTo(minPrice) > 0) {
            sql.append(" and price <= ?");
            list.add(maxPrice);
        }
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement pr = connection.prepareStatement(sql.toString())){
            for (int i = 1; i < list.size() + 1; i++){
                pr.setObject(i, list.get(i - 1));
            }
            List<Product> products = new ArrayList<>();
            try(ResultSet res = pr.executeQuery()){
                while(res.next()){
                    Product product = new Product(res.getInt( "id"), res.getString("name"), res.getString("category"), res.getBigDecimal("price"), res.getString("manufacturer"));
                    products.add(product);
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Map<String, List<Product>> groupByCategory() {
        String sql = "select * from product";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement pr = connection.prepareStatement(sql)){
            List<Product> products = new ArrayList<>();
            try(ResultSet res = pr.executeQuery()){
                while(res.next()){
                    Product product = new Product(res.getInt("id"), res.getString("name"), res.getString("category"), res.getBigDecimal("price"), res.getString("manufacturer"));
                    products.add(product);
                }
                Map <String, List<Product>> result = products.stream().collect(Collectors.groupingBy(Product :: getCategory));
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

