package config;

import java.awt.*;

public class AppConfig {

    // === THÔNG TIN ỨNG DỤNG ===
    public static final String APP_NAME = "Hệ Thống Quản Lý Bán Hàng - QLBH";
    public static final int WIDTH  = 1400;
    public static final int HEIGHT = 800;

    // === ĐƯỜNG DẪN THƯ MỤC ẢNH ===
    public static final String PRODUCT_IMAGE_FOLDER = "resources/images/products/";
    public static final String QR_IMAGE_FOLDER       = "resources/images/qr/";
    public static final String ICON_FOLDER           = "resources/icons/";

    // Tự động tạo thư mục nếu chưa có
    static {
        createFolderIfNotExist(PRODUCT_IMAGE_FOLDER);
        createFolderIfNotExist(QR_IMAGE_FOLDER);
        createFolderIfNotExist(ICON_FOLDER);
    }

    private static void createFolderIfNotExist(String path) {
        java.io.File folder = new java.io.File(path);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            System.out.println("[AppConfig] Tạo thư mục " + path + " → " + (created ? "Thành công" : "Thất bại"));
        }
    }

    // === MÀU SẮC CHỦ ĐẠO ===
    public static final Color PRIMARY   = new Color(41, 128, 185);
    public static final Color SUCCESS   = new Color(46, 204, 113);
    public static final Color DANGER    = new Color(231, 76, 60);
    public static final Color WARNING   = new Color(241, 196, 15);
    public static final Color LIGHT     = new Color(236, 240, 241);
}