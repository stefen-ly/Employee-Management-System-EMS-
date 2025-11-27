package com.ems.view.menus;

import com.ems.model.User;
import com.ems.model.Employee;
import com.ems.service.UserService;
import com.ems.service.EmployeeService;
import com.ems.util.Components;
import com.ems.util.Table;
import com.ems.util.BorderStyle;
import java.util.List;
import java.util.Scanner;

public class UserMenu {
    private Scanner scanner;
    private UserService userService;
    private EmployeeService employeeService;

    public UserMenu(Scanner scanner) {
        this.scanner = scanner;
        this.userService = new UserService();
        this.employeeService = new EmployeeService();
    }

    public void display() {
        boolean running = true;
        
        while (running) {
            Components.clearScreen();
            System.out.print(USER_MENU);
            int choice = getIntInput("Enter choice: ");
            
            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public final static String USER_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                 USER MANAGEMENT                  ║
            ╠══════════════════════════════════════════════════╣
            ║ [1]. Create User Account                         ║
            ║ [2]. View All Users                              ║
            ║ [3]. Delete User                                 ║ 
            ║ [0]. Back                                        ║                                     
            ╚══════════════════════════════════════════════════╝  
            """;


    private void createUser() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                Create User Account               ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        
        System.out.print("[+] Enter Username: ");
        String username = scanner.nextLine();
        
        // Check if username already exists
        if (userService.getUserByUsername(username) != null) {
            System.out.println("✗ Username already exists!");
            return;
        }
        
        System.out.print("[+] Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Role (ADMIN/STAFF): ");
        String role = scanner.nextLine().toUpperCase();
        
        if (!role.equals("ADMIN") && !role.equals("STAFF")) {
            System.out.println("✗ Invalid role! Must be ADMIN or STAFF.");
            return;
        }
        
        String employeeId = null;
        if (role.equals("STAFF")) {
            // Link to an employee
            System.out.println("Available Employees:");
            List<Employee> employees = employeeService.getAllEmployees();
            if (employees.isEmpty()) {
                System.out.println("No employees available. Please create an employee first.");
                return;
            }
            employees.forEach(emp -> 
                System.out.println("  - " + emp.getEmployeeId() + ": " + emp.getName() + " (" + emp.getPosition() + ")")
            );
            
            System.out.print("Link to Employee ID (or press Enter to skip): ");
            String inputId = scanner.nextLine();
            if (!inputId.trim().isEmpty()) {
                Employee emp = employeeService.getEmployeeById(inputId);
                if (emp != null) {
                    employeeId = inputId;
                } else {
                    System.out.println("Warning: Employee ID not found. Proceeding without link.");
                }
            }
        }
        
        User user = new User(username, password, role, employeeId);
        
        if (userService.createUser(user)) {
            System.out.println("✓ User account created successfully!");
            System.out.println("  - Username: " + username);
            System.out.println("  - Role: " + role);
            if (employeeId != null) {
                System.out.println("  Linked to Employee: " + employeeId);
            }
            
            System.out.print("Create another user? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                createUser();
            }
        } else {
            System.out.println("✗ Failed to create user account.");
        }
    }

    private void viewAllUsers() {
        List<User> users = userService.getAllUsers();
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║              ALL USERS             ║");
            System.out.println("╚════════════════════════════════════╝");
            Table table = new Table(3, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Username");
            table.addCell("Role");
            table.addCell("Employee ID");
            
            for (User user : users) {
                table.addCell(user.getUsername());
                table.addCell(user.getRole());
                table.addCell(user.getEmployeeId() != null ? user.getEmployeeId() : "N/A");
            }
            
            System.out.println(table.render());
            System.out.println("Total: " + users.size() + " users");
        }
    }

    private void deleteUser() {
        System.out.print("[+] Enter username to delete: ");
        String username = scanner.nextLine();
        
        if (username.equals("admin")) {
            System.out.println("✗ Cannot delete admin account!");
            return;
        }
        
        User user = userService.getUserByUsername(username);
        if (user == null) {
            System.out.println("✗ User not found.");
            return;
        }
        
        System.out.println("User: " + username + " (Role: " + user.getRole() + ")");
        System.out.print("Are you sure you want to delete this user? (y/n): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            if (userService.deleteUser(username)) {
                System.out.println("✓ User deleted successfully!");
                
                System.out.print("Delete another user? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    deleteUser();
                }
            } else {
                System.out.println("✗ Failed to delete user.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
