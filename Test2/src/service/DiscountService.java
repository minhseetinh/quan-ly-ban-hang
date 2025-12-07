package service;

import dao.DiscountDAO;
import model.Discount;

public class DiscountService {
    private final DiscountDAO dao = new DiscountDAO();

    public Discount getDiscount() {
        return new Discount(dao.isEnabled(), dao.getDiscountPercent());
    }

    public boolean updateDiscount(boolean enabled, double percent) {
        if (percent < 0 || percent > 100) {
            return false;
        }
        return dao.update(enabled, percent);
    }
}