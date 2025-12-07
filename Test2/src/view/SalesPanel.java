package view;

import model.CartItem;
import model.Product;
import service.CartService;
import service.CategoryService;
import service.OrderService;
import service.ProductService;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesPanel extends JPanel {
    private final JFrame parent;
    private final CartService cart = new CartService();
    private final JPanel cartItemsContainer;
    private final Map<Product, CartItemRow> rowMap = new HashMap<>();
    private JLabel lblTotal;
    private JPanel productPanel;
    private JComboBox<String> cbCategory;

    public SalesPanel(JFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        topBar.setBackground(new Color(245, 247, 250));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setBackground(new Color(245, 247, 250));
        left.add(new JLabel("Danh mục:"));
        cbCategory = new JComboBox<>();
        cbCategory.addItem("Tất cả");
        new CategoryService().getAllCategoryNames().forEach(cbCategory::addItem);
        cbCategory.addActionListener(e -> reloadProducts());
        left.add(cbCategory);
        topBar.add(left, BorderLayout.WEST);

        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.setBackground(new Color(46, 204, 113));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.addActionListener(e -> {
            reloadProducts();
            updateCartDisplay();
        });
        topBar.add(btnRefresh, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        productPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 15, 15));
        productPanel.setBackground(Color.WHITE);
        JScrollPane scrollProducts = new JScrollPane(productPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollProducts.setBorder(BorderFactory.createTitledBorder("Chọn món"));
        add(scrollProducts, BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout());
        right.setPreferredSize(new Dimension(420, 0));
        right.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));

        cartItemsContainer = new JPanel();
        cartItemsContainer.setLayout(new BoxLayout(cartItemsContainer, BoxLayout.Y_AXIS));
        cartItemsContainer.setBackground(Color.WHITE);

        JScrollPane scrollCart = new JScrollPane(cartItemsContainer);
        scrollCart.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        right.add(scrollCart, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        JButton btnSave = new JButton("Lưu hóa đơn");
        btnSave.setBackground(new Color(52, 152, 219));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSave.addActionListener(e -> showSaveOrderDialog());

        JButton btnPay = new JButton("Thanh Toán");
        btnPay.setBackground(new Color(46, 204, 113));
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnPay.addActionListener(e -> new PaymentDialog(parent, cart));

        buttonPanel.add(btnSave);
        buttonPanel.add(btnPay);
        bottom.add(buttonPanel, BorderLayout.NORTH);

        lblTotal = new JLabel("Tổng: 0đ");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTotal.setForeground(new Color(46, 204, 113));
        lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
        bottom.add(lblTotal, BorderLayout.SOUTH);

        right.add(bottom, BorderLayout.SOUTH);
        add(right, BorderLayout.EAST);

        reloadProducts();
        updateCartDisplay();
    }

    private void reloadProducts() {
        productPanel.removeAll();
        List<Product> products = new ProductService().getAllProducts();
        String selected = (String) cbCategory.getSelectedItem();
        if (!"Tất cả".equals(selected)) {
            products = products.stream()
                    .filter(p -> selected.equals(p.getCategoryName()))
                    .collect(java.util.stream.Collectors.toList());
        }
        for (Product p : products) {
            productPanel.add(createProductItem(p));
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    private JPanel createProductItem(Product p) {
        JPanel item = new JPanel(new BorderLayout());
        item.setPreferredSize(new Dimension(140, 160));
        item.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));
        item.setBackground(Color.WHITE);
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel img = new JLabel();
        img.setHorizontalAlignment(SwingConstants.CENTER);
        String path = p.getImagePath();
        if (path != null && new java.io.File(path).exists()) {
            img.setIcon(ImageUtils.resize(path, 90, 90));
        } else {
            img.setIcon(ImageUtils.resize("resources/icons/default_product.png", 90, 90));
        }
        item.add(img, BorderLayout.CENTER);

        JLabel text = new JLabel("<html><center><b style='font-size:11px'>" + p.getName() +
                "</b><br><span style='color:green;font-weight:bold'>" +
                FormatUtils.vnd(p.getPrice()) + "</span></center></html>");
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setBorder(BorderFactory.createEmptyBorder(5, 5, 8, 5));
        item.add(text, BorderLayout.SOUTH);

        item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cart.addItem(p, 1);
                updateCartDisplay();
            }
        });
        return item;
    }

    private void updateCartDisplay() {
        List<Product> current = cart.getItems().stream().map(CartItem::getProduct).toList();

        rowMap.entrySet().removeIf(entry -> {
            if (!current.contains(entry.getKey())) {
                cartItemsContainer.remove(entry.getValue());
                return true;
            }
            return false;
        });

        for (CartItem ci : cart.getItems()) {
            Product p = ci.getProduct();
            CartItemRow row = rowMap.get(p);
            if (row == null) {
                row = new CartItemRow(ci, cart, this);
                rowMap.put(p, row);
                cartItemsContainer.add(row);
            } else {
                row.refresh();
            }
        }

        updateTotalOnly();
        cartItemsContainer.revalidate();
        cartItemsContainer.repaint();
    }

    public void updateTotalOnly() {
        lblTotal.setText("Tổng: " + FormatUtils.vnd(cart.getTotal()));
    }

    public void removeRowAndUpdate(Product product) {
        CartItemRow row = rowMap.remove(product);
        if (row != null) {
            cartItemsContainer.remove(row);
        }
        updateTotalOnly();
        cartItemsContainer.revalidate();
        cartItemsContainer.repaint();
    }

    private void showSaveOrderDialog() {
        if (cart.getItems().isEmpty()) {
            DialogUtils.error("Giỏ hàng trống!");
            return;
        }
        JDialog d = new JDialog(parent, "Lưu hóa đơn tạm", true);
        d.setSize(420, 280);
        d.setLocationRelativeTo(parent);

        JPanel p = new JPanel(new GridLayout(4, 1, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        JTextField txtName = new JTextField("Hóa đơn lẻ");
        JTextArea txtNote = new JTextArea("Ghi chú...");
        txtNote.setRows(4);
        p.add(new JLabel("Tên hóa đơn:"));
        p.add(txtName);
        p.add(new JLabel("Ghi chú:"));
        p.add(new JScrollPane(txtNote));

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("Lưu");
        ok.setBackground(new Color(46, 204, 113));
        ok.setForeground(Color.WHITE);
        ok.addActionListener(e -> {
            String name = txtName.getText().trim();
            if (name.isEmpty()) name = "Hóa đơn lẻ";
            new OrderService().savePendingOrderWithNameAndNote(cart.getItems(), cart.getTotal(), name, txtNote.getText());
            DialogUtils.success("Đã lưu: " + name);
            cart.clearCart();
            cartItemsContainer.removeAll();
            rowMap.clear();
            updateCartDisplay();
            d.dispose();
        });
        JButton cancel = new JButton("Hủy");
        cancel.addActionListener(e -> d.dispose());
        btns.add(cancel);
        btns.add(ok);

        d.setLayout(new BorderLayout());
        d.add(p, BorderLayout.CENTER);
        d.add(btns, BorderLayout.SOUTH);
        d.setVisible(true);
    }

    private class CartItemRow extends JPanel {
        private final CartItem cartItem;
        private final JLabel qtyLabel;
        private final JLabel infoLabel;

        public CartItemRow(CartItem cartItem, CartService cart, SalesPanel salesPanel) {
            this.cartItem = cartItem;
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                    BorderFactory.createEmptyBorder(14, 15, 14, 15)));
            setBackground(Color.WHITE);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

            infoLabel = new JLabel();
            infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            add(infoLabel, BorderLayout.CENTER);

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
            btnPanel.setBackground(Color.WHITE);

            JButton btnMinus = new JButton("-");
            btnMinus.setFont(new Font("Segoe UI", Font.BOLD, 20));
            btnMinus.setPreferredSize(new Dimension(45, 45));
            btnMinus.setBackground(new Color(231, 76, 60));
            btnMinus.setForeground(Color.WHITE);
            btnMinus.setFocusPainted(false);
            btnMinus.addActionListener(e -> {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                } else {
                    cart.removeItem(cartItem.getProduct());
                    salesPanel.removeRowAndUpdate(cartItem.getProduct());
                    return;
                }
                refresh();
            });

            qtyLabel = new JLabel(String.valueOf(cartItem.getQuantity()));
            qtyLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
            qtyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            qtyLabel.setPreferredSize(new Dimension(60, 45));

            JButton btnPlus = new JButton("+");
            btnPlus.setFont(new Font("Segoe UI", Font.BOLD, 20));
            btnPlus.setPreferredSize(new Dimension(45, 45));
            btnPlus.setBackground(new Color(46, 204, 113));
            btnPlus.setForeground(Color.WHITE);
            btnPlus.setFocusPainted(false);
            btnPlus.addActionListener(e -> {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                refresh();
            });

            JButton btnDelete = new JButton("×");
            btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 22));
            btnDelete.setPreferredSize(new Dimension(45, 45));
            btnDelete.setBackground(new Color(192, 57, 43));
            btnDelete.setForeground(Color.WHITE);
            btnDelete.setFocusPainted(false);
            btnDelete.addActionListener(e -> {
                cart.removeItem(cartItem.getProduct());
                salesPanel.removeRowAndUpdate(cartItem.getProduct());
            });

            btnPanel.add(btnMinus);
            btnPanel.add(qtyLabel);
            btnPanel.add(btnPlus);
            btnPanel.add(btnDelete);
            add(btnPanel, BorderLayout.EAST);

            refresh();
        }

        public void refresh() {
            Product p = cartItem.getProduct();
            infoLabel.setText(p.getName() + " × " + cartItem.getQuantity() +
                    " = " + FormatUtils.vnd(cartItem.getSubtotal()));
            qtyLabel.setText(String.valueOf(cartItem.getQuantity()));
            SalesPanel.this.updateTotalOnly(); // Chỉ cập nhật tổng tiền
        }
    }

    private class WrapLayout extends FlowLayout {
        public WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }
        @Override public Dimension preferredLayoutSize(Container target) { return layoutSize(target, true); }
        @Override public Dimension minimumLayoutSize(Container target) { return layoutSize(target, false); }
        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int w = target.getWidth();
                if (w == 0) w = Integer.MAX_VALUE;
                int hgap = getHgap(), vgap = getVgap();
                Insets ins = target.getInsets();
                int maxWidth = w - ins.left - ins.right - hgap * 2;
                int x = 0, y = ins.top + vgap, rowH = 0;
                for (int i = 0; i < target.getComponentCount(); i++) {
                    Component c = target.getComponent(i);
                    if (c.isVisible()) {
                        Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                        if (x + d.width > maxWidth && x > 0) { y += rowH + vgap; x = 0; rowH = 0; }
                        x += d.width + hgap;
                        rowH = Math.max(rowH, d.height);
                    }
                }
                y += rowH + ins.bottom;
                return new Dimension(w, y);
            }
        }
    }
}