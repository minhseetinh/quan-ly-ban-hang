package view;

import model.CartItem;
import service.CartService;
import service.OrderService;
import utils.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class PaymentDialog extends JDialog {
    private CartService cart;
    private String orderCode = "";
    private JLabel lblQR; // giữ tham chiếu để thay đổi ảnh

    // Constructor cho Doanh Số
    public PaymentDialog(JFrame parent, CartService cart) {
        this(parent, cart, null, null, "");
    }

    // Constructor cho hóa đơn tạm (nếu cần)
    public PaymentDialog(JFrame parent, List<CartItem> items, double total, String orderCode) {
        this(parent, new CartService(), items, total, orderCode);
    }

    private PaymentDialog(JFrame parent, CartService cart, List<CartItem> items, Double total, String orderCode) {
        super(parent, orderCode.isEmpty() ? "Thanh Toán" : "Thanh toán: " + orderCode, true);
        this.cart = cart;
        this.orderCode = orderCode == null ? "" : orderCode;

        if (items != null) {
            this.cart.getItems().clear();
            this.cart.getItems().addAll(items);
        }

        initComponents();
    }

    private void initComponents() {
        setSize(520, 720);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        main.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("TỔNG TIỀN THANH TOÁN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(46, 204, 113));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTotal = new JLabel(FormatUtils.vnd(cart.getTotal()));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblTotal.setForeground(AppConstants.PRIMARY);
        lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMethod = new JLabel("Chọn phương thức:");
        lblMethod.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMethod.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> cbMethod = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});
        cbMethod.setMaximumSize(new Dimension(320, 50));
        cbMethod.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cbMethod.setAlignmentX(Component.CENTER_ALIGNMENT);

        // QR Panel
        JPanel qrPanel = new JPanel(new BorderLayout());
        qrPanel.setBackground(Color.WHITE);

        lblQR = new JLabel();
        lblQR.setHorizontalAlignment(SwingConstants.CENTER);
        loadDefaultQR(); // Load QR mặc định

        JButton btnChangeQR = new JButton("QR khác (Chọn ảnh)");
        btnChangeQR.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnChangeQR.setBackground(new Color(52, 152, 219));
        btnChangeQR.setForeground(Color.WHITE);
        btnChangeQR.addActionListener(e -> chooseAndReplaceQR());

        qrPanel.add(lblQR, BorderLayout.CENTER);
        qrPanel.add(btnChangeQR, BorderLayout.SOUTH);
        qrPanel.setVisible(false);
        qrPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cbMethod.addActionListener(e -> qrPanel.setVisible(cbMethod.getSelectedIndex() == 1));

        JButton btnConfirm = new JButton("HOÀN TẤT THANH TOÁN");
        btnConfirm.setBackground(new Color(46, 204, 113));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 22));
        btnConfirm.setPreferredSize(new Dimension(320, 60));
        btnConfirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirm.addActionListener(e -> {
            String method = cbMethod.getSelectedIndex() == 0 ? "cash" : "transfer";
            new OrderService().createPaidOrder(cart.getItems(), cart.getTotal(), method);
            DialogUtils.paymentSuccess("Thanh toán thành công!");
            dispose();
        });

        main.add(Box.createVerticalStrut(20));
        main.add(lblTitle);
        main.add(Box.createVerticalStrut(20));
        main.add(lblTotal);
        main.add(Box.createVerticalStrut(40));
        main.add(lblMethod);
        main.add(Box.createVerticalStrut(10));
        main.add(cbMethod);
        main.add(Box.createVerticalStrut(20));
        main.add(qrPanel);
        main.add(Box.createVerticalStrut(30));
        main.add(btnConfirm);
        main.add(Box.createVerticalStrut(20));

        add(new JScrollPane(main), BorderLayout.CENTER);
        setVisible(true);
    }

    // Load QR mặc định từ thư mục
    private void loadDefaultQR() {
        File folder = new File("resources/images/qr");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> 
                name.toLowerCase().matches(".*\\.(png|jpg|jpeg|gif)"));
            if (files != null && files.length > 0) {
                lblQR.setIcon(ImageUtils.resize(files[0].getAbsolutePath(), 300, 300));
                return;
            }
        }
        lblQR.setIcon(ImageUtils.resize("resources/icons/no-qr.png", 300, 300));
    }

    // NÚT "QR KHÁC" → MỞ CHỌN ẢNH + THAY THẾ NGAY
    private void chooseAndReplaceQR() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh QR mới");
        chooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "png", "jpg", "jpeg", "gif"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try {
                // Copy ảnh vào thư mục QR
                File destFolder = new File("resources/images/qr");
                if (!destFolder.exists()) destFolder.mkdirs();

                File destFile = new File(destFolder, selectedFile.getName());
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Hiển thị ngay QR mới
                lblQR.setIcon(ImageUtils.resize(destFile.getAbsolutePath(), 300, 300));
                DialogUtils.success("Đã thay QR thành công!");
            } catch (Exception ex) {
                DialogUtils.error("Không thể thay QR: " + ex.getMessage());
            }
        }
    }
}