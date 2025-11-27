package com.ems.util;

import com.ems.data.DataStore;

public class IDGenerator {
    
    public static String generateEmployeeId() {
        DataStore dataStore = DataStore.getInstance();
        int count = dataStore.getEmployees().size() + 1;
        return String.format("emp%03d", count);
    }
    
    public static String generateDepartmentId() {
        DataStore dataStore = DataStore.getInstance();
        int count = dataStore.getDepartments().size() + 1;
        return String.format("dep%d", count);
    }
    
    public static String generateAttendanceId() {
        DataStore dataStore = DataStore.getInstance();
        int count = dataStore.getAttendances().size() + 1;
        return String.format("att%03d", count);
    }
    
    public static String generateLeaveId() {
        DataStore dataStore = DataStore.getInstance();
        int count = dataStore.getLeaveRequests().size() + 1;
        return String.format("lv%03d", count);
    }
    
    public static String generateSalaryId() {
        DataStore dataStore = DataStore.getInstance();
        int count = dataStore.getSalaryRecords().size() + 1;
        return String.format("sal%03d", count);
    }
}
