package view;

import config.AppConfig;       // Đã thêm đúng
import utils.AppConstants;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        // Đã sửa 3 dòng dùng AppConfig
        setTitle(AppConfig.APP_NAME);
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(1000, 600));

        // === MENU CHÍNH ===
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(AppConfig.PRIMARY);
        menuBar.setPreferredSize(new Dimension(0, 50));

        String[] menuNames = {"Doanh Số", "Hoá Đơn", "Mặt Hàng", "Cài Đặt"};
        JPanel[] panels = {
            new SalesPanel(this),
            new InvoicePanel(),
            new ProductManagerPanel(),
            new SettingsPanel()
        };

        for (int i = 0; i < menuNames.length; i++) {
            JMenuItem item = new JMenuItem(menuNames[i]);
            item.setFont(new Font("Segoe UI", Font.BOLD, 18));
            item.setForeground(Color.WHITE);
            item.setBackground(AppConfig.PRIMARY);
            item.setBorderPainted(false);
            item.setOpaque(true);

            int index = i;
            item.addActionListener(e -> cardLayout.show(mainPanel, menuNames[index]));

            // Hover effect
            item.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    item.setBackground(AppConfig.SUCCESS);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    item.setBackground(AppConfig.PRIMARY);
                }
            });

            menuBar.add(item);
            menuBar.add(Box.createHorizontalStrut(20)); // Khoảng cách
        }

        setJMenuBar(menuBar);

        // === NỘI DUNG CHÍNH (CardLayout) ===
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(AppConstants.LIGHT);

        for (int i = 0; i < menuNames.length; i++) {
            mainPanel.add(panels[i], menuNames[i]);
        }

        add(mainPanel, BorderLayout.CENTER);

        // Mở mặc định trang Doanh Số
        cardLayout.show(mainPanel, "Doanh Số");

        setVisible(true);
    }

    // Hàm để các panel khác gọi chuyển tab (nếu cần)
    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
}