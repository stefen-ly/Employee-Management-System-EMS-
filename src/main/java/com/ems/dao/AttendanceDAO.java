package com.ems.dao;

import com.ems.dao.interfaces.IAttendanceDAO;
import com.ems.data.DataStore;
import com.ems.model.Attendance;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceDAO implements IAttendanceDAO {
    private DataStore dataStore;

    public AttendanceDAO() {
        this.dataStore = DataStore.getInstance();
    }

    @Override
    public boolean addAttendance(Attendance attendance) {
        boolean result = dataStore.getAttendances().add(attendance);
        if (result) {
            dataStore.saveAllData();
        }
        return result;
    }

    @Override
    public List<Attendance> getAllAttendances() {
        return dataStore.getAttendances();
    }

    @Override
    public List<Attendance> getAttendanceByEmployeeId(String employeeId) {
        return dataStore.getAttendances().stream()
                .filter(att -> att.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return dataStore.getAttendances().stream()
                .filter(att -> att.getDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public Attendance getTodayAttendance(String employeeId) {
        LocalDate today = LocalDate.now();
        return dataStore.getAttendances().stream()
                .filter(att -> att.getEmployeeId().equals(employeeId) && 
                             att.getDate().equals(today))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateAttendance(Attendance attendance) {
        Attendance existing = dataStore.getAttendances().stream()
                .filter(att -> att.getAttendanceId().equals(attendance.getAttendanceId()))
                .findFirst()
                .orElse(null);
        
        if (existing != null) {
            dataStore.getAttendances().remove(existing);
            dataStore.getAttendances().add(attendance);
            dataStore.saveAllData();
            return true;
        }
        return false;
    }
}
