package com.ems.view.menus;

import com.ems.model.LeaveRequest;
import com.ems.service.AuthService;
import com.ems.service.LeaveService;
import com.ems.util.Components;
import com.ems.util.Table;
import com.ems.util.BorderStyle;
import java.util.List;
import java.util.Scanner;

public class LeaveMenu {
    private Scanner scanner;
    private LeaveService leaveService;
    private AuthService authService;

    public LeaveMenu(Scanner scanner, AuthService authService) {
        this.scanner = scanner;
        this.authService = authService;
        this.leaveService = new LeaveService();
    }

    public final static String LEAVE_MANAGEMENT_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                SALARY & PAYROll                  ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. View All Leave Requests                     ║
            ║ [2]. Approve Leave                               ║
            ║ [3]. Reject Leave                                ║                       
            ║ [4]. View Leave History                          ║                          
            ║ [0]. Back                                        ║                                 
            ╚══════════════════════════════════════════════════╝
            """;

    public void displayAdminMenu() {
        boolean running = true;
        
        while (running) {
            Components.clearScreen();
            System.out.print(LEAVE_MANAGEMENT_MENU);
            int choice = getIntInput("[+]Enter choice: ");
            
            switch (choice) {
                case 1:
                    viewAllLeaveRequests();
                    break;
                case 2:
                    approveLeave();
                    break;
                case 3:
                    rejectLeave();
                    break;
                case 4:
                    viewLeaveHistory();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

//    private void viewAllLeaveRequests() {
//        List<LeaveRequest> requests = leaveService.getPendingLeaves();
//
//        if (requests.isEmpty()) {
//            System.out.println("No pending leave requests.");
//        } else {
//            System.out.println("╔═════════════════════════════════════════════════════════════════════════════╗");
//            System.out.println("║                             PENDING LEAVE REQUESTS                          ║");
//            System.out.println("╚═════════════════════════════════════════════════════════════════════════════╝");
//            Table table = new Table(7, BorderStyle.UNICODE_ROUND_BOX_WIDE);
//            table.addCell("Leave ID");
//            table.addCell("Employee ID");
//            table.addCell("Type");
//            table.addCell("Start Date");
//            table.addCell("End Date");
//            table.addCell("Reason");
//            table.addCell("Status");
//
//            for (LeaveRequest req : requests) {
//                table.addCell(req.getLeaveId());
//                table.addCell(req.getEmployeeId());
//                table.addCell(req.getLeaveType());
//                table.addCell(req.getStartDate().toString());
//                table.addCell(req.getEndDate().toString());
//                table.addCell(req.getReason());
//                table.addCell(req.getStatus());
//            }
//
//            System.out.println(table.render());
//        }
//    }

    private void viewAllLeaveRequests() {
        List<LeaveRequest> requests = leaveService.getPendingLeaves();

        if (requests.isEmpty()) {
            System.out.println("No pending leave requests.");
            Components.pause();
            return;
        }

        final int ROWS_PER_PAGE = 5;
        int total = requests.size();
        int totalPages = (total + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE;
        int currentPage = 1;

        while (true) {
            Components.clearScreen();

            int start = (currentPage - 1) * ROWS_PER_PAGE;
            int end = Math.min(start + ROWS_PER_PAGE, total);
            List<LeaveRequest> pageRequests = requests.subList(start, end);

            // Header
            System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                       PENDING LEAVE REQUESTS - Page " + currentPage + " of " + totalPages + "                         ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

            // Table
            Table table = new Table(7, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Leave ID");
            table.addCell("Employee ID");
            table.addCell("Type");
            table.addCell("Start Date");
            table.addCell("End Date");
            table.addCell("Reason");
            table.addCell("Status");

            for (LeaveRequest req : pageRequests) {
                table.addCell(req.getLeaveId());
                table.addCell(req.getEmployeeId());
                table.addCell(req.getLeaveType());
                table.addCell(req.getStartDate().toString());
                table.addCell(req.getEndDate().toString());
                table.addCell(req.getReason());
                table.addCell(req.getStatus());
            }

            System.out.println(table.render());

            // Navigation
            System.out.print("Press [N] Next | [P] Previous | [B] Back to Menu: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("n") && currentPage < totalPages) {
                currentPage++;
            } else if (choice.equals("p") && currentPage > 1) {
                currentPage--;
            } else if (choice.equals("b")) {
                return;
            } else {
                System.out.println("Invalid input or boundary reached! Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }


    private void approveLeave() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                   Approve Leave                  ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Leave ID to approve: ");
        String leaveId = scanner.nextLine();
        
        if (leaveService.approveLeave(leaveId)) {
            System.out.println("✓ Leave approved successfully!");
            
            System.out.print("Approve another leave? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                approveLeave();
            }
        } else {
            System.out.println("✗ Failed to approve leave. It may not exist or is not pending.");
        }
    }

    private void rejectLeave() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                   Reject Leave                   ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Leave ID to reject: ");
        String leaveId = scanner.nextLine();
        
        if (leaveService.rejectLeave(leaveId)) {
            System.out.println("✓ Leave rejected successfully!");
            
            System.out.print("Reject another leave? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                rejectLeave();
            }
        } else {
            System.out.println("✗ Failed to reject leave. It may not exist or is not pending.");
        }
    }

//    private void viewLeaveHistory() {
//        List<LeaveRequest> history = leaveService.getAllLeaveRequests();
//
//        if (history.isEmpty()) {
//            System.out.println("No leave history found.");
//        } else {
//            System.out.println("╔═════════════════════════════════════════════════════════════════════════════╗");
//            System.out.println("║                                  LEAVE HISTORY                              ║");
//            System.out.println("╚═════════════════════════════════════════════════════════════════════════════╝");
//            Table table = new Table(7, BorderStyle.UNICODE_ROUND_BOX_WIDE);
//            table.addCell("Leave ID");
//            table.addCell("Employee ID");
//            table.addCell("Type");
//            table.addCell("Start Date");
//            table.addCell("End Date");
//            table.addCell("Reason");
//            table.addCell("Status");
//
//            for (LeaveRequest req : history) {
//                table.addCell(req.getLeaveId());
//                table.addCell(req.getEmployeeId());
//                table.addCell(req.getLeaveType());
//                table.addCell(req.getStartDate().toString());
//                table.addCell(req.getEndDate().toString());
//                table.addCell(req.getReason());
//                table.addCell(req.getStatus());
//            }
//
//            System.out.println(table.render());
//        }
//    }

    private void viewLeaveHistory() {
        List<LeaveRequest> history = leaveService.getAllLeaveRequests();

        if (history.isEmpty()) {
            System.out.println("No leave history found.");
            Components.pause();
            return;
        }

        final int ROWS_PER_PAGE = 5;
        int total = history.size();
        int totalPages = (total + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE;
        int currentPage = 1;

        while (true) {
            Components.clearScreen();

            int start = (currentPage - 1) * ROWS_PER_PAGE;
            int end = Math.min(start + ROWS_PER_PAGE, total);
            List<LeaveRequest> pageHistory = history.subList(start, end);

            // Header
            System.out.println("╔════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                              LEAVE HISTORY - Page " + currentPage + " of " + totalPages + "                                ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════════╝");

            // Table
            Table table = new Table(7, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Leave ID");
            table.addCell("Employee ID");
            table.addCell("Type");
            table.addCell("Start Date");
            table.addCell("End Date");
            table.addCell("Reason");
            table.addCell("Status");

            for (LeaveRequest req : pageHistory) {
                table.addCell(req.getLeaveId());
                table.addCell(req.getEmployeeId());
                table.addCell(req.getLeaveType());
                table.addCell(req.getStartDate().toString());
                table.addCell(req.getEndDate().toString());
                table.addCell(req.getReason());
                table.addCell(req.getStatus());
            }

            System.out.println(table.render());

            // Navigation
            System.out.print("Press [N] Next | [P] Previous | [B] Back to Menu: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("n") && currentPage < totalPages) {
                currentPage++;
            } else if (choice.equals("p") && currentPage > 1) {
                currentPage--;
            } else if (choice.equals("b")) {
                return;
            } else {
                System.out.println("Invalid input or boundary reached! Press Enter to continue...");
                scanner.nextLine();
            }
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
