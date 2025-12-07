package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    // Crop ảnh thành vuông + lưu
    public static String cropAndSave(ImageIcon originalIcon, String folderPath, String fileName) {
        Image img = originalIcon.getImage();
        int size = Math.min(img.getWidth(null), img.getHeight(null));
        BufferedImage cropped = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = cropped.createGraphics();
        g2d.drawImage(img, 0, 0, size, size, null);
        g2d.dispose();

        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();

        File output = new File(folderPath + fileName);
        try {
            ImageIO.write(cropped, "png", output);
            return output.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Load ảnh, nếu không có thì trả về ảnh mặc định
    public static ImageIcon loadImage(String path, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            // Trả về ảnh mặc định nếu lỗi
            return new ImageIcon("resources/icons/no-image.png");
        }
    }

    // Resize ảnh
    public static ImageIcon resize(String path, int width, int height) {
        return loadImage(path, width, height);
    }
}