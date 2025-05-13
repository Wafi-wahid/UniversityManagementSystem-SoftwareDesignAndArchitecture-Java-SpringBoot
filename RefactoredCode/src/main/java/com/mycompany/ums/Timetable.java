/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Timetable class

import java.util.HashMap;
import java.util.Map;

class Timetable {
    public static void viewTimetable(String studentId, Map<String, Map<String, String>> timetables) {
        System.out.println("\nYour Weekly Timetable");
        
        Map<String, String> timetable = timetables.getOrDefault(studentId, new HashMap<>());
        if (timetable.isEmpty()) {
            System.out.println("Timetable not available yet.");
            return;
        }
        
        System.out.printf("%-10s %-15s %-10s\n", "Day", "Time", "Course");
        timetable.forEach((day, course) -> 
            System.out.printf("%-10s %-15s %-10s\n", day, "09:00-11:00", course));
    }
}

