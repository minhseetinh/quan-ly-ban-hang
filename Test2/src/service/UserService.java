package service;

import dao.UserDAO;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean login(String username, String password) {
        if (username == null || password == null) return false;
        return userDAO.checkLogin(username.trim(), password);
    }
}