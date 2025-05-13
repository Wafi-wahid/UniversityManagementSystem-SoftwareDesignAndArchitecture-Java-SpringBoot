/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Result class

import java.util.HashMap;
import java.util.Map;

class Result {
    public static void viewResults(String studentId, Map<String, Map<String, Double>> semesterResults) {
        System.out.println("\nAcademic Results");
        
        Map<String, Double> results = semesterResults.getOrDefault(studentId, new HashMap<>());
        if (results.isEmpty()) {
            System.out.println("No results available yet.");
            return;
        }
        
        System.out.printf("%-10s %-5s\n", "Course", "Grade");
        results.forEach((course, grade) -> 
            System.out.printf("%-10s %-5.2f\n", course, grade));
    }
}
