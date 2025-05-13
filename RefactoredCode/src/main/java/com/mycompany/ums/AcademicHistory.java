/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Academic History class

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class AcademicHistory {
    public static void viewHistory(String studentId, Map<String, List<String>> academicHistory) {
        System.out.println("\nAcademic History");
        
        List<String> history = academicHistory.getOrDefault(studentId, new ArrayList<>());
        if (history.isEmpty()) {
            System.out.println("No academic history available.");
            return;
        }
        
        history.forEach(System.out::println);
    }
}
