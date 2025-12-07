package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    // URL ĐÃ SỬA – CHẠY MƯỢT 100% VỚI MySQL 8
    private static final String URL = "jdbc:mysql://localhost:3306/sales_db" +
        "?useSSL=false" +
        "&allowPublicKeyRetrieval=true" +   
        "&serverTimezone=Asia/Ho_Chi_Minh" + 
        "&useUnicode=true&characterEncoding=utf8";

    private static final String USERNAME = "root"; 
    private static final String PASSWORD = "nhatminh2006"; 
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Kết nối database thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy MySQL Driver! Thêm mysql-connector vào project!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Kết nối database thất bại! Kiểm tra MySQL + database 'sales_db'");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}