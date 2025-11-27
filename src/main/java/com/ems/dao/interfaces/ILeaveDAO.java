package com.ems.dao.interfaces;

import com.ems.model.LeaveRequest;
import java.util.List;

public interface ILeaveDAO {
    boolean addLeaveRequest(LeaveRequest request);
    List<LeaveRequest> getAllLeaveRequests();
    List<LeaveRequest> getLeaveByEmployeeId(String employeeId);
    LeaveRequest getLeaveById(String leaveId);
    boolean updateLeaveRequest(LeaveRequest request);
}
