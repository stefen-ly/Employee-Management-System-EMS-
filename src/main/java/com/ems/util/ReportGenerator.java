package com.ems.util;

import com.ems.model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ReportGenerator {
    Scanner scanner = new Scanner(System.in);

    public static String generateEmployeeReport(List<Employee> employees) {
        Table table = new Table(4, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        table.addCell("Employee ID");
        table.addCell("Name");
        table.addCell("Position");
        table.addCell("Department");

        for (Employee emp : employees) {
            table.addCell(emp.getEmployeeId());
            table.addCell(emp.getName());
            table.addCell(emp.getPosition());
            table.addCell(emp.getDepartmentId());
        }

        return table.render();
    }

    public static String generateDepartmentReport(List<Department> departments,
                                                 List<Employee> employees) {
        Table table = new Table(3, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        table.addCell("Department ID");
        table.addCell("Department Name");
        table.addCell("Employee Count");

        for (Department dept : departments) {
            long empCount = employees.stream()
                    .filter(e -> dept.getDepartmentId().equals(e.getDepartmentId()))
                    .count();
            table.addCell(dept.getDepartmentId());
            table.addCell(dept.getDepartmentName());
            table.addCell(String.valueOf(empCount));
        }

        return table.render();
    }

    public static String generateAttendanceReport(List<Attendance> attendances) {
        Table table = new Table(3, BorderStyle.UNICODE_ROUND_BOX_WIDE);

        table.addCell("Employee ID");
        table.addCell("Date");
        table.addCell("Status");

        for (Attendance att : attendances) {
            table.addCell(att.getEmployeeId());
            table.addCell(att.getDate().toString());
            table.addCell(att.getStatus());
        }

        return table.render();
    }

        public static String generateSalaryReport(List<SalaryRecord> salaries) {
            StringBuilder report = new StringBuilder();
            report.append("\n========== SALARY REPORT ==========\n");
            report.append("Generated: ").append(LocalDateTime.now()).append("\n");
            report.append("Total Records: ").append(salaries.size()).append("\n");
            report.append("-----------------------------------\n");

            double totalGross = 0;
            double totalNet = 0;

            Table table = new Table(3, BorderStyle.UNICODE_ROUND_BOX_WIDE);

            table.addCell("Employee ID");
            table.addCell("Gross Salary");
            table.addCell("Net Salary");

            for (SalaryRecord sal : salaries) {
                table.addCell(sal.getEmployeeId());
                table.addCell(String.format("$%.2f", sal.getGrossSalary()));
                table.addCell(String.format("$%.2f", sal.getNetSalary()));

                totalGross += sal.getGrossSalary();
                totalNet += sal.getNetSalary();
            }

            report.append(table.render()).append("\n");

            report.append("-----------------------------------\n");
            report.append(String.format("Total Gross: $%.2f\n", totalGross));
            report.append(String.format("Total Net: $%.2f\n", totalNet));
            report.append("===================================\n");

            return report.toString();
        }


    public static boolean exportAllReports(String employeeReport, String deptReport,
                                          String attendanceReport, String salaryReport) {
        try (FileWriter writer = new FileWriter("report.txt")) {
            writer.write("========== COMPREHENSIVE SYSTEM REPORT ==========\n");
            writer.write("Generated: " + LocalDateTime.now() + "\n\n");
            writer.write(employeeReport + "\n\n");
            writer.write(deptReport + "\n\n");
            writer.write(attendanceReport + "\n\n");
            writer.write(salaryReport + "\n");
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting reports: " + e.getMessage());
            return false;
        }
    }
}
