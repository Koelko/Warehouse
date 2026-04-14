package repositories.jdbc;

import com.mysql.cj.x.protobuf.MysqlxCursor;
import models.Supplier;
import repositories.DatabaseConfig;
import repositories.SupplierRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdbcSupplierRepository implements SupplierRepository {
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
    public Supplier update(Supplier supplier){
        if (supplier == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (supplier.getId() <= 0) {
            throw new IllegalArgumentException("ID проставщика не может быть 0 или отрицательным");
        }
        if (supplier.getName() == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (supplier.getContactInfo() == null) {
            throw new IllegalArgumentException("Ошибка пустая контактная информация");
        }
        String sql = "select * from supplier where id = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, supplier.getId());
            try (ResultSet result = preparedStatement.executeQuery()) {
                if (!result.next()) {
                    throw new IllegalArgumentException("Поставщик не найден");
                }
            }
            String sql1 = "select id from supplier where name = ? and contactInfo = ? and id != ?";
            try (PreparedStatement pre = connection.prepareStatement(sql1)) {
                pre.setString(1, supplier.getName());
                pre.setString(2, supplier.getContactInfo());
                pre.setInt(3,supplier.getId());
                try (ResultSet result = pre.executeQuery()) {
                    if (result.next()) {
                        throw new IllegalArgumentException("Ошибка, поставщик с такими данными существует с другим id");
                    }
                }
            }

            String sql2 = "update supplier set name = ?, contactInfo = ? where id = ?";
            try (PreparedStatement pS = connection.prepareStatement(sql2)) {
                pS.setString(1, supplier.getName());
                pS.setString(2, supplier.getContactInfo());
                pS.setInt(3, supplier.getId());
                int rowsUpdated = pS.executeUpdate();
                if (rowsUpdated != 1) {
                    throw new RuntimeException("Не удалось обновить поставщика");
                }
                return supplier;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Supplier> findById(int id){
        String sql = "select * from supplier where id = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement pS = connection.prepareStatement(sql)){
            pS.setInt(1, id);
            try(ResultSet result = pS.executeQuery()){
                if (!result.next()){
                    return Optional.empty();
                }
                int foundId = result.getInt(1);
                String name = result.getString(2);
                String contactInfo = result.getString(3);
                Supplier supplier = new Supplier(foundId, name, contactInfo);
                return Optional.of(supplier);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при поиске поставщика с id=" + id, e);
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
