package com.ems.view.menus;

import com.ems.model.Department;
import com.ems.service.DepartmentService;
import com.ems.util.Components;
import com.ems.util.InputValidator;
import com.ems.util.Table;
import com.ems.util.BorderStyle;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

public class DepartmentMenu {
    private Scanner scanner;
    private DepartmentService departmentService;

    public DepartmentMenu(Scanner scanner) {
        this.scanner = scanner;
        this.departmentService = new DepartmentService();
    }

    public void display() {
        boolean running = true;
        
        while (running) {
            Components.clearScreen();
            System.out.print(DEPARTMENT_MANAGEMENT_MENU);
            int choice = getIntInput("[+] Enter choice: ");
            
            switch (choice) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    viewDepartments();
                    break;
                case 3:
                    updateDepartment();
                    break;
                case 4:
                    deleteDepartment();
                    break;
                case 5:
                    countEmployees();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public final static String DEPARTMENT_MANAGEMENT_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║               DEPARTMENT MANAGEMENT              ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Add Department                              ║
            ║ [2]. View Departments                            ║
            ║ [3]. Update Department                           ║                       
            ║ [4]. Delete Department                           ║                          
            ║ [5]. Count Employees in Each Department          ║
            ║ [0]. Back                                        ║                                 
            ╚══════════════════════════════════════════════════╝
            """;

    private void addDepartment() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                Add New Department                ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        String name;
        do {
            System.out.print("Department Name: ");
            name = scanner.nextLine();
            if (InputValidator.isEmpty(name)) {
                System.out.println("Department Name cannot be empty.");
            }
        } while (InputValidator.isEmpty(name));

        String description;
        do {
            System.out.print("Description: ");
            description = scanner.nextLine();
            if (InputValidator.isEmpty(description)) {
                System.out.println("Description cannot be empty.");
            }
        } while (InputValidator.isEmpty(description));

        System.out.print("Manager ID (optional, press Enter to skip): ");
        String managerId = scanner.nextLine();

        if (InputValidator.isEmpty(managerId)) {
            managerId = null;
        }

        Department department = new Department(null, name, description, managerId);

        if (departmentService.addDepartment(department)) {
            System.out.println("✓ Department added successfully! ID: " + department.getDepartmentId());

            System.out.print("Add another department? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                addDepartment();
            }
        } else {
            System.out.println("✗ Failed to add department.");
        }
    }

//    public void viewDepartments() {
//        List<Department> departments = departmentService.getAllDepartments();
//
//        if (departments.isEmpty()) {
//            System.out.println("No departments found.");
//        } else {
//            System.out.println("╔═════════════════════════════════════════════════════════════════╗");
//            System.out.println("║                          ALL DEPARTMENTS                        ║");
//            System.out.println("╚═════════════════════════════════════════════════════════════════╝");
//            Table table = new Table(4, BorderStyle.UNICODE_ROUND_BOX_WIDE);
//            table.addCell("ID");
//            table.addCell("Name");
//            table.addCell("Description");
//            table.addCell("Manager ID");
//
//            for (Department dept : departments) {
//                table.addCell(dept.getDepartmentId());
//                table.addCell(dept.getDepartmentName());
//                table.addCell(dept.getDescription());
//                table.addCell(dept.getManagerId() != null ? dept.getManagerId() : "N/A");
//            }
//
//            System.out.println(table.render());
//        }
//    }

    public void viewDepartments() {
        List<Department> departments = departmentService.getAllDepartments();

        if (departments.isEmpty()) {
            System.out.println("No departments found.");
            Components.pause();
            return;
        }

        final int ROWS_PER_PAGE = 5;
        int totalDepartments = departments.size();
        int totalPages = (totalDepartments + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE;
        int currentPage = 1;

        while (true) {
            Components.clearScreen();

            int start = (currentPage - 1) * ROWS_PER_PAGE;
            int end = Math.min(start + ROWS_PER_PAGE, totalDepartments);
            List<Department> pageDepartments = departments.subList(start, end);

            // Header
            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                         LIST DEPARTMENTS - Page " + currentPage + " of " + totalPages + "                           ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════╝");

            // Table
            Table table = new Table(4, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Description");
            table.addCell("Manager ID");

            for (Department dept : pageDepartments) {
                table.addCell(dept.getDepartmentId());
                table.addCell(dept.getDepartmentName());
                table.addCell(dept.getDescription());
                table.addCell(dept.getManagerId() != null ? dept.getManagerId() : "N/A");
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


    private void displaySingleDepartment(Department dept) {
        if (dept == null) {
            System.out.println("Department details unavailable.");
            return;
        }

        Table table = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell("Field");
        table.addCell("Value");

        table.addCell("ID");
        table.addCell(dept.getDepartmentId());

        table.addCell("Name");
        table.addCell(dept.getDepartmentName());

        table.addCell("Description");
        table.addCell(dept.getDescription() != null ? dept.getDescription() : "N/A");

        table.addCell("Manager ID");
        table.addCell(dept.getManagerId() != null ? dept.getManagerId() : "None Assigned");

        System.out.println(table.render());
    }

    private void updateDepartment() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                Update Department                 ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Department ID to update: ");
        String id = scanner.nextLine();

        Department dept = departmentService.getDepartmentById(id);

        if (dept == null) {
            System.out.println("Department not found.");
            return;
        }

        System.out.println("Current Department Details:");
        displaySingleDepartment(dept);

        System.out.println("Press Enter to skip any field.");

        String name;
        boolean nameValid = false;
        do {
            System.out.print("Name [" + dept.getDepartmentName() + "]: ");
            name = scanner.nextLine();

            if (!InputValidator.isEmpty(name)) {
                dept.setDepartmentName(name);
                nameValid = true;
            } else if (InputValidator.isEmpty(name) && InputValidator.isEmpty(dept.getDepartmentName())) {
                System.out.println("Department Name cannot be empty. Please enter a value or re-enter the original name.");
            } else {
                nameValid = true;
            }
        } while (!nameValid);

        String desc;
        boolean descValid = false;
        do {
            System.out.print("Description [" + (dept.getDescription() != null ? dept.getDescription() : "N/A") + "]: ");
            desc = scanner.nextLine();

            if (!InputValidator.isEmpty(desc)) {
                dept.setDescription(desc);
                descValid = true;
            } else if (InputValidator.isEmpty(desc) && InputValidator.isEmpty(dept.getDescription())) {
                System.out.println("Description cannot be empty. Please enter a value.");
            } else {
                descValid = true;
            }
        } while (!descValid);

        System.out.print("Manager ID [" + (dept.getManagerId() != null ? dept.getManagerId() : "None") + "]: ");
        String managerId = scanner.nextLine();

        if (!InputValidator.isEmpty(managerId)) {
            dept.setManagerId(managerId);
        } else if (managerId.equalsIgnoreCase("none")) {
            dept.setManagerId(null);
        }

        if (departmentService.updateDepartment(dept)) {
            System.out.println("✓ Department updated successfully!");

            System.out.print("Update another department? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                updateDepartment();
            }
        } else {
            System.out.println("✗ Failed to update department.");
        }
    }

    private void deleteDepartment() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                 Delete Department                ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Department ID to delete: ");
        String id = scanner.nextLine();
        
        System.out.print("Are you sure? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            if (departmentService.deleteDepartment(id)) {
                System.out.println("✓ Department deleted successfully!");
                
                System.out.print("Delete another department? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    deleteDepartment();
                }
            } else {
                System.out.println("✗ Failed to delete department.");
            }
        }
    }

    private void countEmployees() {
        Map<String, Integer> counts = departmentService.countEmployeesByDepartment();
        
        if (counts.isEmpty()) {
            System.out.println("No departments found.");
        } else {
            System.out.println("╔═════════════════════════════════════╗");
            System.out.println("║    EMPLOYEE COUNT BY DEPARTMENT     ║");
            System.out.println("╚═════════════════════════════════════╝");
            Table table = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("Department");
            table.addCell("Employee Count");
            
            counts.forEach((dept, count) -> {
                table.addCell(dept);
                table.addCell(String.valueOf(count));
            });
            
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
