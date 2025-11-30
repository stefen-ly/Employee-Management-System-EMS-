package com.ems.view;

import com.ems.service.*;
import com.ems.model.*;
import com.ems.util.Table;
import com.ems.util.BorderStyle;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class StaffMenu {
    private AuthService authService;
    private Scanner scanner;
    private EmployeeService employeeService;
    private AttendanceService attendanceService;
    private LeaveService leaveService;
    private PayrollService payrollService;

    public StaffMenu(AuthService authService, Scanner scanner) {
        this.authService = authService;
        this.scanner = scanner;
        this.employeeService = new EmployeeService();
        this.attendanceService = new AttendanceService();
        this.leaveService = new LeaveService();
        this.payrollService = new PayrollService();
    }

    public void display() {
        boolean running = true;
        
        while (running) {
            System.out.print(displayMenu);
            int choice = getIntInput("[+] Enter choice: ");
            
            switch (choice) {
                case 1:
                    viewMyProfile();
                    break;
                case 2:
                    attendanceMenu();
                    break;
                case 3:
                    leaveMenu();
                    break;
                case 4:
                    salaryMenu();
                    break;
                case 5:
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

    public final static String displayMenu = """
            ╔══════════════════════════════════════════════════╗
            ║                   STAFF MENU                     ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. My Profile                                  ║
            ║ [2]. Attendance                                  ║
            ║ [3]. Leave Management                            ║                       
            ║ [4]. Salary (View Only)                          ║                          
            ║ [5]. Settings                                    ║
            ║ [0]. Logout                                      ║                                     
            ╚══════════════════════════════════════════════════╝
            """;

    private void viewMyProfile() {
        String employeeId = authService.getCurrentUser().getEmployeeId();
        if (employeeId == null) {
            System.out.println("No employee profile linked to this account.");
            return;
        }
        
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee != null) {
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║                   MY PROFILE                     ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            Table table = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Field");
            table.addCell("Value");
            table.addCell("Employee ID");
            table.addCell(employee.getEmployeeId());
            table.addCell("Name");
            table.addCell(employee.getName());
            table.addCell("Date of Birth");
            table.addCell(employee.getDateOfBirth().toString());
            table.addCell("Age");
            table.addCell(String.valueOf(employee.getAge()));
            table.addCell("Gender");
            table.addCell(employee.getGender());
            table.addCell("Email");
            table.addCell(employee.getEmail());
            table.addCell("Phone");
            table.addCell(employee.getPhoneNumber());
            table.addCell("Address");
            table.addCell(employee.getAddress());
            table.addCell("Position");
            table.addCell(employee.getPosition());
            table.addCell("Department");
            table.addCell(employee.getDepartmentId());
            table.addCell("Salary");
            table.addCell(String.format("%.2f", employee.getSalary()));
            
            System.out.println(table.render());
        } else {
            System.out.println("Profile not found.");
        }
    }

    public final static String ATTENDANCE_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                   ATTENDANCE                     ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Check-In                                    ║
            ║ [2]. Check-Out                                   ║
            ║ [3]. My Attendance History                       ║                       
            ║ [0]. Back                                        ║                                     
            ╚══════════════════════════════════════════════════╝  
            """;

    private void attendanceMenu() {
        boolean running = true;
        
        while (running) {
            System.out.print(ATTENDANCE_MENU);
            int choice = getIntInput("[+] Enter choice: ");
            String employeeId = authService.getCurrentUser().getEmployeeId();
            
            switch (choice) {
                case 1:
                    if (attendanceService.checkIn(employeeId)) {
                        System.out.println("✓ Checked in successfully!");
                    } else {
                        System.out.println("✗ Already checked in today.");
                    }
                    break;
                case 2:
                    if (attendanceService.checkOut(employeeId)) {
                        System.out.println("✓ Checked out successfully!");
                    } else {
                        System.out.println("✗ Unable to check out. Not checked in or already checked out.");
                    }
                    break;
                case 3:
                    List<Attendance> history = attendanceService.getEmployeeAttendance(employeeId);
                    if (history.isEmpty()) {
                        System.out.println("No attendance records found.");
                    } else {
                        Table table = new Table(5, BorderStyle.UNICODE_ROUND_BOX_WIDE);
                        table.addCell("Date");
                        table.addCell("Check-In");
                        table.addCell("Check-Out");
                        table.addCell("Status");
                        table.addCell("Hours");
                        
                        for (Attendance att : history) {
                            table.addCell(att.getDate().toString());
                            table.addCell(att.getCheckIn() != null ? att.getCheckIn().toString() : "N/A");
                            table.addCell(att.getCheckOut() != null ? att.getCheckOut().toString() : "N/A");
                            table.addCell(att.getStatus());
                            if (att.getCheckIn() != null && att.getCheckOut() != null) {
                                long hours = java.time.Duration.between(att.getCheckIn(), att.getCheckOut()).toHours();
                                table.addCell(String.valueOf(hours) + "h");
                            } else {
                                table.addCell("N/A");
                            }
                        }
                        
                        System.out.println(table.render());
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public final static String LEAVE_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                      LEAVE                       ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Request Leave                               ║
            ║ [2]. View My Leave Status                        ║
            ║ [3]. Leave History                               ║                       
            ║ [0]. Back                                        ║                                     
            ╚══════════════════════════════════════════════════╝  
            """;

    private void leaveMenu() {
        boolean running = true;
        
        while (running) {
            System.out.print(LEAVE_MENU);
            
            int choice = getIntInput("[+] Enter choice: ");
            String employeeId = authService.getCurrentUser().getEmployeeId();
            
            switch (choice) {
                case 1:
                    requestLeave(employeeId);
                    break;
                case 2:
                case 3:
                    List<LeaveRequest> leaves = leaveService.getEmployeeLeaves(employeeId);
                    if (leaves.isEmpty()) {
                        System.out.println("No leave records found.");
                    } else {
                        Table table = new Table(6, BorderStyle.UNICODE_ROUND_BOX_WIDE);
                        table.addCell("Leave ID");
                        table.addCell("Type");
                        table.addCell("Start Date");
                        table.addCell("End Date");
                        table.addCell("Reason");
                        table.addCell("Status");
                        
                        for (LeaveRequest leave : leaves) {
                            table.addCell(leave.getLeaveId());
                            table.addCell(leave.getLeaveType());
                            table.addCell(leave.getStartDate().toString());
                            table.addCell(leave.getEndDate().toString());
                            table.addCell(leave.getReason());
                            table.addCell(leave.getStatus());
                        }
                        
                        System.out.println(table.render());
                    }
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public final static String SALARY_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                     LEAVE                        ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. View Salary Summary                         ║
            ║ [2]. Download / Print Salary Slip                ║
            ║ [0]. Back                                        ║                                     
            ╚══════════════════════════════════════════════════╝  
            """;

    private void salaryMenu() {
        boolean running = true;
        
        while (running) {
            System.out.print(SALARY_MENU);
            
            int choice = getIntInput("[+] Enter choice: ");
            String employeeId = authService.getCurrentUser().getEmployeeId();
            
            switch (choice) {
                case 1:
                case 2:
                    String slip = payrollService.generateSalarySlip(employeeId);
                    System.out.println(slip);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    public final static String SETTINGS = """
            ╔══════════════════════════════════════════════════╗
            ║                    SETTINGS                      ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Update My Profile                           ║
            ║ [2]. Change Password                             ║
            ║ [0]. Back                                        ║                                     
            ╚══════════════════════════════════════════════════╝  
            """;

    private void settingsMenu() {
        boolean running = true;
        
        while (running) {
            System.out.print(SETTINGS);
            
            int choice = getIntInput("[+] Enter choice: ");
            
            switch (choice) {
                case 1:
                    System.out.println("Profile update feature coming soon...");
                    break;
                case 2:
                    changePassword();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void changePassword() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                   Change Password                ║");
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

    public final static String REQUEST_LEAVE_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                  REQUEST LEAVE                   ║
            ╠══════════════════════════════════════════════════╣
            ║ -> Select Leave Type:                            ║
            ║ [1]. Sick Leave                                  ║
            ║ [2]. Annual Leave                                ║
            ║ [3]. Emergency Leave                             ║ 
            ║ [4]. Personal Leave                              ║
            ║ [0]. Maternity/Paternity Leave                   ║                                     
            ╚══════════════════════════════════════════════════╝  
            """;

    private void requestLeave(String employeeId) {
        System.out.print(REQUEST_LEAVE_MENU);
        int typeChoice = getIntInput("[+] Enter choice: ");
        
        String leaveType;
        switch (typeChoice) {
            case 1: leaveType = "SICK"; break;
            case 2: leaveType = "ANNUAL"; break;
            case 3: leaveType = "EMERGENCY"; break;
            case 4: leaveType = "PERSONAL"; break;
            case 0: leaveType = "MATERNITY/PATERNITY"; break;
            default:
                System.out.println("Invalid leave type selected.");
                return;
        }
        
        LocalDate startDate = null;
        while (startDate == null) {
            System.out.print("[+] Enter start date (YYYY-MM-DD): ");
            String startDateStr = scanner.nextLine();
            try {
                startDate = LocalDate.parse(startDateStr);
                if (startDate.isBefore(LocalDate.now())) {
                    System.out.println("Start date cannot be in the past.");
                    startDate = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        
        // Get end date
        LocalDate endDate = null;
        while (endDate == null) {
            System.out.print("[+] Enter end date (YYYY-MM-DD): ");
            String endDateStr = scanner.nextLine();
            try {
                endDate = LocalDate.parse(endDateStr);
                if (endDate.isBefore(startDate)) {
                    System.out.println("End date cannot be before start date.");
                    endDate = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        
        System.out.print("[+] Enter reason for leave: ");
        String reason = scanner.nextLine();
        
        if (reason.trim().isEmpty()) {
            System.out.println("Reason cannot be empty.");
            return;
        }
        
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(employeeId);
        leaveRequest.setLeaveType(leaveType);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setReason(reason);
        
        if (leaveService.requestLeave(leaveRequest)) {
            System.out.println("\n✓ Leave request submitted successfully!");
            System.out.println("Your request is pending approval from management.");
            
            System.out.print("\nRequest another leave? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                requestLeave(employeeId);
            }
        } else {
            System.out.println("✗ Failed to submit leave request. Please try again.");
        }
    }
}
