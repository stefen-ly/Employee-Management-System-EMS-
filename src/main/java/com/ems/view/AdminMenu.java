package com.ems.view;

import com.ems.data.DataStore;
import com.ems.service.AuthService;
import com.ems.util.Components;
import com.ems.view.menus.*;
import java.util.Scanner;

public class AdminMenu {
    private AuthService authService;
    private Scanner scanner;
    private EmployeeMenu employeeMenu;
    private DepartmentMenu departmentMenu;
    private PayrollMenu payrollMenu;
    private AttendanceMenu attendanceMenu;
    private LeaveMenu leaveMenu;
    private UserMenu userMenu;

    public AdminMenu(AuthService authService, Scanner scanner) {
        this.authService = authService;
        this.scanner = scanner;
        this.employeeMenu = new EmployeeMenu(scanner);
        this.departmentMenu = new DepartmentMenu(scanner);
        this.payrollMenu = new PayrollMenu(scanner);
        this.attendanceMenu = new AttendanceMenu(scanner, authService);
        this.leaveMenu = new LeaveMenu(scanner, authService);
        this.userMenu = new UserMenu(scanner);
    }

    public void display() {
        boolean running = true;
        
        while (running) {
            Components.clearScreen();
            System.out.print(ADMIN_MENU);
            int choice = getIntInput("[+] Enter choice: ");
            
            switch (choice) {
                case 1:
                    employeeMenu.display();
                    break;
                case 2:
                    departmentMenu.display();
                    break;
                case 3:
                    payrollMenu.display();
                    break;
                case 4:
                    attendanceMenu.displayAdminMenu();
                    break;
                case 5:
                    leaveMenu.displayAdminMenu();
                    break;
                case 6:
                    searchAndFilterMenu();
                    break;
                case 7:
                    reportsMenu();
                    break;
                case 8:
                    userMenu.display();
                    break;
                case 9:
                    settingsMenu();
                    break;
                case 0:
                    authService.logout();
                    System.out.println("Logged out successfully.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public final static String ADMIN_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                   STAFF MENU                     ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Employee Management                         ║
            ║ [2]. Department Management                       ║
            ║ [3]. Salary & Payroll                            ║                       
            ║ [4]. Attendance                                  ║                          
            ║ [5]. Leave Management                            ║
            ║ [6]. Search & Filter Tools                       ║    
            ║ [7]. Reports                                     ║
            ║ [8]. User Management                             ║
            ║ [9]. Settings                                    ║
            ║ [0]. Logout                                      ║                                 
            ╚══════════════════════════════════════════════════╝
            """;

    private void searchAndFilterMenu() {
        employeeMenu.displaySearchAndFilter();
    }

    private void reportsMenu() {
        employeeMenu.displayReports();
    }

    public final static String SETTINGS_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                   STAFF MENU                     ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Update Profile                              ║
            ║ [2]. Change Password                             ║
            ║ [3]. Manual Save Data to CSV                     ║                       
            ║ [0]. Back                                        ║                  
            ╚══════════════════════════════════════════════════╝
            """;

    private void settingsMenu() {
        boolean running = true;
        
        while (running) {
            Components.clearScreen();
            System.out.print(SETTINGS_MENU);
            int choice = getIntInput("[+] Enter choice: ");
            
            switch (choice) {
                case 1:
                    System.out.println("Profile update feature coming soon...");
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    manualSaveData();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manualSaveData() {
        System.out.println("\nSaving all data to CSV files...");
        DataStore.getInstance().saveAllData();
        System.out.println("Manual save completed successfully!");
    }

    private void changePassword() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                CHANGING PASSWORD                 ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter old password: ");
        String oldPassword = scanner.nextLine();
        System.out.print("[+] Enter new password: ");
        String newPassword = scanner.nextLine();
        System.out.print("[+] Confirm new password: ");
        String confirmPassword = scanner.nextLine();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }
        
        if (authService.changePassword(oldPassword, newPassword)) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Failed to change password. Please check your old password.");
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
