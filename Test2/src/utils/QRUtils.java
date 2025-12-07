package utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class QRUtils {

    private static final String[] qrPaths = {
        "resources/images/qr/qr_momo.jpg",
        "resources/images/qr/qr_vietcombank.jpg",
        "resources/images/qr/qr_mbbank.jpg"
    };

    private static int currentIndex = 0;

    public static ImageIcon getCurrentQR(int width, int height) {
        File file = new File(qrPaths[currentIndex]);
        if (file.exists()) {
            return ImageUtils.resize(qrPaths[currentIndex], width, height);
        }
        return new ImageIcon("resources/icons/no-qr.png");
    }

    public static void nextQR() {
        currentIndex = (currentIndex + 1) % qrPaths.length;
    }

    public static String getCurrentQRName() {
        String name = qrPaths[currentIndex].substring(qrPaths[currentIndex].lastIndexOf("/") + 1);
        return name.replace(".jpg", "").replace("qr_", "").toUpperCase();
    }
}