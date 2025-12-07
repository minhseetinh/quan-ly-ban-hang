CREATE DATABASE IF NOT EXISTS sales_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sales_db;

-- Bảng danh mục
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Bảng sản phẩm
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    price DOUBLE NOT NULL DEFAULT 0,
    image_path VARCHAR(500),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- BẢNG HÓA ĐƠN 
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_code VARCHAR(100) UNIQUE NOT NULL,
    total_amount DOUBLE NOT NULL DEFAULT 0,
    discount_amount DOUBLE DEFAULT 0,
    final_amount DOUBLE NOT NULL DEFAULT 0,
    payment_method VARCHAR(50),
    status ENUM('pending', 'paid') DEFAULT 'pending',
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Bảng chi tiết hóa đơn
CREATE TABLE IF NOT EXISTS order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB;

ALTER TABLE pending_orders ADD COLUMN order_name VARCHAR(100) DEFAULT 'Hóa đơn lẻ';
ALTER TABLE pending_orders ADD COLUMN note TEXT DEFAULT '';
