package com.ems.dao;

import com.ems.dao.interfaces.IPayrollDAO;
import com.ems.data.DataStore;
import com.ems.model.SalaryRecord;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PayrollDAO implements IPayrollDAO {
    private DataStore dataStore;

    public PayrollDAO() {
        this.dataStore = DataStore.getInstance();
    }

    @Override
    public boolean addSalaryRecord(SalaryRecord record) {
        boolean result = dataStore.getSalaryRecords().add(record);
        if (result) {
            dataStore.saveAllData();
        }
        return result;
    }

    @Override
    public List<SalaryRecord> getAllSalaryRecords() {
        return dataStore.getSalaryRecords();
    }

    @Override
    public List<SalaryRecord> getSalaryByEmployeeId(String employeeId) {
        return dataStore.getSalaryRecords().stream()
                .filter(record -> record.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

    @Override
    public SalaryRecord getLatestSalary(String employeeId) {
        return dataStore.getSalaryRecords().stream()
                .filter(record -> record.getEmployeeId().equals(employeeId))
                .max(Comparator.comparing(SalaryRecord::getPaymentDate))
                .orElse(null);
    }

    @Override
    public boolean updateSalaryRecord(SalaryRecord record) {
        SalaryRecord existing = dataStore.getSalaryRecords().stream()
                .filter(sal -> sal.getSalaryId().equals(record.getSalaryId()))
                .findFirst()
                .orElse(null);
        
        if (existing != null) {
            dataStore.getSalaryRecords().remove(existing);
            dataStore.getSalaryRecords().add(record);
            dataStore.saveAllData();
            return true;
        }
        return false;
    }
}
