package com.ems.model;

public class Department {
    private String departmentId;
    private String departmentName;
    private String description;
    private String managerId;

    public Department() {}

    public Department(String departmentId, String departmentName, String description, String managerId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.description = description;
        this.managerId = managerId;
    }

    // Getters and Setters
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Manager: %s | Description: %s",
                departmentId, departmentName, managerId != null ? managerId : "N/A", description);
    }
}
