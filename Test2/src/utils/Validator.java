package utils;

import javax.swing.*;

public class Validator {

    public static boolean isEmpty(JTextField field, String fieldName) {
        if (field.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, fieldName + " không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            field.requestFocus();
            return true;
        }
        return false;
    }

    public static boolean isPositiveNumber(String text, String fieldName) {
        try {
            double value = Double.parseDouble(text.replace(".", ""));
            if (value <= 0) throw new Exception();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, fieldName + " phải là số dương!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean isInteger(String text, String fieldName) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, fieldName + " phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}