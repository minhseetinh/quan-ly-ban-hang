package model;

public class Discount {
    private boolean enabled;
    private double percent;

    public Discount() {}

    public Discount(boolean enabled, double percent) {
        this.enabled = enabled;
        this.percent = percent;
    }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public double getPercent() { return percent; }
    public void setPercent(double percent) { this.percent = percent; }
}