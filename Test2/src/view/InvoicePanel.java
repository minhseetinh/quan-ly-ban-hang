package view;

import dao.OrderDAO;
import service.OrderService;
import utils.*;

import javax.swing.*;
import java.awt.*;

public class InvoicePanel extends JPanel {
    private DefaultListModel<String> pendingModel = new DefaultListModel<>();
    private DefaultListModel<String> paidModel = new DefaultListModel<>();
    private JList<String> pendingList, paidList;

    public InvoicePanel() {
        setLayout(new BorderLayout());
        JTabbedPane tab = new JTabbedPane();
        tab.addTab("Chưa thanh toán", createPendingPanel());
        tab.addTab("Đã thanh toán", createPaidPanel());
        add(tab);
    }

    private JPanel createPendingPanel() {
        JPanel p = new JPanel(new BorderLayout());
        pendingList = new JList<>(pendingModel);
        p.add(new JScrollPane(pendingList), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton btnLoad = new JButton("Tải lại");
        JButton btnPay = new JButton("Thanh toán");
        btnPay.setBackground(new Color(46, 204, 113));
        btnPay.setForeground(Color.WHITE);
        JButton btnDelete = new JButton("Xóa");
        btnDelete.setForeground(Color.RED);

        btnLoad.addActionListener(e -> loadPending());
        btnPay.addActionListener(e -> payPending());
        btnDelete.addActionListener(e -> deletePending());

        btns.add(btnLoad);
        btns.add(btnPay);
        btns.add(btnDelete);
        p.add(btns, BorderLayout.SOUTH);
        loadPending();
        return p;
    }

    private JPanel createPaidPanel() {
        JPanel p = new JPanel(new BorderLayout());
        paidList = new JList<>(paidModel);
        p.add(new JScrollPane(paidList), BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton btnLoad = new JButton("Tải lại");
        JButton btnDelete = new JButton("Xóa");
        btnDelete.setForeground(Color.RED);

        btnLoad.addActionListener(e -> loadPaid());
        btnDelete.addActionListener(e -> deletePaid());

        btns.add(btnLoad);
        btns.add(btnDelete);
        p.add(btns, BorderLayout.SOUTH);
        loadPaid();
        return p;
    }

    private void loadPending() {
        pendingModel.clear();
        new OrderService().getOrdersByStatus("pending").forEach(pendingModel::addElement);
    }

    private void loadPaid() {
        paidModel.clear();
        new OrderService().getOrdersByStatus("paid").forEach(paidModel::addElement);
    }

    private void payPending() {
        String selected = pendingList.getSelectedValue();
        if (selected != null) {
            String code = selected.split(" \\| ")[0];
            int id = new OrderDAO().getOrderIdByCode(code);
            if (id != -1) {
                new OrderService().updateStatus(id, "paid");
                DialogUtils.success("Đã thanh toán: " + selected);
                loadPending();
                loadPaid();
            }
        }
    }

    private void deletePending() {
        String selected = pendingList.getSelectedValue();
        if (selected != null && DialogUtils.confirm("Xóa hóa đơn này?")) {
            String code = selected.split(" \\| ")[0];
            int id = new OrderDAO().getOrderIdByCode(code);
            new OrderDAO().deleteOrder(id);
            loadPending();
        }
    }

    private void deletePaid() {
        String selected = paidList.getSelectedValue();
        if (selected != null && DialogUtils.confirm("Xóa vĩnh viễn hóa đơn này?")) {
            String code = selected.split(" \\| ")[0];
            int id = new OrderDAO().getOrderIdByCode(code);
            new OrderDAO().deleteOrder(id);
            loadPaid();
        }
    }
}