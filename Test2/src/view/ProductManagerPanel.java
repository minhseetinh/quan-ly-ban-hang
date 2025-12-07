package view;

import javax.swing.*;
import java.awt.*;

public class ProductManagerPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductManagerPanel() {
        setLayout(new BorderLayout());

        JTabbedPane tab = new JTabbedPane();

        // Tab 1: Thêm + sửa sản phẩm (có ảnh)
        tab.addTab("Thêm/Sửa Mặt Hàng", new ProductTab());

        // Tab 2: Danh sách món đẹp như Doanh Số + lọc theo danh mục
        tab.addTab("Danh Sách Mặt Hàng", new ProductListPanel());

        // Tab 3: Danh mục
        tab.addTab("Danh Mục", new CategoryTab());

        // Tab 4: Chiết khấu
        tab.addTab("Chiết Khấu", new DiscountTab());

        add(tab, BorderLayout.CENTER);
    }
}