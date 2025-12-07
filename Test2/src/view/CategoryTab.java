package view;

import service.CategoryService;
import utils.DialogUtils;

import javax.swing.*;
import java.awt.*;

public class CategoryTab extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> list = new JList<>(model);

    public CategoryTab() {
        setLayout(new BorderLayout());

        add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JTextField txt = new JTextField(15);
        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");

        btnAdd.addActionListener(e -> {
            String name = txt.getText().trim();
            if (name.isEmpty()) return;
            if (new CategoryService().addCategory(name)) {
                model.addElement(name);
                txt.setText("");
            }
        });

        btnDelete.addActionListener(e -> {
            String selected = list.getSelectedValue();
            if (selected != null && DialogUtils.confirm("Xóa danh mục này?")) {
                new CategoryService().deleteCategory(new CategoryService().getCategoryIdByName(selected));
                model.removeElement(selected);
            }
        });

        panel.add(new JLabel("Tên danh mục:"));
        panel.add(txt);
        panel.add(btnAdd);
        panel.add(btnDelete);
        add(panel, BorderLayout.SOUTH);

        loadCategories();
    }

    private void loadCategories() {
        model.clear();
        new CategoryService().getAllCategoryNames().forEach(model::addElement);
    }
}