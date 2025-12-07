package model;

public class Product {
    private int id;
    private String name;
    private int categoryId;
    private String categoryName;
    private double price;
    private double costPrice;
    private int stock;
    private boolean hasStock;
    private String imagePath;
    private String color;

    public Product() {}

    public Product(int id, String name, String categoryName, double price, double costPrice,
                   int stock, boolean hasStock, String imagePath, String color) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.price = price;
        this.costPrice = costPrice;
        this.stock = stock;
        this.hasStock = hasStock;
        this.imagePath = imagePath;
        this.color = color;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getCostPrice() { return costPrice; }
    public void setCostPrice(double costPrice) { this.costPrice = costPrice; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public boolean isHasStock() { return hasStock; }
    public void setHasStock(boolean hasStock) { this.hasStock = hasStock; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    @Override
    public String toString() {
        return name + " - " + String.format("%,.0fđ", price);
    }
}