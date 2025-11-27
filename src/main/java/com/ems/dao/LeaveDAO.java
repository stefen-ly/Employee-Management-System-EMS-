package com.ems.dao;

import com.ems.dao.interfaces.ILeaveDAO;
import com.ems.data.DataStore;
import com.ems.model.LeaveRequest;
import java.util.List;
import java.util.stream.Collectors;

public class LeaveDAO implements ILeaveDAO {
    private DataStore dataStore;

    public LeaveDAO() {
        this.dataStore = DataStore.getInstance();
    }

    @Override
    public boolean addLeaveRequest(LeaveRequest request) {
        boolean result = dataStore.getLeaveRequests().add(request);
        if (result) {
            dataStore.saveAllData();
        }
        return result;
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return dataStore.getLeaveRequests();
    }

    @Override
    public List<LeaveRequest> getLeaveByEmployeeId(String employeeId) {
        return dataStore.getLeaveRequests().stream()
                .filter(leave -> leave.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

    @Override
    public LeaveRequest getLeaveById(String leaveId) {
        return dataStore.getLeaveRequests().stream()
                .filter(leave -> leave.getLeaveId().equals(leaveId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateLeaveRequest(LeaveRequest request) {
        LeaveRequest existing = getLeaveById(request.getLeaveId());
        if (existing != null) {
            dataStore.getLeaveRequests().remove(existing);
            dataStore.getLeaveRequests().add(request);
            dataStore.saveAllData();
            return true;
        }
        return false;
    }
}
