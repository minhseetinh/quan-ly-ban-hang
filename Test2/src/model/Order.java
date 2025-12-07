package model;

import java.util.Date;

public class Order {
    private int id;
    private String orderCode;
    private Date createdAt;
    private double totalAmount;
    private double discountAmount;
    private double finalAmount;
    private String paymentMethod; // cash / transfer
    private String status;        // pending, paid, refunded

    public Order() {}

    public Order(int id, String orderCode, Date createdAt, double totalAmount,
                 double discountAmount, double finalAmount, String paymentMethod, String status) {
        this.id = id;
        this.orderCode = orderCode;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOrderCode() { return orderCode; }
    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}