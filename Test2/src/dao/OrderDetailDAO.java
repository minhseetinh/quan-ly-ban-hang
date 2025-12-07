package dao;

import java.sql.*;

public class OrderDetailDAO extends BaseDAO {

    public boolean addDetail(int orderId, int productId, int quantity, double unitPrice, double subtotal) {
        String sql = """
            INSERT INTO order_details (order_id, product_id, quantity, unit_price, subtotal)
            VALUES (?, ?, ?, ?, ?)
            """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, unitPrice);
            ps.setDouble(5, subtotal);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
        finally { closeResources(); }
    }
}