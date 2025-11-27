package com.ems.service.interfaces;

import com.ems.model.SalaryRecord;

public interface IPayrollService {
    SalaryRecord createSalaryRecord(String employeeId);
    boolean addBonus(String employeeId, double bonus);
    boolean addDeduction(String employeeId, double deduction);
    String generateSalarySlip(String employeeId);
}
