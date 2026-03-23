package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
   private static final String url = "jdbc:mysql://localhost:3306/warehouse_db";
   private static final String user = "root";
   private static final String password = System.getenv("password");
   public static Connection getConnection() throws SQLException{
       if (password == null){
           throw new IllegalArgumentException("Ошибка");
       }
       return DriverManager.getConnection(url, user, password);
   }
}
