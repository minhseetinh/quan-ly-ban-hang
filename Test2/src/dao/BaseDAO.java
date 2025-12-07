package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.DatabaseConfig;

public class BaseDAO {
    protected Connection conn = null;
    protected PreparedStatement ps = null;
    protected ResultSet rs = null;

    public BaseDAO() {
        conn = DatabaseConfig.getConnection();
    }

    // Đóng tài nguyên
    protected void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}