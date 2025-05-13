/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// FYP Manager class

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class FYPManager {
    public static void registerForFYP(String studentId, Map<String, Integer> completedCredits,
                                     Map<String, Boolean> fypStatus, Map<String, List<String>> enrolledCourses,
                                     Map<String, List<String>> prerequisites, Scanner scanner) {
        System.out.println("\nFinal Year Project Registration");
        
        int credits = completedCredits.getOrDefault(studentId, 0);
        if (credits < 100) {
            System.out.println("You need at least 100 completed credits to register for FYP.");
            System.out.println("Your completed credits: " + credits);
            return;
        }
        
        if (fypStatus.getOrDefault(studentId, false)) {
            System.out.println("You are already registered for FYP.");
            return;
        }
        
        // Check prerequisites
        if (!PrerequisiteManager.checkPrerequisites(studentId, "CS801", prerequisites, 
                UMS.academicHistory)) {
            System.out.println("You haven't completed all prerequisites for FYP.");
            return;
        }
        
        System.out.print("Enter FYP title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter supervisor name: ");
        String supervisor = scanner.nextLine();
        
        fypStatus.put(studentId, true);
        enrolledCourses.computeIfAbsent(studentId, k -> new ArrayList<>()).add("CS801");
        System.out.println("Successfully registered for FYP!");
    }
}
