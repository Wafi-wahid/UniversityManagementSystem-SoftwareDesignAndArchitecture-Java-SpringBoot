/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Degree Clearance class

import java.util.Map;
import java.util.Scanner;

class DegreeClearance {
    public static void applyForClearance(String studentId, Map<String, Boolean> degreeClearance,
                                        Map<String, Boolean> feeStatus, Map<String, Integer> completedCredits,
                                        Scanner scanner) {
        System.out.println("\nDegree Clearance Application");
        
        if (degreeClearance.getOrDefault(studentId, false)) {
            System.out.println("Your degree clearance is already in progress.");
            return;
        }
        
        // Check requirements
        if (!feeStatus.getOrDefault(studentId, false)) {
            System.out.println("You have unpaid fees. Cannot apply for clearance.");
            return;
        }
        
        if (completedCredits.getOrDefault(studentId, 0) < 130) {
            System.out.println("You haven't completed the required 130 credits.");
            return;
        }
        
        System.out.println("Checking clearance from all departments...");
        System.out.println("- Finance: Clear");
        System.out.println("- Academics: Clear");
        System.out.println("- Library: Clear");
        
        degreeClearance.put(studentId, true);
        System.out.println("Degree clearance application submitted successfully!");
    }
}

