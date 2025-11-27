package com.ems.model;

import java.time.LocalDate;

public class SalaryRecord {
    private String salaryId;
    private String employeeId;
    private double basicSalary;
    private double bonus;
    private double deduction;
    private double tax;
    private double grossSalary;
    private double netSalary;
    private LocalDate paymentDate;
    private String month;
    private int year;

    public SalaryRecord() {
        this.paymentDate = LocalDate.now();
    }

    public SalaryRecord(String salaryId, String employeeId, double basicSalary) {
        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.basicSalary = basicSalary;
        this.bonus = 0;
        this.deduction = 0;
        this.tax = 0;
        this.paymentDate = LocalDate.now();
        calculateSalary();
    }

    public void calculateSalary() {
        this.grossSalary = basicSalary + bonus;
        this.tax = calculateTax(grossSalary);
        this.netSalary = grossSalary - deduction - tax;
    }

    private double calculateTax(double gross) {
        if (gross <= 5000) return gross * 0.05;
        else if (gross <= 10000) return gross * 0.10;
        else if (gross <= 20000) return gross * 0.15;
        else return gross * 0.20;
    }

    // Getters and Setters
    public String getSalaryId() { return salaryId; }
    public void setSalaryId(String salaryId) { this.salaryId = salaryId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { 
        this.basicSalary = basicSalary;
        calculateSalary();
    }

    public double getBonus() { return bonus; }
    public void setBonus(double bonus) { 
        this.bonus = bonus;
        calculateSalary();
    }

    public double getDeduction() { return deduction; }
    public void setDeduction(double deduction) { 
        this.deduction = deduction;
        calculateSalary();
    }

    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }

    public double getGrossSalary() { return grossSalary; }
    public void setGrossSalary(double grossSalary) { this.grossSalary = grossSalary; }

    public double getNetSalary() { return netSalary; }
    public void setNetSalary(double netSalary) { this.netSalary = netSalary; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return String.format("Salary ID: %s | Employee: %s | Basic: $%.2f | Bonus: $%.2f | Deduction: $%.2f | Tax: $%.2f | Gross: $%.2f | Net: $%.2f",
                salaryId, employeeId, basicSalary, bonus, deduction, tax, grossSalary, netSalary);
    }
}
