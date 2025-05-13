/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


// Prerequisite Manager class
class PrerequisiteManager {
    public static void setPrerequisites(Map<String, List<String>> prerequisites, 
                                      List<Course> courses, Scanner scanner) {
        System.out.println("\nSet Course Prerequisites");
        
        Course.viewAllCourses(courses);
        
        System.out.print("\nEnter course code to set prerequisites for: ");
        String courseCode = scanner.nextLine();
        
        List<String> prereqs = new ArrayList<>();
        System.out.println("Enter prerequisite course codes (one per line, enter 'done' to finish):");
        
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;
            prereqs.add(input);
        }
        
        prerequisites.put(courseCode, prereqs);
        System.out.println("Prerequisites set successfully for " + courseCode);
    }
    
    public static boolean checkPrerequisites(String studentId, String courseCode, 
                                           Map<String, List<String>> prerequisites,
                                           Map<String, List<String>> academicHistory) {
        if (!prerequisites.containsKey(courseCode)) {
            return true; // no prerequisites
        }
        
        List<String> required = prerequisites.get(courseCode);
        List<String> completed = academicHistory.getOrDefault(studentId, new ArrayList<>());
        
        for (String prereq : required) {
            boolean found = false;
            for (String course : completed) {
                if (course.startsWith(prereq)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        
        return true;
    }
}
