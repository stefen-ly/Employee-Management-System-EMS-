package com.ems.dao.interfaces;

import com.ems.model.Department;
import java.util.List;

public interface IDepartmentDAO {
    boolean addDepartment(Department department);
    Department getDepartmentById(String id);
    List<Department> getAllDepartments();
    boolean updateDepartment(Department department);
    boolean deleteDepartment(String id);
}
