package dao;

import java.sql.*;

public class UserDAO extends BaseDAO {

    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { closeResources(); }
    }
}