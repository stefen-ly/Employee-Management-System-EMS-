package com.ems.service;

import com.ems.dao.DepartmentDAO;
import com.ems.dao.EmployeeDAO;
import com.ems.data.DataStore;
import com.ems.model.Department;
import com.ems.service.interfaces.IDepartmentService;
import com.ems.util.IDGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentService implements IDepartmentService {
    private DepartmentDAO departmentDAO;
    private EmployeeDAO employeeDAO;

    public DepartmentService() {
        this.departmentDAO = new DepartmentDAO();
        this.employeeDAO = new EmployeeDAO();
    }
    @Override
    public Department getDepartmentById(String id) {
        // Implementation: Find department by ID. (Resolves the previous compilation error)
        if (id == null) return null;
        return DataStore.getInstance().getDepartments().stream()
                .filter(d -> d.getDepartmentId().equalsIgnoreCase(id.trim()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean addDepartment(Department department) {
        if (department.getDepartmentId() == null || department.getDepartmentId().isEmpty()) {
            department.setDepartmentId(IDGenerator.generateDepartmentId());
        }
        return departmentDAO.addDepartment(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentDAO.getAllDepartments();
    }

    @Override
    public boolean updateDepartment(Department department) {
        return departmentDAO.updateDepartment(department);
    }

    @Override
    public boolean deleteDepartment(String id) {
        return departmentDAO.deleteDepartment(id);
    }

    @Override
    public Map<String, Integer> countEmployeesByDepartment() {
        Map<String, Integer> counts = new HashMap<>();
        List<Department> departments = getAllDepartments();
        
        for (Department dept : departments) {
            int count = employeeDAO.searchByDepartment(dept.getDepartmentId()).size();
            counts.put(dept.getDepartmentName(), count);
        }
        
        return counts;
    }
}
