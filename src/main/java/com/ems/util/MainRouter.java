package com.ems.util;

import com.ems.data.DataStore;
import com.ems.service.AuthService;
import com.ems.view.AdminMenu;
import com.ems.view.StaffMenu;

import java.util.Scanner;

public class MainRouter {
    private Scanner scanner;
    private AuthService authService;

    public MainRouter() {
        this.scanner = new Scanner(System.in);
        this.authService = new AuthService();
    }

    public void start() {
        boolean running = true;
        
        while (running) {
            Components.clearScreen();
            System.out.print(MAIN_MENU);
            int choice = getIntInput("[+] Enter choice: ");
            
            switch (choice) {
                case 1:
                    loginMenu();
                    break;
                case 2:
                    System.out.print(SYSTEM_INFO);
                    break;
                case 3:
                    System.out.print(Demo.DEMO_CREDENTIALS);
                    break;
                case 0:
                    System.out.println("\nSaving all data...");
                    DataStore.getInstance().saveAllData();
                    System.out.println("Thank you for using Employee Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }

    public final static String MAIN_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                  WELCOME to EMS                  ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Login                                       ║
            ║ [2]. System Info                                 ║
            ║ [3]. View Demo Credentials                       ║                       
            ║ [0]. Exit                                        ║                                     
            ╚══════════════════════════════════════════════════╝  
            """;

    private void loginMenu() {
        Components.clearScreen();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                   LOGIN SYSTEM                   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("[+] Enter Password: ");
        String password = scanner.nextLine();
        
        if (authService.login(username, password)) {
            System.out.println("→ Login Successful!");
            
            if (authService.isAdmin()) {
                new AdminMenu(authService, scanner).display();
            } else if (authService.isStaff()) {
                new StaffMenu(authService, scanner).display();
            }
        } else {
            System.out.println("→ Login Failed (Invalid username/password)");
        }
    }


    public final static String SYSTEM_INFO = """
            ╔══════════════════════════════════════════════════════════════════════════════╗
            ║                                 SYSTEM INFO                                  ║
            ╠══════════════════════════════════════════════════════════════════════════════╣ 
            ║  -> Employee Management System v1.0                                          ║
            ║  -> Developed for comprehensive employee management                          ║
            ║  -> Features: Employee, Department, Payroll, Attendance, Leave Management    ║
            ║  -> Data Persistence: CSV files in /data directory                           ║            
            ╚══════════════════════════════════════════════════════════════════════════════╝
            
            """;

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
