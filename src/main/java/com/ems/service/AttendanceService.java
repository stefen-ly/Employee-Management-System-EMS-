package com.ems.service;

import com.ems.dao.AttendanceDAO;
import com.ems.model.Attendance;
import com.ems.util.IDGenerator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceService {
    private AttendanceDAO attendanceDAO;

    public AttendanceService() {
        this.attendanceDAO = new AttendanceDAO();
    }

    public boolean checkIn(String employeeId) {
        Attendance today = attendanceDAO.getTodayAttendance(employeeId);
        if (today != null) {
            return false;
        }
        
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(IDGenerator.generateAttendanceId());
        attendance.setEmployeeId(employeeId);
        attendance.setDate(LocalDate.now());
        attendance.setCheckIn(LocalTime.now());
        
        LocalTime checkInTime = LocalTime.now();
        if (checkInTime.isAfter(LocalTime.of(9, 0))) {
            attendance.setStatus("LATE");
        } else {
            attendance.setStatus("PRESENT");
        }
        
        return attendanceDAO.addAttendance(attendance);
    }

    public boolean checkOut(String employeeId) {
        Attendance today = attendanceDAO.getTodayAttendance(employeeId);
        if (today == null || today.getCheckOut() != null) {
            return false;
        }
        
        today.setCheckOut(LocalTime.now());
        return attendanceDAO.updateAttendance(today);
    }

    public List<Attendance> getAllAttendances() {
        return attendanceDAO.getAllAttendances();
    }

    public List<Attendance> getEmployeeAttendance(String employeeId) {
        return attendanceDAO.getAttendanceByEmployeeId(employeeId);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceDAO.getAttendanceByDate(date);
    }
}
