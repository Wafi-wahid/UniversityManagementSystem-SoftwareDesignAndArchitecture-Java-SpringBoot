/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Exam class

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Exam {
    public static void viewExams(String studentId, Map<String, List<String>> enrolledCourses,
                                Map<String, List<String>> examSchedule) {
        System.out.println("\nExam Schedule");
        
        List<String> enrolled = enrolledCourses.getOrDefault(studentId, new ArrayList<>());
        if (enrolled.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
        }
        
        System.out.printf("%-10s %-15s %-15s\n", "Course", "Date", "Time");
        for (String course : enrolled) {
            // In a real system, this would come from a proper schedule
            System.out.printf("%-10s %-15s %-15s\n", course, "2025-05-20", "09:00 AM");
        }
    }
}