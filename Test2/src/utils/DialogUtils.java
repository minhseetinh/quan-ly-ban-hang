package utils;

import javax.swing.*;
import java.awt.*;

public class DialogUtils {

    public static void success(String message) {
        JOptionPane.showMessageDialog(null, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(String message) {
        JOptionPane.showMessageDialog(null, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirm(String message) {
        int choice = JOptionPane.showConfirmDialog(null, message, "Xác nhận", JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }

    // Thông báo thanh toán thành công có icon
    public static void paymentSuccess(String orderInfo) {
        JLabel label = new JLabel("<html><h2>Thanh toán thành công!</h2><p>" + orderInfo.replace("|", "<br>") + "</p></html>");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JOptionPane.showMessageDialog(null, label, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }
}