package view;

import service.DiscountService;
import model.Discount;
import utils.DialogUtils;

import javax.swing.*;
import java.awt.*;

public class DiscountTab extends JPanel {
    private JCheckBox chkEnable;
    private JTextField txtPercent;

    public DiscountTab() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);

        chkEnable = new JCheckBox("Bật chiết khấu toàn bộ hóa đơn");
        chkEnable.setFont(new Font("Segoe UI", Font.BOLD, 18));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        add(chkEnable, c);

        c.gridwidth = 1; c.gridy = 1;
        add(new JLabel("Phần trăm chiết khấu:"), c);
        txtPercent = new JTextField("10", 10);
        c.gridx = 1;
        add(txtPercent, c);

        JButton btnSave = new JButton("Lưu cài đặt");
        btnSave.setBackground(utils.AppConstants.SUCCESS);
        btnSave.setForeground(Color.WHITE);
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        btnSave.addActionListener(e -> {
            boolean enabled = chkEnable.isSelected();
            double percent = 0;
            try {
                percent = Double.parseDouble(txtPercent.getText());
            } catch (Exception ex) {
                DialogUtils.error("Phần trăm không hợp lệ!");
                return;
            }
            if (new DiscountService().updateDiscount(enabled, percent)) {
                DialogUtils.success("Cập nhật chiết khấu thành công!");
            }
        });
        add(btnSave, c);

        loadCurrentDiscount();
    }

    private void loadCurrentDiscount() {
        Discount d = new DiscountService().getDiscount();
        chkEnable.setSelected(d.isEnabled());
        txtPercent.setText(String.valueOf((int)d.getPercent()));
    }
}