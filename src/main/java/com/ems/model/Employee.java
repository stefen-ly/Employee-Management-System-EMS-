package com.ems.model;

import java.time.LocalDate;
import java.time.Period;

public class Employee {
    private String employeeId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String position;
    private String departmentId;
    private double salary;
    private LocalDate hireDate;
    private String status; // ACTIVE, INACTIVE

    public Employee() {
        this.status = "ACTIVE";
        this.hireDate = LocalDate.now();
    }

    public Employee(String employeeId, String name, LocalDate dateOfBirth, String gender,
                    String email, String phoneNumber, String address, String position,
                    String departmentId, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.position = position;
        this.departmentId = departmentId;
        this.salary = salary;
        this.hireDate = LocalDate.now();
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getAge() {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | DOB: %s | Gender: %s | Position: %s | Dept: %s | Salary: $%.2f | Status: %s",
                employeeId, name, dateOfBirth, gender, position, departmentId, salary, status);
    }
}
