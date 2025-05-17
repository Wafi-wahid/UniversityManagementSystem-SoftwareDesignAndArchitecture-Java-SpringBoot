/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ocp;

import java.util.ArrayList;
import java.util.List;

public class AttendanceTracker {
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public void markAttendance(String studentId, String courseCode) {
        boolean found = false;
        for (AttendanceRecord record : attendanceRecords) {
            if (record.studentId.equals(studentId) && record.courseCode.equals(courseCode)) {
                record.count++;
                found = true;
                break;
            }
        }
        
        if (!found) {
            attendanceRecords.add(new AttendanceRecord(studentId, courseCode));
        }
        
        System.out.println("Attendance marked for " + courseCode);
    }
    
    public void viewAttendance(String studentId) {
        System.out.println("\nYour Attendance Record:");
        boolean found = false;
        
        for (AttendanceRecord record : attendanceRecords) {
            if (record.studentId.equals(studentId)) {
                System.out.println(record.courseCode + ": " + record.count + " classes attended");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No attendance records found.");
        }
    }
    

}
