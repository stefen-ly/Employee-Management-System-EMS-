package com.ems.view.menus;

import com.ems.model.SalaryRecord;
import com.ems.service.PayrollService;
import com.ems.util.Components;

import java.util.Scanner;

public class PayrollMenu {
    private Scanner scanner;
    private PayrollService payrollService;

    public PayrollMenu(Scanner scanner) {
        this.scanner = scanner;
        this.payrollService = new PayrollService();
    }

    public void display() {
        boolean running = true;
        
        while (running) {
            Components.clearScreen();
            System.out.print(SALARY_N_PAYROLL);
            int choice = getIntInput("[+] Enter choice: ");
            
            switch (choice) {
                case 1:
                    setMonthlySalary();
                    break;
                case 2:
                    addBonus();
                    break;
                case 3:
                    addDeduction();
                    break;
                case 4:
                    calculateGrossSalary();
                    break;
                case 5:
                    calculateNetSalary();
                    break;
                case 6:
                    calculateTax();
                    break;
                case 7:
                    generateSalarySlip();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    public final static String SALARY_N_PAYROLL = """
            ╔══════════════════════════════════════════════════╗
            ║                SALARY & PAYROll                  ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Set Monthly Salary                          ║
            ║ [2]. Add Bonus                                   ║
            ║ [3]. Add Deduction                               ║                       
            ║ [4]. Calculate Gross Salary                      ║                          
            ║ [5]. Calculate Net Salary                        ║
            ║ [6]. Calculate Tax                               ║    
            ║ [7]. Generate Salary Slip                        ║
            ║ [0]. Back                                        ║                                 
            ╚══════════════════════════════════════════════════╝
            """;

    private void setMonthlySalary() {
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        
        SalaryRecord record = payrollService.createSalaryRecord(empId);
        if (record != null) {
            System.out.println("✓ Salary record created!");
            System.out.println(record);
        } else {
            System.out.println("✗ Failed to create salary record. Employee not found.");
        }
    }

    private void addBonus() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                     Add Bonus                    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        System.out.print("[+] Enter Bonus Amount: ");
        double bonus = getDoubleInput();
        
        if (payrollService.addBonus(empId, bonus)) {
            System.out.println("✓ Bonus added successfully!");
        } else {
            System.out.println("✗ Failed to add bonus.");
        }
    }

    private void addDeduction() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                   Add Deduction                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        System.out.print("[+] Enter Deduction Amount: ");
        double deduction = getDoubleInput();
        
        if (payrollService.addDeduction(empId, deduction)) {
            System.out.println("✓ Deduction added successfully!");
        } else {
            System.out.println("✗ Failed to add deduction.");
        }
    }

    private void calculateGrossSalary() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║             Calculate Gross Salary               ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        
        SalaryRecord record = payrollService.getLatestSalary(empId);
        if (record != null) {
            System.out.printf("Gross Salary: $%.2f\n", record.getGrossSalary());
        } else {
            System.out.println("No salary record found.");
        }
    }

    private void calculateNetSalary() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║              Calculate Net Salary                ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        
        SalaryRecord record = payrollService.getLatestSalary(empId);
        if (record != null) {
            System.out.printf("Net Salary: $%.2f\n", record.getNetSalary());
        } else {
            System.out.println("No salary record found.");
        }
    }

    private void calculateTax() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                   Calculate Tax                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        
        SalaryRecord record = payrollService.getLatestSalary(empId);
        if (record != null) {
            System.out.printf("Tax: $%.2f\n", record.getTax());
        } else {
            System.out.println("No salary record found.");
        }
    }

    private void generateSalarySlip() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║              Generate Slip Salary                ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        
        String slip = payrollService.generateSalarySlip(empId);
        System.out.println(slip);
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
