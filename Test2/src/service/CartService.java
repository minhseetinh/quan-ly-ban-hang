package service;

import model.CartItem;
import model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private final List<CartItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {
        if (product == null || quantity <= 0) return;

        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    // Cập nhật số lượng
    public void updateQuantity(int productId, int newQuantity) {
        if (newQuantity <= 0) {
            removeItem(productId);
            return;
        }
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                item.setQuantity(newQuantity);
                return;
            }
        }
    }

    // Xóa món khỏi giỏ
    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    public void clearCart() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items); 
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }
    public void removeItem(Product product) {
        getItems().removeIf(item -> item.getProduct().equals(product));
    }

}