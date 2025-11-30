package com.ems.data;

import com.ems.model.*;
import com.ems.util.CSVManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore instance;
    
    private List<Employee> employees;
    private List<Department> departments;
    private List<User> users;
    private List<Attendance> attendances;
    private List<LeaveRequest> leaveRequests;
    private List<SalaryRecord> salaryRecords;
    
    private CSVManager csvManager;

    private DataStore() {
        csvManager = new CSVManager();
        loadDataFromCSV();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void loadDataFromCSV() {
        employees = csvManager.loadEmployees();
        departments = csvManager.loadDepartments();
        users = csvManager.loadUsers();
        attendances = csvManager.loadAttendance();
        leaveRequests = csvManager.loadLeaveRequests();
        salaryRecords = csvManager.loadSalaryRecords();

        if (employees.isEmpty() && departments.isEmpty() && users.isEmpty()) {
            System.out.println("No existing data found. Initializing with default data...");
            initializeDefaultData();
            saveAllData();
        } else {
            System.out.println("Data loaded from CSV files successfully!");
        }
    }

    private void initializeDefaultData() {
        // Create default admin user
        users.add(new User("admin", "admin123", "ADMIN", null));
        
        // Add departments
        departments.add(new Department("dep1", "IT Department", "Information Technology", null));
        departments.add(new Department("dep2", "HR Department", "Human Resources", null));
        departments.add(new Department("dep3", "Finance Department", "Finance and Accounting", null));
        
        // Add employees
        Employee emp1 = new Employee(
            "emp001",
            "Jimmy Kiss",
            LocalDate.of(1990, 5, 15),
            "Male",
            "jimmykiss@gmail.com",
            "1234567890",
            "123 Main St, City",
            "Software Developer",
            "dep1",
            75000.00
        );

        
        Employee emp2 = new Employee(
            "emp002",
            "Messoy Jetjea",
            LocalDate.of(1992, 8, 22),
            "Female",
            "messoyjetjea@gmail.com",
            "9876543210",
            "456 Oak Ave, City",
            "HR Manager",
            "dep2",
            65000.00
        );
        
        Employee emp3 = new Employee(
            "emp003",
            "Penaldo Leo",
            LocalDate.of(1988, 3, 10),
            "Male",
            "penaldoleo@gmail.com",
            "5551234567",
            "789 Pine Rd, City",
            "Accountant",
            "dep3",
            60000.00
        );

        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        
        // Create staff user accounts
        users.add(new User("jimmy12", "jimmy123", "STAFF", "emp001"));
        users.add(new User("messoy10", "messoy123", "STAFF", "emp002"));
        users.add(new User("penaldo7", "cr7777", "STAFF", "emp003"));
        
        // Add salary records
        SalaryRecord sal1 = new SalaryRecord("SAL001", "emp001", 75000.00);
        sal1.setBonus(5000.00);
        sal1.setDeduction(0.00);
        sal1.setPaymentDate(LocalDate.now());
        sal1.setMonth(LocalDate.now().getMonth().toString());
        sal1.setYear(LocalDate.now().getYear());
        salaryRecords.add(sal1);

    }

    public void saveAllData() {
        csvManager.saveAllData(employees, departments, users, attendances, leaveRequests, salaryRecords);
    }

    // Getters
    public List<Employee> getEmployees() { return employees; }
    public List<Department> getDepartments() { return departments; }
    public List<User> getUsers() { return users; }
    public List<Attendance> getAttendances() { return attendances; }
    public List<LeaveRequest> getLeaveRequests() { return leaveRequests; }
    public List<SalaryRecord> getSalaryRecords() { return salaryRecords; }
}
