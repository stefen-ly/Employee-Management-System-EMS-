package com.ems.service.interfaces;

import com.ems.model.Department;
import java.util.List;
import java.util.Map;

public interface IDepartmentService {
    boolean addDepartment(Department department);
    Department getDepartmentById(String id);
    List<Department> getAllDepartments();
    boolean updateDepartment(Department department);
    boolean deleteDepartment(String id);
    Map<String, Integer> countEmployeesByDepartment();
}
