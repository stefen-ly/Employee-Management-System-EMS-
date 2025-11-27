package com.ems.service;

import com.ems.dao.EmployeeDAO;
import com.ems.dao.PayrollDAO;
import com.ems.model.Employee;
import com.ems.model.SalaryRecord;
import com.ems.service.interfaces.IPayrollService;
import com.ems.util.BorderStyle;
import com.ems.util.IDGenerator;
import com.ems.util.Table;

import java.time.LocalDate;

public class PayrollService implements IPayrollService {
    private PayrollDAO payrollDAO;
    private EmployeeDAO employeeDAO;

    public PayrollService() {
        this.payrollDAO = new PayrollDAO();
        this.employeeDAO = new EmployeeDAO();
    }

    @Override
    public SalaryRecord createSalaryRecord(String employeeId) {
        Employee employee = employeeDAO.getEmployeeById(employeeId);
        if (employee == null) {
            return null;
        }
        
        SalaryRecord record = new SalaryRecord();
        record.setSalaryId(IDGenerator.generateSalaryId());
        record.setEmployeeId(employeeId);
        record.setBasicSalary(employee.getSalary());
        record.setPaymentDate(LocalDate.now());
        record.calculateSalary();
        
        payrollDAO.addSalaryRecord(record);
        return record;
    }

    @Override
    public boolean addBonus(String employeeId, double bonus) {
        SalaryRecord record = payrollDAO.getLatestSalary(employeeId);
        if (record == null) {
            record = createSalaryRecord(employeeId);
        }
        
        if (record != null) {
            record.setBonus(record.getBonus() + bonus);
            return payrollDAO.updateSalaryRecord(record);
        }
        return false;
    }

    @Override
    public boolean addDeduction(String employeeId, double deduction) {
        SalaryRecord record = payrollDAO.getLatestSalary(employeeId);
        if (record == null) {
            record = createSalaryRecord(employeeId);
        }
        
        if (record != null) {
            record.setDeduction(record.getDeduction() + deduction);
            return payrollDAO.updateSalaryRecord(record);
        }
        return false;
    }

    @Override
    public String generateSalarySlip(String employeeId) {
        Employee employee = employeeDAO.getEmployeeById(employeeId);
        SalaryRecord salary = payrollDAO.getLatestSalary(employeeId);

        if (employee == null || salary == null) {
            return "No salary record found.";
        }

        Table table = new Table(2, BorderStyle.UNICODE_ROUND_BOX_WIDE);
        table.addCell("Field");
        table.addCell("Amount");

        table.addCell("Employee ID");
        table.addCell(employee.getEmployeeId());

        table.addCell("Name");
        table.addCell(employee.getName());

        table.addCell("Position");
        table.addCell(employee.getPosition());

        table.addCell("Payment Date");
        table.addCell(salary.getPaymentDate().toString());

        table.addCell("Basic Salary");
        table.addCell(String.format("$%.2f", salary.getBasicSalary()));

        table.addCell("Bonus");
        table.addCell(String.format("$%.2f", salary.getBonus()));

        table.addCell("Gross Salary");
        table.addCell(String.format("$%.2f", salary.getGrossSalary()));

        table.addCell("Deductions");
        table.addCell(String.format("$%.2f", salary.getDeduction()));

        table.addCell("Tax");
        table.addCell(String.format("$%.2f", salary.getTax()));

        table.addCell("Net Salary");
        table.addCell(String.format("$%.2f", salary.getNetSalary()));

        return table.render();
    }


    public SalaryRecord getLatestSalary(String employeeId) {
        return payrollDAO.getLatestSalary(employeeId);
    }
}
