package com.ems.view.menus;

import com.ems.model.Attendance;
import com.ems.service.AttendanceService;
import com.ems.service.AuthService;
import com.ems.util.Components;
import com.ems.util.Table;
import com.ems.util.BorderStyle;

import java.util.List;
import java.util.Scanner;

public class AttendanceMenu {
    private Scanner scanner;
    private AttendanceService attendanceService;
    private AuthService authService;

    public AttendanceMenu(Scanner scanner, AuthService authService) {
        this.scanner = scanner;
        this.authService = authService;
        this.attendanceService = new AttendanceService();
    }

    public final static String ATTENDANCE_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                     ATTENDANCE                   ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Employee Check-In                           ║
            ║ [2]. Employee Check-Out                          ║
            ║ [3]. Show All Attendance Logs                    ║                       
            ║ [4]. Attendance Report by Employee               ║                             
            ║ [0]. Back                                        ║                                 
            ╚══════════════════════════════════════════════════╝
            """;

    public void displayAdminMenu() {
        boolean running = true;
        
        while (running) {
            Components.clearScreen();
            System.out.print(ATTENDANCE_MENU);
            int choice = getIntInput("[+] Enter choice: ");
            
            switch (choice) {
                case 1:
                    checkIn();
                    break;
                case 2:
                    checkOut();
                    break;
                case 3:
                    showAllAttendance();
                    break;
                case 4:
                    showEmployeeAttendance();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void checkIn() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                     Check In                     ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        
        if (attendanceService.checkIn(empId)) {
            System.out.println("✓ Employee checked in successfully!");
        } else {
            System.out.println("✗ Failed to check in. Employee may have already checked in today.");
        }
    }

    private void checkOut() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                    Check Out                     ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        
        if (attendanceService.checkOut(empId)) {
            System.out.println("✓ Employee checked out successfully!");
        } else {
            System.out.println("✗ Failed to check out. Employee may not be checked in.");
        }
    }

    private void showAllAttendance() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        
        if (attendances.isEmpty()) {
            System.out.println("No attendance records found.");
        } else {
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                             ALL ATTENDANCE LOGS                           ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════╝");
            Table table = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Attendance ID");
            table.addCell("Employee ID");
            table.addCell("Date");
            table.addCell("Check-In");
            table.addCell("Check-Out");
            table.addCell("Status");
            
            for (Attendance att : attendances) {
                table.addCell(att.getAttendanceId());
                table.addCell(att.getEmployeeId());
                table.addCell(att.getDate().toString());
                table.addCell(att.getCheckIn() != null ? att.getCheckIn().toString() : "N/A");
                table.addCell(att.getCheckOut() != null ? att.getCheckOut().toString() : "N/A");
                table.addCell(att.getStatus());
            }
            
            System.out.println(table.render());
        }
    }

    private void showEmployeeAttendance() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║            Show Employee Attendance              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID: ");
        String empId = scanner.nextLine();
        
        List<Attendance> attendances = attendanceService.getEmployeeAttendance(empId);
        
        if (attendances.isEmpty()) {
            System.out.println("No attendance records found for this employee.");
        } else {
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                 ATTENDANCE REPORT                ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            Table table = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Attendance ID");
            table.addCell("Employee ID");
            table.addCell("Date");
            table.addCell("Check-In");
            table.addCell("Check-Out");
            table.addCell("Status");
            
            for (Attendance att : attendances) {
                table.addCell(att.getAttendanceId());
                table.addCell(att.getEmployeeId());
                table.addCell(att.getDate().toString());
                table.addCell(att.getCheckIn() != null ? att.getCheckIn().toString() : "N/A");
                table.addCell(att.getCheckOut() != null ? att.getCheckOut().toString() : "N/A");
                table.addCell(att.getStatus());
            }
            
            System.out.println(table.render());
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
