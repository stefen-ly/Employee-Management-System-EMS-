package com.ems.model;

public class User {
    private String username;
    private String password; // In production, this should be hashed
    private String role; // ADMIN or STAFF
    private String employeeId; // Link to Employee

    public User() {}

    public User(String username, String password, String role, String employeeId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.employeeId = employeeId;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
}
