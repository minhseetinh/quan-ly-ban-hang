package dao;

import java.sql.*;

public class DiscountDAO extends BaseDAO {

    public double getDiscountPercent() {
        String sql = "SELECT percent FROM discount WHERE id = 1";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("percent");
        } catch (SQLException e) { e.printStackTrace(); }
        finally { closeResources(); }
        return 0.0;
    }

    public boolean isEnabled() {
        String sql = "SELECT enabled FROM discount WHERE id = 1";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getBoolean("enabled");
        } catch (SQLException e) { e.printStackTrace(); }
        finally { closeResources(); }
        return false;
    }

    public boolean update(boolean enabled, double percent) {
        String sql = "UPDATE discount SET enabled = ?, percent = ? WHERE id = 1";
        try {
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, enabled);
            ps.setDouble(2, percent);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { closeResources(); }
    }
}