package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String url = "jdbc:mysql://localhost:3306/warehouse_db";
    private static final String user = "root";
    private static final String db_password = System.getenv("db_password");
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL драйвер не найден!", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        if (db_password == null) {
            throw new IllegalArgumentException("Пароль к БД не задан в переменной 'db_password'");
        }

        return DriverManager.getConnection(url, user, db_password);
    }
}
