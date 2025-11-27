package com.ems.dao;

import com.ems.dao.interfaces.IEmployeeDAO;
import com.ems.data.DataStore;
import com.ems.model.Employee;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDAO implements IEmployeeDAO {
    private DataStore dataStore;

    public EmployeeDAO() {
        this.dataStore = DataStore.getInstance();
    }

    @Override
    public boolean addEmployee(Employee employee) {
        boolean result = dataStore.getEmployees().add(employee);
        if (result) {
            dataStore.saveAllData();
        }
        return result;
    }

    @Override
    public Employee getEmployeeById(String id) {
        return dataStore.getEmployees().stream()
                .filter(emp -> emp.getEmployeeId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return dataStore.getEmployees();
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        Employee existing = getEmployeeById(employee.getEmployeeId());
        if (existing != null) {
            dataStore.getEmployees().remove(existing);
            dataStore.getEmployees().add(employee);
            dataStore.saveAllData();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEmployee(String id) {
        Employee employee = getEmployeeById(id);
        if (employee != null) {
            boolean result = dataStore.getEmployees().remove(employee);
            if (result) {
                dataStore.saveAllData();
            }
            return result;
        }
        return false;
    }

    @Override
    public List<Employee> searchByName(String name) {
        return dataStore.getEmployees().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> searchByDepartment(String departmentId) {
        return dataStore.getEmployees().stream()
                .filter(emp -> emp.getDepartmentId() != null && 
                             emp.getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> searchByPosition(String position) {
        return dataStore.getEmployees().stream()
                .filter(emp -> emp.getPosition().toLowerCase().contains(position.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> searchBySalaryRange(double min, double max) {
        return dataStore.getEmployees().stream()
                .filter(emp -> emp.getSalary() >= min && emp.getSalary() <= max)
                .collect(Collectors.toList());
    }
}
