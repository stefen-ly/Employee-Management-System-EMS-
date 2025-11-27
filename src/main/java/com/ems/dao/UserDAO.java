package com.ems.dao;

import com.ems.dao.interfaces.IUserDAO;
import com.ems.data.DataStore;
import com.ems.model.User;
import java.util.List;

public class UserDAO implements IUserDAO {
    private DataStore dataStore;

    public UserDAO() {
        this.dataStore = DataStore.getInstance();
    }

    @Override
    public User getUserByUsername(String username) {
        return dataStore.getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean addUser(User user) {
        boolean result = dataStore.getUsers().add(user);
        if (result) {
            dataStore.saveAllData();
        }
        return result;
    }

    @Override
    public boolean updateUser(User user) {
        User existing = getUserByUsername(user.getUsername());
        if (existing != null) {
            dataStore.getUsers().remove(existing);
            dataStore.getUsers().add(user);
            dataStore.saveAllData();
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.setPassword(newPassword);
            dataStore.saveAllData();
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return dataStore.getUsers();
    }

    @Override
    public boolean deleteUser(String username) {
        User user = getUserByUsername(username);
        if (user != null && !"admin".equals(username)) {
            boolean result = dataStore.getUsers().remove(user);
            if (result) {
                dataStore.saveAllData();
            }
            return result;
        }
        return false;
    }
}
