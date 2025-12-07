package service;

import dao.CategoryDAO;
import java.util.List;

public class CategoryService {
    private final CategoryDAO dao = new CategoryDAO();

    // ĐÚNG TÊN PHƯƠNG THỨC – BÂY GIỜ KHỚP 100%
    public List<String> getAllCategoryNames() {
        return dao.getAllCategoryNames();
    }

    public int getCategoryIdByName(String name) {
        return dao.getCategoryIdByName(name);
    }

    public boolean addCategory(String name) {
        if (name == null || name.trim().isEmpty()) return false;
        return dao.add(name.trim());
    }

    public boolean updateCategory(int id, String newName) {
        if (newName == null || newName.trim().isEmpty()) return false;
        return dao.update(id, newName.trim());
    }

    public boolean deleteCategory(int id) {
        return dao.delete(id);
    }
}