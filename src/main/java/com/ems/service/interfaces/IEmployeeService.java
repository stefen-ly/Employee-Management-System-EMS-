package com.ems.service.interfaces;

import com.ems.model.Employee;
import java.util.List;

public interface IEmployeeService {
    boolean addEmployee(Employee employee);
    Employee getEmployeeById(String id);
    List<Employee> getAllEmployees();
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(String id);
    List<Employee> searchEmployees(String criteria, String value);
    List<Employee> sortEmployees(String criteria, boolean ascending);
}
