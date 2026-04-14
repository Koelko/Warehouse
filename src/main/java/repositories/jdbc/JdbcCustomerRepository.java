package repositories.jdbc;

import models.Customer;
import models.Supplier;
import repositories.CustomerRepository;
import repositories.DatabaseConfig;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdbcCustomerRepository implements CustomerRepository {
    public Customer createCustomer(String name, String contactInfo){
        if (name == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        String sql = "select id from customer where name = ? and contactInfo = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, contactInfo);
            try(ResultSet result = preparedStatement.executeQuery()) {
                if (result.next()) {
                    throw new IllegalArgumentException("Такой покупатель уже есть");
                }
            }
            String sql1 = "insert into customer(name, contactInfo) values (?, ?)";
            try(PreparedStatement pS = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)){
                pS.setString(1, name);
                pS.setString(2, contactInfo);
                pS.executeUpdate();
                ResultSet generatedKeys = pS.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new RuntimeException("Не удалось получить id созданного покупателя");
                }
                int id = generatedKeys.getInt(1);
                Customer customer = new Customer(id, name, contactInfo);
                return customer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer update(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        if (customer.getId() <= 0) {
            throw new IllegalArgumentException("ID покупателя не может быть 0 или отрицательным");
        }
        if (customer.getName() == null) {
            throw new IllegalArgumentException("Ошибка пустое имя");
        }
        String sql = "select * from customer where id = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, customer.getId());
            try(ResultSet result = preparedStatement.executeQuery()) {
                if (!result.next()) {
                    throw new IllegalArgumentException("Покупатель не найден");
                }
            }
            String sql1 = "update customer set name = ?, contactInfo = ? WHERE id = ?";
            try(PreparedStatement pS = connection.prepareStatement(sql1)){
                pS.setString(1, customer.getName());
                if (customer.getContactInfo() == null) {
                    pS.setNull(2, Types.VARCHAR);
                } else {
                    pS.setString(2, customer.getContactInfo());
                }
                pS.setInt(3, customer.getId());
                int rowsUpdated = pS.executeUpdate();
                if (rowsUpdated != 1) {
                    throw new RuntimeException("Не удалось обновить покупателя");
                }
                return customer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Customer> findById(int id){
        String sql = "select * from customer where id = ?";
        try(Connection connection = DatabaseConfig.getConnection(); PreparedStatement pS = connection.prepareStatement(sql)){
            pS.setInt(1, id);
            try(ResultSet result = pS.executeQuery()){
                if (!result.next()){
                    return Optional.empty();
                }
                int foundId = result.getInt(1);
                String name = result.getString(2);
                String contactInfo = result.getString(3);
                Customer customer = new Customer(foundId, name, contactInfo);
                return Optional.of(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД при поиске покупателя с id=" + id, e);
        }
    }
    public List<Customer> findAll() {
        String sql = "select * from customer";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet result = preparedStatement.executeQuery()) {
            List<Customer> customers = new CopyOnWriteArrayList<>();
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                String contactInfo = result.getString(3);
                Customer c = new Customer(id, name, contactInfo);
                customers.add(c);
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка БД", e);
        }
    }
}
