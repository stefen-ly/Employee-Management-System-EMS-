package com.ems.util;

import com.ems.model.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVManager {
    private static final String DATA_DIR = "data";
    private static final String EMPLOYEES_FILE = DATA_DIR + "/employees.csv";
    private static final String DEPARTMENTS_FILE = DATA_DIR + "/departments.csv";
    private static final String USERS_FILE = DATA_DIR + "/users.csv";
    private static final String ATTENDANCE_FILE = DATA_DIR + "/attendance.csv";
    private static final String LEAVE_REQUESTS_FILE = DATA_DIR + "/leave_requests.csv";
    private static final String SALARY_RECORDS_FILE = DATA_DIR + "/salary_records.csv";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public CSVManager() {
        createDataDirectory();
    }

    private void createDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Save Employees to CSV
    public void saveEmployees(List<Employee> employees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EMPLOYEES_FILE))) {
            // Header
            writer.write("employeeId,name,dateOfBirth,gender,email,phone,address,position,departmentId,salary\n");
            
            // Data
            for (Employee emp : employees) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f\n",
                    emp.getEmployeeId(),
                    escapeCSV(emp.getName()),
                    emp.getDateOfBirth().format(DATE_FORMATTER),
                    emp.getGender(),
                    emp.getEmail(),
                    emp.getPhoneNumber(),
                    escapeCSV(emp.getAddress()),
                    escapeCSV(emp.getPosition()),
                    emp.getDepartmentId(),
                    emp.getSalary()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving employees: " + e.getMessage());
        }
    }

    // Load Employees from CSV
    public List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        File file = new File(EMPLOYEES_FILE);
        if (!file.exists()) return employees;

        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEES_FILE))) {
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 10) {
                    Employee emp = new Employee(
                        parts[0], // employeeId
                        parts[1], // name
                        LocalDate.parse(parts[2], DATE_FORMATTER), // dateOfBirth
                        parts[3], // gender
                        parts[4], // email
                        parts[5], // phone
                        parts[6], // address
                        parts[7], // position
                        parts[8], // departmentId
                        Double.parseDouble(parts[9]) // salary
                    );
                    employees.add(emp);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading employees: " + e.getMessage());
        }
        return employees;
    }

    // Save Departments to CSV
    public void saveDepartments(List<Department> departments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DEPARTMENTS_FILE))) {
            writer.write("departmentId,name,description,managerId\n");
            
            for (Department dept : departments) {
                writer.write(String.format("%s,%s,%s,%s\n",
                    dept.getDepartmentId(),
                    escapeCSV(dept.getDepartmentName()),
                    escapeCSV(dept.getDescription()),
                    dept.getManagerId() != null ? dept.getManagerId() : ""
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving departments: " + e.getMessage());
        }
    }

    // Load Departments from CSV
    public List<Department> loadDepartments() {
        List<Department> departments = new ArrayList<>();
        File file = new File(DEPARTMENTS_FILE);
        if (!file.exists()) return departments;

        try (BufferedReader reader = new BufferedReader(new FileReader(DEPARTMENTS_FILE))) {
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 3) {
                    Department dept = new Department(
                        parts[0], // departmentId
                        parts[1], // name
                        parts[2], // description
                        parts.length > 3 && !parts[3].isEmpty() ? parts[3] : null // managerId
                    );
                    departments.add(dept);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading departments: " + e.getMessage());
        }
        return departments;
    }

    // Save Users to CSV
    public void saveUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            writer.write("username,password,role,employeeId\n");
            
            for (User user : users) {
                writer.write(String.format("%s,%s,%s,%s\n",
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole(),
                    user.getEmployeeId() != null ? user.getEmployeeId() : ""
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // Load Users from CSV
    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 3) {
                    User user = new User(
                        parts[0], // username
                        parts[1], // password
                        parts[2], // role
                        parts.length > 3 && !parts[3].isEmpty() ? parts[3] : null
                    );
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    // Save Attendance to CSV
    public void saveAttendance(List<Attendance> attendances) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ATTENDANCE_FILE))) {
            writer.write("attendanceId,employeeId,date,checkIn,checkOut,status\n");
            
            for (Attendance att : attendances) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                    att.getAttendanceId(),
                    att.getEmployeeId(),
                    att.getDate().format(DATE_FORMATTER),
                    att.getCheckIn() != null ? att.getCheckIn().format(TIME_FORMATTER) : "",
                    att.getCheckOut() != null ? att.getCheckOut().format(TIME_FORMATTER) : "",
                    att.getStatus()
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving attendance: " + e.getMessage());
        }
    }

    // Load Attendance from CSV
    public List<Attendance> loadAttendance() {
        List<Attendance> attendances = new ArrayList<>();
        File file = new File(ATTENDANCE_FILE);
        if (!file.exists()) return attendances;

        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 6) {
                    Attendance att = new Attendance(
                        parts[0], // attendanceId
                        parts[1], // employeeId
                        LocalDate.parse(parts[2], DATE_FORMATTER), // date
                        !parts[3].isEmpty() ? LocalTime.parse(parts[3], TIME_FORMATTER) : null, // checkIn
                        !parts[4].isEmpty() ? LocalTime.parse(parts[4], TIME_FORMATTER) : null, // checkOut
                        parts[5] // status
                    );
                    attendances.add(att);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading attendance: " + e.getMessage());
        }
        return attendances;
    }

    // Save Leave Requests to CSV
    public void saveLeaveRequests(List<LeaveRequest> leaveRequests) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEAVE_REQUESTS_FILE))) {
            writer.write("leaveId,employeeId,leaveType,startDate,endDate,reason,status,requestDate\n");
            
            for (LeaveRequest leave : leaveRequests) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s\n",
                    leave.getLeaveId(),
                    leave.getEmployeeId(),
                    leave.getLeaveType(),
                    leave.getStartDate().format(DATE_FORMATTER),
                    leave.getEndDate().format(DATE_FORMATTER),
                    escapeCSV(leave.getReason()),
                    leave.getStatus(),
                    leave.getRequestDate().format(DATE_FORMATTER)
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving leave requests: " + e.getMessage());
        }
    }

    // Load Leave Requests from CSV
    public List<LeaveRequest> loadLeaveRequests() {
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        File file = new File(LEAVE_REQUESTS_FILE);
        if (!file.exists()) return leaveRequests;

        try (BufferedReader reader = new BufferedReader(new FileReader(LEAVE_REQUESTS_FILE))) {
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 8) {
                    LeaveRequest leave = new LeaveRequest(
                        parts[0], // leaveId
                        parts[1], // employeeId
                        parts[2], // leaveType
                        LocalDate.parse(parts[3], DATE_FORMATTER), // startDate
                        LocalDate.parse(parts[4], DATE_FORMATTER), // endDate
                        parts[5] // reason
                    );
                    leave.setStatus(parts[6]); // status
                    leave.setRequestDate(LocalDate.parse(parts[7], DATE_FORMATTER));
                    leaveRequests.add(leave);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading leave requests: " + e.getMessage());
        }
        return leaveRequests;
    }

    // Save Salary Records to CSV
    public void saveSalaryRecords(List<SalaryRecord> salaryRecords) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SALARY_RECORDS_FILE))) {
            writer.write("salaryId,employeeId,basicSalary,bonus,deduction,netSalary,taxAmount,month,year,paymentDate\n");
            
            for (SalaryRecord sal : salaryRecords) {
                writer.write(String.format("%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%s,%d,%s\n",
                    sal.getSalaryId(),
                    sal.getEmployeeId(),
                    sal.getBasicSalary(),
                    sal.getBonus(),
                    sal.getDeduction(),
                    sal.getNetSalary(),
                    sal.getTax(),
                    sal.getMonth(),
                    sal.getYear(),
                    sal.getPaymentDate() != null ? sal.getPaymentDate().format(DATE_FORMATTER) : ""
                ));
            }
        } catch (IOException e) {
            System.err.println("Error saving salary records: " + e.getMessage());
        }
    }

    // Load Salary Records from CSV
    public List<SalaryRecord> loadSalaryRecords() {
        List<SalaryRecord> salaryRecords = new ArrayList<>();
        File file = new File(SALARY_RECORDS_FILE);
        if (!file.exists()) return salaryRecords;

        try (BufferedReader reader = new BufferedReader(new FileReader(SALARY_RECORDS_FILE))) {
            String line = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 10) {
                    SalaryRecord sal = new SalaryRecord(
                        parts[0], // salaryId
                        parts[1], // employeeId
                        Double.parseDouble(parts[2]) // basicSalary
                    );
                    sal.setBonus(Double.parseDouble(parts[3]));
                    sal.setDeduction(Double.parseDouble(parts[4]));
                    sal.setMonth(parts[7]);
                    sal.setYear(Integer.parseInt(parts[8]));
                    if (!parts[9].isEmpty()) {
                        sal.setPaymentDate(LocalDate.parse(parts[9], DATE_FORMATTER));
                    }
                    salaryRecords.add(sal);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading salary records: " + e.getMessage());
        }
        return salaryRecords;
    }

    // Utility method to escape CSV special characters
    private String escapeCSV(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // Utility method to parse CSV line properly handling quoted values
    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        
        return result.toArray(new String[0]);
    }

    // Save all data
    public void saveAllData(List<Employee> employees, List<Department> departments,
                           List<User> users, List<Attendance> attendances,
                           List<LeaveRequest> leaveRequests, List<SalaryRecord> salaryRecords) {
        saveEmployees(employees);
        saveDepartments(departments);
        saveUsers(users);
        saveAttendance(attendances);
        saveLeaveRequests(leaveRequests);
        saveSalaryRecords(salaryRecords);
        System.out.println("All data saved to CSV files successfully!");
    }
}
