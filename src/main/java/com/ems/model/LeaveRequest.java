package com.ems.model;

import java.time.LocalDate;

public class LeaveRequest {
    private String leaveId;
    private String employeeId;
    private String leaveType; // SICK, ANNUAL, EMERGENCY, etc.
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status; // PENDING, APPROVED, REJECTED
    private LocalDate requestDate;

    public LeaveRequest() {
        this.status = "PENDING";
        this.requestDate = LocalDate.now();
    }

    public LeaveRequest(String leaveId, String employeeId, String leaveType,
                       LocalDate startDate, LocalDate endDate, String reason) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = "PENDING";
        this.requestDate = LocalDate.now();
    }

    // Getters and Setters
    public String getLeaveId() { return leaveId; }
    public void setLeaveId(String leaveId) { this.leaveId = leaveId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }

    @Override
    public String toString() {
        return String.format("ID: %s | Employee: %s | Type: %s | From: %s To: %s | Status: %s | Reason: %s",
                leaveId, employeeId, leaveType, startDate, endDate, status, reason);
    }
}
