package service;

import dao.OrderDAO;
import java.sql.*;

public class ReportService {
    private OrderDAO orderDAO;

    public ReportService() {
        this.orderDAO = new OrderDAO();
    }

    public double getTodayRevenue() {
        String sql = "SELECT SUM(final_amount) FROM orders WHERE DATE(created_at) = CURDATE() AND status = 'paid'";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}