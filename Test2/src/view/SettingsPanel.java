package view;

import utils.AppConstants;
import java.awt.*;
import javax.swing.*;

public class SettingsPanel extends JPanel {
    public SettingsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppConstants.LIGHT);

        JLabel lbl = new JLabel("CÀI ĐẶT HỆ THỐNG");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(100));
        add(lbl);

        JLabel lblPrint = new JLabel("In hóa đơn bằng máy in: Đang phát triển...");
        lblPrint.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblPrint.setAlignmentX(CENTER_ALIGNMENT);
        add(lblPrint);
    }
}