package view;

import model.Product;
import service.ProductService;
import utils.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import service.CategoryService;
import java.util.List;

public class ProductTab extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtName, txtPrice, txtCost, txtStock;
    private JComboBox<String> cbCategory;
    private JCheckBox chkStock;
    private JLabel lblImage;
    private String currentImagePath = null;

    public ProductTab() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Form
        c.gridx = 0; c.gridy = 0; add(new JLabel("Tên sản phẩm:"), c);
        c.gridx = 1; txtName = new JTextField(20); add(txtName, c);

        c.gridx = 0; c.gridy = 1; add(new JLabel("Danh mục:"), c);
        c.gridx = 1; cbCategory = new JComboBox<>(); add(cbCategory, c);

        c.gridx = 0; c.gridy = 2; add(new JLabel("Giá bán:"), c);
        c.gridx = 1; txtPrice = new JTextField(20); add(txtPrice, c);

        c.gridx = 0; c.gridy = 3; add(new JLabel("Giá nhập:"), c);
        c.gridx = 1; txtCost = new JTextField(20); add(txtCost, c);

        c.gridx = 0; c.gridy = 4; add(new JLabel("Tồn kho:"), c);
        c.gridx = 1; txtStock = new JTextField(20); add(txtStock, c);

        chkStock = new JCheckBox("Quản lý tồn kho");
        c.gridx = 1; c.gridy = 5; add(chkStock, c);

        lblImage = new JLabel("Chưa chọn ảnh", SwingConstants.CENTER);
        lblImage.setPreferredSize(new Dimension(200, 200));
        lblImage.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JButton btnChoose = new JButton("Chọn ảnh");
        btnChoose.addActionListener(e -> chooseImage());

        JPanel imgPanel = new JPanel();
        imgPanel.add(lblImage);
        imgPanel.add(btnChoose);
        c.gridx = 2; c.gridy = 0; c.gridheight = 6; add(imgPanel, c);

        JButton btnSave = new JButton("Lưu sản phẩm");
        btnSave.setBackground(AppConstants.SUCCESS);
        btnSave.setForeground(Color.WHITE);
        c.gridx = 0; c.gridy = 6; c.gridwidth = 3;
        btnSave.addActionListener(e -> saveProduct());
        add(btnSave, c);

        loadCategories();
    }

    void loadCategories() {
        cbCategory.removeAllItems(); // XÓA CŨ ĐI
        List<String> categories = new CategoryService().getAllCategoryNames();
        for (String cat : categories) {
            cbCategory.addItem(cat);
        }
        
        // Nếu có ít nhất 1 danh mục → chọn cái đầu tiên
        if (cbCategory.getItemCount() > 0) {
            cbCategory.setSelectedIndex(0);
        }
    }
    private void chooseImage() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            ImageIcon icon = new ImageIcon(file.getPath());
            Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(scaled));
            lblImage.setText("");

            // Crop + lưu
            String fileName = "prod_" + System.currentTimeMillis() + ".png";
            currentImagePath = ImageUtils.cropAndSave(new ImageIcon(file.getPath()),
                config.AppConfig.PRODUCT_IMAGE_FOLDER, fileName);
        }
    }

    private void saveProduct() {
        if (Validator.isEmpty(txtName, "Tên sản phẩm") ||
            Validator.isEmpty(txtPrice, "Giá bán") ||
            !Validator.isPositiveNumber(txtPrice.getText(), "Giá bán")) {
            return;
        }

        Product p = new Product();
        p.setName(txtName.getText().trim());
        p.setCategoryName((String) cbCategory.getSelectedItem());
        p.setPrice(Double.parseDouble(txtPrice.getText().replace(".", "")));
        p.setCostPrice(txtCost.getText().isEmpty() ? 0 : Double.parseDouble(txtCost.getText().replace(".", "")));
        p.setHasStock(chkStock.isSelected());
        p.setStock(txtStock.getText().isEmpty() ? 0 : Integer.parseInt(txtStock.getText()));
        p.setImagePath(currentImagePath);

        if (new ProductService().addProduct(p)) {
            DialogUtils.success("Thêm sản phẩm thành công!");
            // Reset form...
        } else {
            DialogUtils.error("Thêm thất bại!");
        }
    }
}