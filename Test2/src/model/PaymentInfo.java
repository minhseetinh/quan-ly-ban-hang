package model;

public class PaymentInfo {
    private String method; // cash / transfer
    private double total;
    private double discount;
    private double finalAmount;

    public PaymentInfo(String method, double total, double discount, double finalAmount) {
        this.method = method;
        this.total = total;
        this.discount = discount;
        this.finalAmount = finalAmount;
    }

    // Getter
    public String getMethod() { return method; }
    public double getTotal() { return total; }
    public double getDiscount() { return discount; }
    public double getFinalAmount() { return finalAmount; }
}