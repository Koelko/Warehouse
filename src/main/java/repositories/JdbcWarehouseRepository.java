package repositories;

import models.Warehouse;

import java.sql.Connection;
import java.sql.SQLException;

import repositories.DatabaseConfig;

public class JdbcWarehouseRepository implements WarehouseRepository{
    public Warehouse createWarehouse(){
        try{
            Connection connection = DatabaseConfig.getConnection();
            = "insert into warehouse(name, address) values (?,?)";
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при созданиии класса", e);
        }
    }
}
