package service;

import dao.ProductDAO;
import model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final ProductDAO dao = new ProductDAO();

    // LẤY TẤT CẢ SẢN PHẨM ĐỂ HIỂN THỊ
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        List<ProductDAO.ProductInfo> infos = dao.getAll();

        for (ProductDAO.ProductInfo info : infos) {
            Product p = new Product();
            p.setId(info.id);
            p.setName(info.name);
            p.setCategoryName(info.category);
            p.setPrice(info.price);
            p.setCostPrice(info.costPrice);
            p.setStock(info.stock);
            p.setHasStock(info.hasStock);
            p.setImagePath(info.imagePath);
            p.setColor(info.color);
            products.add(p);
        }
        return products;
    }

    // THÊM SẢN PHẨM
    public boolean addProduct(Product product) {
        if (product == null || product.getName() == null || product.getName().trim().isEmpty()) {
            return false;
        }

        CategoryService cs = new CategoryService();
        int catId = cs.getCategoryIdByName(product.getCategoryName());
        if (catId == -1) {
            // Nếu không tìm thấy danh mục → dùng danh mục đầu tiên hoặc tạo mới (tạm dùng 1)
            catId = 1;
        }

        return dao.add(
            product.getName(),
            catId,
            product.getPrice(),
            product.getCostPrice(),
            product.getStock(),
            product.isHasStock(),
            product.getImagePath(),
            product.getColor()
        );
    }

    // CẬP NHẬT SẢN PHẨM
    public boolean updateProduct(Product product) {
        if (product == null || product.getId() <= 0) return false;

        CategoryService cs = new CategoryService();
        int catId = cs.getCategoryIdByName(product.getCategoryName());
        if (catId == -1) catId = 1;

        return dao.update(
            product.getId(),
            product.getName(),
            catId,
            product.getPrice(),
            product.getCostPrice(),
            product.getStock(),
            product.isHasStock(),
            product.getImagePath(),
            product.getColor()
        );
    }

    // XÓA SẢN PHẨM
    public boolean deleteProduct(int id) {
        return id > 0 && dao.delete(id);
    }
}