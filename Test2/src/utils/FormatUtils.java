package utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class FormatUtils {

    private static final DecimalFormat df;
    private static final AtomicInteger orderCounter = new AtomicInteger(1); // TỰ ĐỘNG TĂNG

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        df = new DecimalFormat("###,###", symbols);
    }

    // Format tiền: 15000 → 15.000đ
    public static String vnd(double amount) {
        if (amount == 0) return "0đ";
        return df.format(amount) + "đ";
    }

    // TẠO MÃ HÓA ĐƠN KHÔNG BAO GIỜ TRÙNG (tự tăng số thứ tự trong ngày)
    public static String generateOrderCode() {
        LocalDateTime now = LocalDateTime.now();
        int seq = orderCounter.getAndIncrement();
        
        // Reset lại số thứ tự khi qua ngày mới
        if (now.getDayOfYear() != LocalDateTime.now().minusDays(1).getDayOfYear()) {
            orderCounter.set(1);
            seq = 1;
        }

        return String.format("Hóa đơn %03d - %02d:%02d", seq, now.getHour(), now.getMinute());
    }

    // Format ngày giờ
    public static String dateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }
}