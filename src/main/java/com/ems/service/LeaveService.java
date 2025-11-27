package com.ems.service;

import com.ems.dao.LeaveDAO;
import com.ems.model.LeaveRequest;
import com.ems.util.IDGenerator;
import java.util.List;
import java.util.stream.Collectors;

public class LeaveService {
    private LeaveDAO leaveDAO;

    public LeaveService() {
        this.leaveDAO = new LeaveDAO();
    }

    public boolean requestLeave(LeaveRequest request) {
        if (request.getLeaveId() == null || request.getLeaveId().isEmpty()) {
            request.setLeaveId(IDGenerator.generateLeaveId());
        }
        request.setStatus("PENDING");
        return leaveDAO.addLeaveRequest(request);
    }

    public boolean approveLeave(String leaveId) {
        LeaveRequest leave = leaveDAO.getLeaveById(leaveId);
        if (leave != null && "PENDING".equals(leave.getStatus())) {
            leave.setStatus("APPROVED");
            return leaveDAO.updateLeaveRequest(leave);
        }
        return false;
    }

    public boolean rejectLeave(String leaveId) {
        LeaveRequest leave = leaveDAO.getLeaveById(leaveId);
        if (leave != null && "PENDING".equals(leave.getStatus())) {
            leave.setStatus("REJECTED");
            return leaveDAO.updateLeaveRequest(leave);
        }
        return false;
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveDAO.getAllLeaveRequests();
    }

    public List<LeaveRequest> getPendingLeaves() {
        return leaveDAO.getAllLeaveRequests().stream()
                .filter(leave -> "PENDING".equals(leave.getStatus()))
                .collect(Collectors.toList());
    }

    public List<LeaveRequest> getEmployeeLeaves(String employeeId) {
        return leaveDAO.getLeaveByEmployeeId(employeeId);
    }
}
