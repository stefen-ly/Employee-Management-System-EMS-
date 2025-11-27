package com.ems.dao;

import com.ems.dao.interfaces.IDepartmentDAO;
import com.ems.data.DataStore;
import com.ems.model.Department;
import java.util.List;

public class DepartmentDAO implements IDepartmentDAO {
    private DataStore dataStore;

    public DepartmentDAO() {
        this.dataStore = DataStore.getInstance();
    }

    @Override
    public boolean addDepartment(Department department) {
        boolean result = dataStore.getDepartments().add(department);
        if (result) {
            dataStore.saveAllData();
        }
        return result;
    }

    @Override
    public Department getDepartmentById(String id) {
        return dataStore.getDepartments().stream()
                .filter(dept -> dept.getDepartmentId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Department> getAllDepartments() {
        return dataStore.getDepartments();
    }

    @Override
    public boolean updateDepartment(Department department) {
        Department existing = getDepartmentById(department.getDepartmentId());
        if (existing != null) {
            dataStore.getDepartments().remove(existing);
            dataStore.getDepartments().add(department);
            dataStore.saveAllData();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteDepartment(String id) {
        Department department = getDepartmentById(id);
        if (department != null) {
            boolean result = dataStore.getDepartments().remove(department);
            if (result) {
                dataStore.saveAllData();
            }
            return result;
        }
        return false;
    }
}
