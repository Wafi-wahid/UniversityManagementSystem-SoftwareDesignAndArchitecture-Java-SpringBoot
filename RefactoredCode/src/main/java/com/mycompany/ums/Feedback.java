/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Feedback class

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Feedback {
    public static void submitFeedback(String studentId, Map<String, List<String>> feedback, Scanner scanner) {
        System.out.println("\nSubmit Feedback");
        
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        
        System.out.print("Enter your feedback: ");
        String fb = scanner.nextLine();
        
        String feedbackEntry = courseCode + ": " + fb;
        feedback.computeIfAbsent(studentId, k -> new ArrayList<>()).add(feedbackEntry);
        
        System.out.println("Thank you for your feedback!");
    }
}
