package repositories;

import models.Supplier;

import java.sql.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdbcSupplierRepository implements SupplierRepository{
    public Supplier createSupplier(String name, String contactInfo){
        if (name == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (contactInfo == null) {
            throw new IllegalArgumentException("Ошибка пустой адрес");
        }
        String sql = "select id from supplier where name = ? and contactInfo = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, contactInfo);
            try(ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    throw new IllegalArgumentException("Такой поставщик уже есть");
                }
            }
                String sql1 = "insert into supplier(name, contactInfo) values (?, ?)";
                try(PreparedStatement pS = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)){
                    pS.setString(1, name);
                    pS.setString(2, contactInfo);
                    pS.executeUpdate();
                    ResultSet generatedKeys = pS.getGeneratedKeys();
                    if (!generatedKeys.next()) {
                        throw new RuntimeException("Не удалось получить id созданного поставщика");
                    }
                    int id = generatedKeys.getInt(1);
                    Supplier supplier = new Supplier(id, name, contactInfo);
                    return supplier;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void remove(int id){
        String sql = "select * from supplier where id = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement pS = connection.prepareStatement(sql)) {
            pS.setInt(1, id);
            try (ResultSet result = pS.executeQuery()) {
                if (!result.next()) {
                    throw new IllegalArgumentException("Ошибка, поставщика нет");
                }
                String sql1 = "SELECT * FROM stockitem WHERE supplier_id = ?";
                try(PreparedStatement p = connection.prepareStatement(sql1)){
                    p.setInt(1, id);
                    try (ResultSet r = p.executeQuery()){
                        if (r.next()){
                            throw new IllegalArgumentException("Нельзя удалить, т.к. хранится товар на складе");
                        }
                        String sql2 = "delete from supplier where id = ?";
                        try(PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
                            int rowsDeleted = preparedStatement.executeUpdate();
                            if (rowsDeleted != 1) {
                                throw new RuntimeException("Не удалось удалить поставщика");
                            }
                            System.out.println("Поставщик удален");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Supplier findById(int id){
        String sql = "select * from supplier where id = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement pS = connection.prepareStatement(sql)){
            pS.setInt(1, id);
            try(ResultSet result = pS.executeQuery()){
                if (!result.next()){
                    throw new IllegalArgumentException("Ошибка, поставщика нет");
                }
                int foundId = result.getInt(1);
                String name = result.getString(2);
                String contactInfo = result.getString(3);
                Supplier supplier = new Supplier(foundId, name, contactInfo);
                return supplier;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Supplier> findAll() {
        String sql = "select * from supplier";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet result = preparedStatement.executeQuery()) {
            List<Supplier> suppliers = new CopyOnWriteArrayList<>();
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                String contactInfo = result.getString(3);
                Supplier s = new Supplier(id, name, contactInfo);
                suppliers.add(s);
            }
            return suppliers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
