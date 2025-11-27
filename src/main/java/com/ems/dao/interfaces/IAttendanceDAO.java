package com.ems.dao.interfaces;

import com.ems.model.Attendance;
import java.time.LocalDate;
import java.util.List;

public interface IAttendanceDAO {
    boolean addAttendance(Attendance attendance);
    List<Attendance> getAllAttendances();
    List<Attendance> getAttendanceByEmployeeId(String employeeId);
    List<Attendance> getAttendanceByDate(LocalDate date);
    Attendance getTodayAttendance(String employeeId);
    boolean updateAttendance(Attendance attendance);
}
