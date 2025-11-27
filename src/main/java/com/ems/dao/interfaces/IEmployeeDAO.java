package com.ems.dao.interfaces;

import com.ems.model.Employee;
import java.util.List;

public interface IEmployeeDAO {
    boolean addEmployee(Employee employee);
    Employee getEmployeeById(String id);
    List<Employee> getAllEmployees();
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(String id);
    List<Employee> searchByName(String name);
    List<Employee> searchByDepartment(String departmentId);
    List<Employee> searchByPosition(String position);
    List<Employee> searchBySalaryRange(double min, double max);
}
