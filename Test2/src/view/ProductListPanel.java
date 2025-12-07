package view;

import model.Product;
import service.CategoryService;
import service.ProductService;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ProductListPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel gridPanel;
    private JComboBox<String> cbFilter;

    public ProductListPanel() {
        setLayout(new BorderLayout());

        // Top: Lọc theo danh mục
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Lọc theo danh mục:"));
        cbFilter = new JComboBox<>();
        cbFilter.addItem("Tất cả");
        new CategoryService().getAllCategoryNames().forEach(cbFilter::addItem);
        cbFilter.addActionListener(e -> loadProducts());
        top.add(cbFilter);

        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> loadProducts());
        top.add(btnRefresh);

        add(top, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(0, 5, 15, 15));
        JScrollPane scroll = new JScrollPane(gridPanel);
        add(scroll, BorderLayout.CENTER);

        loadProducts();
    }

    private void loadProducts() {
        gridPanel.removeAll();
        List<Product> products = new ProductService().getAllProducts();
        String selectedCat = (String) cbFilter.getSelectedItem();

        List<Product> filtered = "Tất cả".equals(selectedCat) ?
            products : products.stream()
                .filter(p -> selectedCat.equals(p.getCategoryName()))
                .collect(Collectors.toList());

        for (Product p : filtered) {
            JPanel item = new JPanel(new BorderLayout());
            item.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            item.setPreferredSize(new Dimension(150, 180));

            JLabel img = new JLabel();
            img.setHorizontalAlignment(SwingConstants.CENTER);
            String path = p.getImagePath();
            if (path != null && new java.io.File(path).exists()) {
                img.setIcon(ImageUtils.resize(path, 100, 100));
            } else {
                img.setIcon(ImageUtils.resize("resources/icons/default_product.png", 100, 100));
            }
            item.add(img, BorderLayout.CENTER);

            JLabel name = new JLabel("<html><center><b>" + p.getName() + "</b><br>" +
                FormatUtils.vnd(p.getPrice()) + "</center></html>", SwingConstants.CENTER);
            name.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            item.add(name, BorderLayout.SOUTH);

            // Nút xóa
            JButton btnDelete = new JButton("X");
            btnDelete.setForeground(Color.RED);
            btnDelete.setBorderPainted(false);
            btnDelete.setContentAreaFilled(false);
            btnDelete.addActionListener(e -> {
                if (DialogUtils.confirm("Xóa món \"" + p.getName() + "\"?")) {
                    new ProductService().deleteProduct(p.getId());
                    loadProducts();
                }
            });
            item.add(btnDelete, BorderLayout.NORTH);

            gridPanel.add(item);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}