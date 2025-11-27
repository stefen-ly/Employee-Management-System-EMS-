package com.ems.view.menus;

import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.service.EmployeeService;
import com.ems.service.DepartmentService;
import com.ems.util.*;
import com.ems.data.DataStore;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {
    private Scanner scanner;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private DepartmentMenu departmentMenu;

    public EmployeeMenu(Scanner scanner) {
        this.scanner = scanner;
        // Assuming DataStore initialization is handled elsewhere or in the services
        this.employeeService = new EmployeeService();
        this.departmentService = new DepartmentService();
        this.departmentMenu = new DepartmentMenu(scanner);
    }

    public void display() {
        boolean running = true;

        while (running) {
            Components.clearScreen();
            System.out.print(EMPLOYEE_MANAGEMENT_MENU);
            int choice = getIntInput("[+] Enter choice: ");

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    updateEmployee();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    sortEmployees();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public final static String EMPLOYEE_MANAGEMENT_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                EMPLOYEE MANAGEMENT               ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Add Employee                                ║
            ║ [2]. View All Employees                          ║
            ║ [3]. Search Employee                             ║                       
            ║ [4]. Update Employee Information                 ║                          
            ║ [5]. Delete Employee                             ║
            ║ [6]. Sort Employees                              ║    
            ║ [0]. Back                                        ║                                 
            ╚══════════════════════════════════════════════════╝
            """;

    private void addEmployee() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                 Add New Employee                 ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        String name;
        boolean nameValid = false;
        do {
            System.out.print("[+] Enter Name: ");
            name = scanner.nextLine();
            if (InputValidator.isEmpty(name)) {
                System.out.println("Name cannot be empty.");
            } else if (!InputValidator.isValidName(name)) {
                System.out.println("Name is invalid. It must only contain letters, spaces, hyphens.");
            } else {
                nameValid = true;
            }
        } while (!nameValid);

        LocalDate dob = null;
        while (dob == null) {
            System.out.print("[+] Enter Date of Birth (yyyy-MM-dd): ");
            String dobStr = scanner.nextLine();
            try {
                dob = LocalDate.parse(dobStr);
                if (!InputValidator.isValidAge(dob)) {
                    System.out.println("Employee must be at least 18 years old and not older than 120!");
                    dob = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }

        String gender;
        do {
            System.out.print("[+] Enter Gender (M/F/Other): ");
            gender = scanner.nextLine();
            if (InputValidator.isEmpty(gender)) {
                System.out.println("Gender cannot be empty.");
            }
        } while (InputValidator.isEmpty(gender));


        // 4. Email Input with Auto-complete (REVERTED LOGIC)
        System.out.print("[+] Enter Email (@gmail.com will be added): ");
        String email = InputValidator.processEmail(scanner.nextLine());

        String phone;
        do {
            System.out.print("[+] Enter Phone Number: ");
            phone = scanner.nextLine();
            if (!InputValidator.isValidPhoneNumber(phone)) {
                System.out.println("Invalid phone number. Must be 9 to 10 digits.");
            }
        } while (!InputValidator.isValidPhoneNumber(phone));

        String address;
        do {
            System.out.print("[+] Enter Address: ");
            address = scanner.nextLine();
            if (InputValidator.isEmpty(address)) {
                System.out.println("Address cannot be empty.");
            }
        } while (InputValidator.isEmpty(address));

        String position;
        do {
            System.out.print("[+] Enter Position: ");
            position = scanner.nextLine();
            if (InputValidator.isEmpty(position)) {
                System.out.println("Position cannot be empty.");
            }
        } while (InputValidator.isEmpty(position));

        String deptId = null;
        Department department = null;
        while (department == null) {
            departmentMenu.viewDepartments();
            System.out.print("[+] Enter Department ID: ");
            deptId = scanner.nextLine();

            department = departmentService.getDepartmentById(deptId);

            if (department == null) {
                System.out.println("Department with ID '" + deptId + "' not found. Please enter a valid ID.");
            }
        }

        double salary = 0;
        while (salary <= 0) {
            System.out.print("[+] Enter Salary: ");
            try {
                salary = Double.parseDouble(scanner.nextLine());
                if (!InputValidator.isValidSalary(salary)) {
                    System.out.println("Salary must be greater than 0!");
                    salary = 0;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format. Salary must be a number.");
            }
        }

        Employee employee = new Employee(null, name, dob, gender, email, phone, address, position, deptId, salary);

        if (employeeService.addEmployee(employee)) {
            System.out.println("✓ Employee added successfully! ID: " + employee.getEmployeeId() + ", Dept: " + department.getDepartmentName());
            System.out.print("Add another employee? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                addEmployee();
            }
        } else {
            System.out.println("✗ Failed to add employee.");
        }
    }

    private void viewAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            Components.pause();
            return;
        }

        final int ROWS_PER_PAGE = 5;
        int totalPages = (employees.size() + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE;
        int currentPage = 1;

        Scanner inputScanner = new Scanner(System.in);

        while (true) {
            int start = (currentPage - 1) * ROWS_PER_PAGE;
            int end = Math.min(start + ROWS_PER_PAGE, employees.size());
            List<Employee> pageEmployees = employees.subList(start, end);

            Components.clearScreen();

            System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                 ALL EMPLOYEES - Page " + currentPage + " of " + totalPages + "                                   ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");

            Table table = new Table(11, BorderStyle.UNICODE_ROUND_BOX_WIDE);

            table.addCell("ID");
            table.addCell("Full Name");
            table.addCell("Age");
            table.addCell("Gender");
            table.addCell("Position");
            table.addCell("Department");
            table.addCell("Phone");
            table.addCell("Email");
            table.addCell("Address");
            table.addCell("Salary");
            table.addCell("Join Date");

            for (Employee emp : pageEmployees) {
                table.addCell(emp.getEmployeeId());
                table.addCell(emp.getName());
                table.addCell(String.valueOf(emp.getAge()));
                table.addCell(emp.getGender());
                table.addCell(emp.getPosition());
                table.addCell(getDepartmentName(emp.getDepartmentId()));
                table.addCell(emp.getPhoneNumber());
                table.addCell(emp.getEmail());
                table.addCell(emp.getAddress());
                table.addCell("$" + String.format("%,.2f", emp.getSalary()));

                String joinDate = emp.getHireDate() != null
                        ? emp.getHireDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        : "N/A";
                table.addCell(joinDate);
            }

            System.out.println(table.render());

            System.out.print("Press [N] Next | [P] Previous | [B] Back to Menu: ");
            String choice = inputScanner.nextLine().trim().toLowerCase();

            if (choice.equals("n") && currentPage < totalPages) {
                currentPage++;
            } else if (choice.equals("p") && currentPage > 1) {
                currentPage--;
            } else if (choice.equals("b")) {
                return;
            } else {
                System.out.println("Invalid input or boundary reached! Press Enter to continue...");
                inputScanner.nextLine();
            }
        }
    }

    public final static String SEARCH_OPTION = """
            ┌──────────────────────────────────────────────────┐
            │                 Search Employee                  │
            ├──────────────────────────────────────────────────┤
            │  [i] -> Search by ID                             │
            │  [n] -> Search by Name                           │
            │  [d] -> Search by Department ID                  │
            └──────────────────────────────────────────────────┘
            """;

    private void searchEmployee() {
        Components.clearScreen();
        System.out.print(SEARCH_OPTION);
        System.out.print("-> Choose option: ");
        String option = scanner.nextLine();

        List<Employee> results = null;

        switch (option.toLowerCase()) {
            case "i":
                System.out.print("[+] Enter Employee ID: ");
                String id = scanner.nextLine();
                Employee emp = employeeService.getEmployeeById(id);
                if (emp != null) {
                    displaySingleEmployee(emp);
                } else {
                    System.out.println("Employee not found.");
                }
                Components.pause();
                return;
            case "n":
                System.out.print("[+] Enter Name: ");
                String name = scanner.nextLine();
                results = employeeService.searchEmployees("name", name);
                break;
            case "d":
                System.out.print("[+] Enter Department ID: ");
                String dept = scanner.nextLine();
                results = employeeService.searchEmployees("department", dept);
                break;
            default:
                System.out.println("Invalid option.");
                Components.pause();
                return;
        }

        if (results != null && !results.isEmpty()) {
            displayEmployeeList(results);
        } else {
            System.out.println("No employees found matching your criteria.");
        }
        Components.pause();
    }

    private void displayEmployeeList(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
            Components.pause();
            return;
        }

        final int ROWS_PER_PAGE = 5;
        int totalEmployees = employees.size();
        int totalPages = (totalEmployees + ROWS_PER_PAGE - 1) / ROWS_PER_PAGE;
        int currentPage = 1;

        while (true) {
            Components.clearScreen();

            int start = (currentPage - 1) * ROWS_PER_PAGE;
            int end = Math.min(start + ROWS_PER_PAGE, totalEmployees);
            List<Employee> pageEmployees = employees.subList(start, end);

            // --- Header ---
            System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                  LIST EMPLOYEES - Page " + currentPage + " of " + totalPages + "                                    ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════╝");

            Table table = new Table(11, BorderStyle.UNICODE_ROUND_BOX_WIDE);
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Age");
            table.addCell("Gender");
            table.addCell("Position");
            table.addCell("Department");
            table.addCell("Phone");
            table.addCell("Email");
            table.addCell("Address");
            table.addCell("Salary");
            table.addCell("Join Date");

            for (Employee emp : pageEmployees) {
                table.addCell(emp.getEmployeeId());
                table.addCell(emp.getName());
                table.addCell(String.valueOf(emp.getAge()));
                table.addCell(emp.getGender());
                table.addCell(emp.getPosition());
                table.addCell(getDepartmentName(emp.getDepartmentId()));
                table.addCell(emp.getPhoneNumber());
                table.addCell(emp.getEmail());
                table.addCell(emp.getAddress());
                table.addCell("$" + String.format("%,.2f", emp.getSalary()));

                String joinDate = emp.getHireDate() != null
                        ? emp.getHireDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        : "N/A";
                table.addCell(joinDate);
            }

            System.out.println(table.render());

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

    private void displaySingleEmployee(Employee emp) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║        Employees Information       ║");
        System.out.println("╚════════════════════════════════════╝");
        Table table = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell("Field");
        table.addCell("Value");
        table.addCell("Employee ID");
        table.addCell(emp.getEmployeeId());
        table.addCell("Name");
        table.addCell(emp.getName());
        table.addCell("Date of Birth");
        table.addCell(emp.getDateOfBirth().toString());
        table.addCell("Age");
        table.addCell(String.valueOf(emp.getAge()));
        table.addCell("Gender");
        table.addCell(emp.getGender());
        table.addCell("Email");
        table.addCell(emp.getEmail());
        table.addCell("Phone");
        table.addCell(emp.getPhoneNumber());
        table.addCell("Address");
        table.addCell(emp.getAddress());
        table.addCell("Position");
        table.addCell(emp.getPosition());
        table.addCell("Department");
        table.addCell(getDepartmentName(emp.getDepartmentId())); // Display name
        table.addCell("Join Date");
        table.addCell(emp.getHireDate().toString());
        table.addCell("Salary");
        table.addCell("$" + String.format("%,.2f", emp.getSalary()));

        System.out.println(table.render());
    }

    private void updateEmployee() {
        Components.clearScreen();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                  Update Employee                 ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID to update: ");
        String id = scanner.nextLine();

        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            Components.pause();
            return;
        }

        System.out.println("\nCurrent details: ");
        displaySingleEmployee(employee);
        System.out.println("\nPress Enter to skip any field you don't want to update.");

        String name = null;
        boolean nameValid = true;
        do {
            System.out.print("[+] Enter Name [" + employee.getName() + "]: ");
            name = scanner.nextLine();

            if (!InputValidator.isEmpty(name)) {
                if (InputValidator.isValidName(name)) {
                    employee.setName(name);
                    nameValid = true;
                } else {
                    System.out.println("Invalid name. Must only contain letters, spaces, hyphens, or apostrophes.");
                    nameValid = false;
                }
            } else {
                nameValid = true;
            }
        } while (!nameValid);


        String position = null;
        boolean positionValid = true;
        do {
            System.out.print("[+] Enter Position [" + employee.getPosition() + "]: ");
            position = scanner.nextLine();

            if (!InputValidator.isEmpty(position)) {
                employee.setPosition(position);
                positionValid = true;
            } else {
                positionValid = true;
            }
        } while (!positionValid);


        String email = null;
        boolean emailValid = true;
        do {
            System.out.print("[+] Enter Email [" + employee.getEmail() + "]: ");
            email = scanner.nextLine();
            if (!InputValidator.isEmpty(email)) {
                if(InputValidator.isValidEmail(email)) {
                    employee.setEmail(email);
                    emailValid = true;
                } else {
                    System.out.println("Invalid email format. Please try again.");
                    emailValid = false;
                }
            } else {
                emailValid = true;
            }
        } while (!emailValid);


        String phone = null;
        boolean phoneValid = true;
        do {
            System.out.print("[+] Enter Phone [" + employee.getPhoneNumber() + "]: ");
            phone = scanner.nextLine();
            if (!InputValidator.isEmpty(phone)) {
                if(InputValidator.isValidPhoneNumber(phone)) {
                    employee.setPhoneNumber(phone);
                    phoneValid = true;
                } else {
                    System.out.println("Invalid phone number. Must be 9 to 10 digits.");
                    phoneValid = false;
                }
            } else {
                phoneValid = true;
            }
        } while (!phoneValid);

        System.out.print("[+] Enter Salary [" + String.format("%,.2f", employee.getSalary()) + "]: ");
        String salaryStr = scanner.nextLine();
        if (!InputValidator.isEmpty(salaryStr)) {
            try {
                double newSalary = Double.parseDouble(salaryStr);
                if (InputValidator.isValidSalary(newSalary)) {
                    employee.setSalary(newSalary);
                } else {
                    System.out.println("Salary must be greater than 0. Keeping old value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary format. Keeping old value.");
            }
        }

        if (employeeService.updateEmployee(employee)) {
            System.out.println("✓ Employee updated successfully!");

            System.out.print("Update another employee? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                updateEmployee();
            }
        } else {
            System.out.println("✗ Failed to update employee. Check service logs.");
        }
        Components.pause();
    }

    private void deleteEmployee() {
        Components.clearScreen();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                  Delete Employee                 ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter Employee ID to delete: ");
        String id = scanner.nextLine();

        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            Components.pause();
            return;
        }

        System.out.println("Employee: " + employee.getName() + " (ID: " + employee.getEmployeeId() + ")");
        System.out.print("Are you sure you want to delete this employee? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            if (employeeService.deleteEmployee(id)) {
                System.out.println("✓ Employee deleted successfully!");

                System.out.print("Delete another employee? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    deleteEmployee();
                }
            } else {
                System.out.println("✗ Failed to delete employee.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
        Components.pause();
    }

    public final static String SORT_OPTIONS = """
            ┌──────────────────────────────────────────────────┐
            │                  Sort Employee                   │
            ├──────────────────────────────────────────────────┤
            │  [s] -> By Salary                                │
            │  [a] -> By Age                                   │
            └──────────────────────────────────────────────────┘
            """;
    private void sortEmployees() {
        Components.clearScreen();
        System.out.print(SORT_OPTIONS);
        System.out.print("Choose option: ");
        String option = scanner.nextLine();

        System.out.print("-> Order [a]->(asc) | [d]->(desc): ");
        String order = scanner.nextLine();
        boolean ascending = order.equalsIgnoreCase("a");

        List<Employee> sorted = null;

        switch (option.toLowerCase()) {
            case "s":
                sorted = employeeService.sortEmployees("salary", ascending);
                break;
            case "a":
                sorted = employeeService.sortEmployees("age", ascending);
                break;
            default:
                System.out.println("Invalid option.");
                Components.pause();
                return;
        }

        if (sorted != null && !sorted.isEmpty()) {
            displayEmployeeList(sorted);
        } else {
            System.out.println("No employees to sort.");
        }
        Components.pause();
    }

    public final static String SEARCH_FILTER_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                 SEARCH & FILTER                  ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Search by Salary Range                      ║
            ║ [2]. Search by Position                          ║
            ║ [3]. List Employees with Low Salary              ║                       
            ║ [4]. List Employees by Department                ║                          
            ║ [0]. Back                                        ║                                 
            ╚══════════════════════════════════════════════════╝
            """;

    public void displaySearchAndFilter() {
        boolean running = true;

        while (running) {
            Components.clearScreen();
            System.out.print(SEARCH_FILTER_MENU);
            int choice = getIntInput("[+] Enter choice: ");

            switch (choice) {
                case 1:
                    searchBySalaryRange();
                    break;
                case 2:
                    System.out.print("[+] Enter position: ");
                    String position = scanner.nextLine();
                    List<Employee> byPosition = employeeService.searchEmployees("position", position);
                    if (!byPosition.isEmpty()) {
                        displayEmployeeList(byPosition);
                    } else {
                        System.out.println("No employees found in that position.");
                    }
                    Components.pause();
                    break;
                case 3:
                    listLowSalaryEmployees(50000.00); // Fixed threshold for 'Low Salary'
                    break;
                case 4:
                    System.out.print("[+] Enter department ID: ");
                    String dept = scanner.nextLine();
                    List<Employee> byDept = employeeService.searchEmployees("department", dept);
                    if (!byDept.isEmpty()) {
                        displayEmployeeList(byDept);
                    } else {
                        System.out.println("No employees found in that department.");
                    }
                    Components.pause();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    Components.pause();
            }
        }
    }

    private void searchBySalaryRange() {
        Components.clearScreen();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║              Search By Salary Range              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("[+] Enter minimum salary: ");
        double min = getDoubleInput();
        System.out.print("[+] Enter maximum salary: ");
        double max = getDoubleInput();

        List<Employee> results = DataStore.getInstance().getEmployees().stream()
                .filter(e -> e.getSalary() >= min && e.getSalary() <= max)
                .toList();

        if (results.isEmpty()) {
            System.out.println("No employees found in that salary range.");
        } else {
            displayEmployeeList(results);
        }
        Components.pause();
    }

    private void listLowSalaryEmployees(double threshold) {
        Components.clearScreen();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        Employees with Salary Below $" + String.format("%,.2f", threshold) + "      ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        List<Employee> results = DataStore.getInstance().getEmployees().stream()
                .filter(e -> e.getSalary() < threshold)
                .toList();

        if (results.isEmpty()) {
            System.out.println("No employees found with a salary below $" + String.format("%,.2f", threshold) + ".");
        } else {
            displayEmployeeList(results);
        }
        Components.pause();
    }

    public final static String REPORT_MENU = """
            ╔══════════════════════════════════════════════════╗
            ║                     REPORTS                      ║
            ╠══════════════════════════════════════════════════╣ 
            ║ [1]. Employee Report                             ║
            ║ [2]. Department Report                           ║
            ║ [3]. Attendance Report                           ║                       
            ║ [4]. Salary Report                               ║  
            ║ [5]. Export All Reports to report.txt            ║                        
            ║ [0]. Back                                        ║                                 
            ╚══════════════════════════════════════════════════╝
            """;

    public void displayReports() {
        boolean running = true;

        while (running) {
            Components.clearScreen();
            System.out.print(REPORT_MENU);
            int choice = getIntInput("[+] Enter choice: ");

            switch (choice) {
                case 1:
                    String empReport = ReportGenerator.generateEmployeeReport(
                            DataStore.getInstance().getEmployees());
                    System.out.println(empReport);
                    Components.pause();
                    break;
                case 2:
                    String deptReport = ReportGenerator.generateDepartmentReport(
                            DataStore.getInstance().getDepartments(),
                            DataStore.getInstance().getEmployees());
                    System.out.println(deptReport);
                    Components.pause();
                    break;
                case 3:
                    String attReport = ReportGenerator.generateAttendanceReport(
                            DataStore.getInstance().getAttendances());
                    System.out.println(attReport);
                    Components.pause();
                    break;
                case 4:
                    String salReport = ReportGenerator.generateSalaryReport(
                            DataStore.getInstance().getSalaryRecords());
                    System.out.println(salReport);
                    Components.pause();
                    break;
                case 5:
                    exportAllReports();
                    Components.pause();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    Components.pause();
            }
        }
    }

    private void exportAllReports() {
        String empReport = ReportGenerator.generateEmployeeReport(
                DataStore.getInstance().getEmployees());
        String deptReport = ReportGenerator.generateDepartmentReport(
                DataStore.getInstance().getDepartments(),
                DataStore.getInstance().getEmployees());
        String attReport = ReportGenerator.generateAttendanceReport(
                DataStore.getInstance().getAttendances());
        String salReport = ReportGenerator.generateSalaryReport(
                DataStore.getInstance().getSalaryRecords());

        if (ReportGenerator.exportAllReports(empReport, deptReport, attReport, salReport)) {
            System.out.println("✓ All reports exported to report.txt successfully!");
        } else {
            System.out.println("✗ Failed to export reports.");
        }
    }

    private String getDepartmentName(String deptId) {
        if (deptId == null) return "N/A";
        return departmentService.getAllDepartments().stream()
                .filter(d -> d.getDepartmentId().equals(deptId))
                .findFirst()
                .map(Department::getDepartmentName)
                .orElse("N/A");
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