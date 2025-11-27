package com.ems.dao.interfaces;

import com.ems.model.SalaryRecord;
import java.util.List;

public interface IPayrollDAO {
    boolean addSalaryRecord(SalaryRecord record);
    List<SalaryRecord> getAllSalaryRecords();
    List<SalaryRecord> getSalaryByEmployeeId(String employeeId);
    SalaryRecord getLatestSalary(String employeeId);
    boolean updateSalaryRecord(SalaryRecord record);
}
