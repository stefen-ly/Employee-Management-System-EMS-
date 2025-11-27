package com.ems.service;

import com.ems.dao.UserDAO;
import com.ems.model.User;
import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean createUser(User user) {
        // Check if username already exists
        if (userDAO.getUserByUsername(user.getUsername()) != null) {
            return false;
        }
        return userDAO.addUser(user);
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean deleteUser(String username) {
        return userDAO.deleteUser(username);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
}
