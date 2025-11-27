package com.ems.service;

import com.ems.dao.UserDAO;
import com.ems.model.User;

public class AuthService {
    private UserDAO userDAO;
    private User currentUser;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public boolean login(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }

    public boolean isStaff() {
        return currentUser != null && "STAFF".equals(currentUser.getRole());
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (currentUser != null && currentUser.getPassword().equals(oldPassword)) {
            return userDAO.changePassword(currentUser.getUsername(), newPassword);
        }
        return false;
    }

    public boolean registerUser(User user) {
        return userDAO.addUser(user);
    }
}
