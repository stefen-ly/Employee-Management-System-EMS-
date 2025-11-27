package com.ems.service;

import com.ems.dao.EmployeeDAO;
import com.ems.model.Employee;
import com.ems.service.interfaces.IEmployeeService;
import com.ems.util.IDGenerator;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService implements IEmployeeService {
    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    @Override
    public boolean addEmployee(Employee employee) {
        if (employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()) {
            employee.setEmployeeId(IDGenerator.generateEmployeeId());
        }
        return employeeDAO.addEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeDAO.getEmployeeById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return employeeDAO.updateEmployee(employee);
    }

    @Override
    public boolean deleteEmployee(String id) {
        return employeeDAO.deleteEmployee(id);
    }

    @Override
    public List<Employee> searchEmployees(String criteria, String value) {
        switch (criteria.toLowerCase()) {
            case "name":
                return employeeDAO.searchByName(value);
            case "department":
                return employeeDAO.searchByDepartment(value);
            case "position":
                return employeeDAO.searchByPosition(value);
            default:
                return getAllEmployees();
        }
    }

    @Override
    public List<Employee> sortEmployees(String criteria, boolean ascending) {
        List<Employee> employees = getAllEmployees();
        
        Comparator<Employee> comparator;
        switch (criteria.toLowerCase()) {
            case "salary":
                comparator = Comparator.comparing(Employee::getSalary);
                break;
            case "age":
                comparator = Comparator.comparing(Employee::getDateOfBirth).reversed();
                break;
            case "name":
                comparator = Comparator.comparing(Employee::getName);
                break;
            default:
                return employees;
        }
        
        if (!ascending) {
            comparator = comparator.reversed();
        }
        
        return employees.stream().sorted(comparator).collect(Collectors.toList());
    }
}
