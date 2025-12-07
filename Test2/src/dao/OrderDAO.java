package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends BaseDAO {

    // PHƯƠNG THỨC QUAN TRỌNG NHẤT – ĐÃ CÓ RỒI ĐÂY!
    public List<String> getOrdersByStatus(String status) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT order_code, created_at, final_amount FROM orders WHERE status = ? ORDER BY created_at DESC";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("order_code") + " | " +
                         rs.getTimestamp("created_at") + " | " +
                         utils.FormatUtils.vnd(rs.getDouble("final_amount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    // Cập nhật trạng thái
    public boolean updateStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Xóa hóa đơn
    public boolean deleteOrder(int orderId) {
        String sql1 = "DELETE FROM order_details WHERE order_id = ?";
        String sql2 = "DELETE FROM orders WHERE id = ?";
        try {
            ps = conn.prepareStatement(sql1);
            ps.setInt(1, orderId);
            ps.executeUpdate();

            ps = conn.prepareStatement(sql2);
            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // Lấy ID từ mã
    public int getOrderIdByCode(String orderCode) {
        String sql = "SELECT id FROM orders WHERE order_code = ?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, orderCode);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return -1;
    }

    // Tạo hóa đơn
    public int createOrder(String orderCode, double total, double discount, double finalAmount,
                           String paymentMethod, String status) {
        String sql = """
            INSERT INTO orders (order_code, total_amount, discount_amount, final_amount, payment_method, status)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, orderCode);
            ps.setDouble(2, total);
            ps.setDouble(3, discount);
            ps.setDouble(4, finalAmount);
            ps.setString(5, paymentMethod);
            ps.setString(6, status);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return -1;
    }
}