package com.ems.dao.interfaces;

import com.ems.model.User;
import java.util.List;

public interface IUserDAO {
    User getUserByUsername(String username);
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean changePassword(String username, String newPassword);
    List<User> getAllUsers();
    boolean deleteUser(String username);
}
