package repositories;

import models.Customer;
import models.Product;
import models.Sale;
import models.SaleItem;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JdbcSaleRepository implements SaleRepository {

    @Override
    public Sale createSale(int customerId, List<SaleItem> saleItems) {
        if (saleItems.isEmpty()) {
            throw new IllegalArgumentException("Ошибка, ни один товар не продан");
        }
        String sql = "insert into sale(customer_id) values(?)";
        try (Connection connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement pr = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pr.setInt(1, customerId);
                pr.executeUpdate();
                ResultSet generatedKeys = pr.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new RuntimeException("Не удалось получить id созданной продажи");
                }
                int sale_id = generatedKeys.getInt(1);
                String sql1 = "insert into saleitem(product_id, count, priceAtSale, sale_id, warehouse_id) values (?, ?, ?, ?, ?)";
                try (PreparedStatement pS = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
                    for (SaleItem s : saleItems) {
                        pS.setInt(1, s.getProductId());
                        pS.setInt(2, s.getCount());
                        pS.setBigDecimal(3, s.getPriceAtSale());
                        pS.setInt(4, sale_id);
                        pS.setInt(5, s.getWarehouseId());
                        pS.executeUpdate();
                    }
                    connection.commit();
                    Sale sale = new Sale(sale_id, LocalDateTime.now(), customerId, saleItems);
                    return sale;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Sale findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id не может быть отрицательным");
        }
        String sql = "select * from sale where id = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setInt(1, id);
            try (ResultSet res = pr.executeQuery()) {
                if (!res.next()) {
                    throw new IllegalArgumentException("Ошибка, продажи нет");
                }
                int foundId = res.getInt("id");
                String sql1 = "select * from saleitem where sale_id = ?";
                try (PreparedStatement pS = connection.prepareStatement(sql1)) {
                    pS.setInt(1, foundId);
                    try (ResultSet result = pS.executeQuery()) {
                        List<SaleItem> saleitems = new ArrayList<>();
                        while (result.next()) {
                            SaleItem saleItem = new SaleItem(result.getInt("id"), result.getInt("product_id"), result.getInt("count"), result.getBigDecimal("priceAtSale"), result.getInt("warehouse_id"));
                            saleitems.add(saleItem);

                        }
                        Sale sale = new Sale(foundId, res.getTimestamp("date").toLocalDateTime(), res.getInt("customer_id"), saleitems);
                        return sale;
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Sale> findAll() {
        String sql = "select * from sale";
        List<Sale> sales = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement pr = connection.prepareStatement(sql); ResultSet res = pr.executeQuery()) {
            while (res.next()) {
                int id = res.getInt("id");
                LocalDateTime date = res.getTimestamp("date").toLocalDateTime();
                int customerId = res.getInt("customer_id");
                String sql1 = "select * from saleitem where sale_id = ?";
                try (PreparedStatement pS = connection.prepareStatement(sql1)) {
                    pS.setInt(1, id);
                    try (ResultSet result = pS.executeQuery()) {
                        List<SaleItem> saleItems = new ArrayList<>();
                        while (result.next()) {
                            SaleItem saleItem = new SaleItem(result.getInt("id"), result.getInt("product_id"), result.getInt("count"), result.getBigDecimal("priceAtSale"), result.getInt("warehouse_id"));
                            saleItems.add(saleItem);
                        }
                        sales.add(new Sale(id, date, customerId, saleItems));
                    }
                }
            }
            return sales;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Sale> findByCustomer(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("id покупателя должен существовать");
        }
        String sql = "select * from sale where customer_id = ?";
        List<Sale> sales = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setInt(1, customerId);
            try (ResultSet res = pr.executeQuery()) {
                while (res.next()) {
                    int saleId = res.getInt("id");
                    String sql1 = "select * from saleitem where sale_id = ?";
                    try (PreparedStatement pS = connection.prepareStatement(sql1)) {
                        pS.setInt(1, saleId);
                        try (ResultSet resultSet = pS.executeQuery()) {
                            List<SaleItem> saleItems = new ArrayList<>();
                            while (resultSet.next()) {
                                SaleItem saleItem = new SaleItem(resultSet.getInt("id"), resultSet.getInt("product_id"), resultSet.getInt("count"), resultSet.getBigDecimal("priceAtSale"), resultSet.getInt("warehouse_id"));
                                saleItems.add(saleItem);
                            }
                            sales.add(new Sale(saleId, res.getTimestamp("date").toLocalDateTime(), customerId, saleItems));
                        }
                    }
                }
                return sales;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Sale> findByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Ошибка пустая дата");
        }
        List<Sale> sales = new ArrayList<>();
        String sql = "select * from sale where date = ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setTimestamp(1, Timestamp.valueOf(date.atStartOfDay()));
            try (ResultSet res = pr.executeQuery()) {
                while (res.next()) {
                    int saleId = res.getInt("id");
                    String sql1 = "select * from saleitem where sale_id = ?";
                    try (PreparedStatement pS = connection.prepareStatement(sql1)) {
                        pS.setInt(1, saleId);
                        try (ResultSet resultSet = pS.executeQuery()) {
                            List<SaleItem> saleItems = new ArrayList<>();
                            while (resultSet.next()) {
                                SaleItem saleItem = new SaleItem(resultSet.getInt("id"), resultSet.getInt("product_id"), resultSet.getInt("count"), resultSet.getBigDecimal("priceAtSale"), resultSet.getInt("warehouse_id"));
                                saleItems.add(saleItem);
                            }
                            sales.add(new Sale(saleId, res.getTimestamp("date").toLocalDateTime(), res.getInt("customer_id"), saleItems));
                        }
                    }
                }
                return sales;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Sale> findByDateRange(LocalDate from, LocalDate to) {
        if (from == null) {
            throw new IllegalArgumentException("Ошибка пустая дата начала");
        }
        if (to == null || to.isBefore(from)) {
            throw new IllegalArgumentException("Ошибка в дате конца");
        }
        List<Sale> sales = new ArrayList<>();
        String sql = "select * from sale where date between ? and ?";
        try (Connection connection = DatabaseConfig.getConnection(); PreparedStatement pr = connection.prepareStatement(sql)) {
            pr.setTimestamp(1, Timestamp.valueOf(from.atStartOfDay()));
            pr.setTimestamp(2, Timestamp.valueOf(to.atStartOfDay()));
            try (ResultSet res = pr.executeQuery()) {
                while (res.next()) {
                    int saleId = res.getInt("id");
                    String sql1 = "select * from saleitem where sale_id = ?";
                    try (PreparedStatement pS = connection.prepareStatement(sql1)) {
                        pS.setInt(1, saleId);
                        try (ResultSet resultSet = pS.executeQuery()) {
                            List<SaleItem> saleItems = new ArrayList<>();
                            while (resultSet.next()) {
                                SaleItem saleItem = new SaleItem(resultSet.getInt("id"), resultSet.getInt("product_id"), resultSet.getInt("count"), resultSet.getBigDecimal("priceAtSale"), resultSet.getInt("warehouse_id"));
                                saleItems.add(saleItem);
                            }
                            sales.add(new Sale(saleId, res.getTimestamp("date").toLocalDateTime(), res.getInt("customer_id"), saleItems));
                        }
                    }
                }
                return sales;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
