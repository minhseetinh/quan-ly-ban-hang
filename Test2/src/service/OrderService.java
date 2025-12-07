package service;

import dao.OrderDAO;
import dao.OrderDetailDAO;
import model.CartItem;
import utils.FormatUtils;

import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderDetailDAO detailDAO = new OrderDetailDAO();

    // LƯU HÓA ĐƠN TẠM CÓ TÊN + GHI CHÚ (ĐÃ CÓ RỒI ĐÂY!)
    public void savePendingOrderWithNameAndNote(List<CartItem> items, double total, String name, String note) {
        int orderId = orderDAO.createOrder(name, total, 0, total, "cash", "pending");
        if (orderId > 0 && items != null) {
            for (CartItem item : items) {
                detailDAO.addDetail(orderId, item.getProduct().getId(),
                    item.getQuantity(), item.getProduct().getPrice(), item.getSubtotal());
            }
        }
    }

    public void savePendingOrder(List<CartItem> items, double total) {
        savePendingOrderWithNameAndNote(items, total, FormatUtils.generateOrderCode(), "");
    }

    public int createPaidOrder(List<CartItem> items, double total, String method) {
        String code = FormatUtils.generateOrderCode();
        int orderId = orderDAO.createOrder(code, total, 0, total, method, "paid");
        if (orderId > 0 && items != null) {
            for (CartItem item : items) {
                detailDAO.addDetail(orderId, item.getProduct().getId(),
                    item.getQuantity(), item.getProduct().getPrice(), item.getSubtotal());
            }
        }
        return orderId;
    }

    // Các method khác (getOrdersByStatus, updateStatus, deleteOrder...) giữ nguyên
    public java.util.List<String> getOrdersByStatus(String status) {
        return orderDAO.getOrdersByStatus(status);
    }

    public boolean updateStatus(int orderId, String newStatus) {
        return orderDAO.updateStatus(orderId, newStatus);
    }

    public boolean deleteOrder(int orderId) {
        return orderDAO.deleteOrder(orderId);
    }

    public int getOrderIdByCode(String orderCode) {
        return orderDAO.getOrderIdByCode(orderCode);
    }
}