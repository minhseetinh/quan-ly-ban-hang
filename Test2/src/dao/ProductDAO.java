package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends BaseDAO {

    // Class lồng để chứa thông tin sản phẩm từ DB
    public static class ProductInfo {
        public int id;
        public String name;
        public String category;
        public double price;
        public double costPrice;
        public int stock;
        public boolean hasStock;
        public String imagePath;
        public String color;

        public ProductInfo(int id, String name, String category, double price, double costPrice,
                           int stock, boolean hasStock, String imagePath, String color) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
            this.costPrice = costPrice;
            this.stock = stock;
            this.hasStock = hasStock;
            this.imagePath = imagePath;
            this.color = color;
        }
    }

    // LẤY TẤT CẢ SẢN PHẨM
    public List<ProductInfo> getAll() {
        List<ProductInfo> list = new ArrayList<>();
        String sql = """
            SELECT p.id, p.name, IFNULL(c.name, 'Chưa phân loại') AS category,
                   p.price, p.cost_price, p.stock, p.has_stock, p.image_path, p.color
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.id
            ORDER BY p.name
            """;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductInfo info = new ProductInfo(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getDouble("cost_price"),
                    rs.getInt("stock"),
                    rs.getBoolean("has_stock"),
                    rs.getString("image_path"),
                    rs.getString("color")
                );
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return list;
    }

    // THÊM SẢN PHẨM
    public boolean add(String name, int categoryId, double price, double costPrice,
                       int stock, boolean hasStock, String imagePath, String color) {
        String sql = """
            INSERT INTO products (name, category_id, price, cost_price, stock, has_stock, image_path, color)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, categoryId);
            ps.setDouble(3, price);
            ps.setDouble(4, costPrice);
            ps.setInt(5, stock);
            ps.setBoolean(6, hasStock);
            ps.setString(7, imagePath);
            ps.setString(8, color);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    // CẬP NHẬT SẢN PHẨM
    public boolean update(int id, String name, int categoryId, double price, double costPrice,
                          int stock, boolean hasStock, String imagePath, String color) {
        String sql = """
            UPDATE products SET name=?, category_id=?, price=?, cost_price=?, stock=?,
            has_stock=?, image_path=?, color=? WHERE id=?
            """;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, categoryId);
            ps.setDouble(3, price);
            ps.setDouble(4, costPrice);
            ps.setInt(5, stock);
            ps.setBoolean(6, hasStock);
            ps.setString(7, imagePath);
            ps.setString(8, color);
            ps.setInt(9, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean delete(int id) {
        String sqlDetails = "DELETE FROM order_details WHERE product_id = ?";
        String sqlProduct = "DELETE FROM products WHERE id = ?";
        
        try {
            ps = conn.prepareStatement(sqlDetails);
            ps.setInt(1, id);
            ps.executeUpdate();
            
            ps = conn.prepareStatement(sqlProduct);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
 }}