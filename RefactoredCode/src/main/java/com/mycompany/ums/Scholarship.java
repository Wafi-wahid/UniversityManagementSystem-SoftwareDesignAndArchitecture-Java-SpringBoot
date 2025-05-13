/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;
// Scholarship class

import java.util.Map;

class Scholarship {
    public static void checkScholarship(String studentId, Map<String, Double> studentGPAs) {
        System.out.println("\nScholarship Status");
        
        double gpa = studentGPAs.getOrDefault(studentId, 0.0);
        System.out.println("Your current GPA: " + gpa);
        
        if (gpa >= 3.8) {
            System.out.println("Congratulations! You qualify for 100% scholarship.");
        } else if (gpa >= 3.5) {
            System.out.println("You qualify for 50% scholarship.");
        } else if (gpa >= 3.0) {
            System.out.println("You qualify for 25% scholarship.");
        } else {
            System.out.println("You currently don't qualify for any scholarships.");
        }
    }
}
