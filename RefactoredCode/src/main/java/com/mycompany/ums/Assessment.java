/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Assessment class

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Assessment {
    private String courseCode;
    private String type; // quiz, assignment, exam
    private LocalDate dueDate;
    private String submission;
    
    public Assessment(String courseCode, String type, LocalDate dueDate) {
        this.courseCode = courseCode;
        this.type = type;
        this.dueDate = dueDate;
    }
    
    public static void submitAssessment(String studentId, Map<String, List<Assessment>> studentAssessments,
                                      Scanner scanner) {
        System.out.println("\nSubmit Assessment");
        
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        
        System.out.print("Enter assessment type (quiz/assignment/exam): ");
        String type = scanner.nextLine();
        
        System.out.println("Enter submission text (end with 'END' on a new line):");
        StringBuilder submission = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            submission.append(line).append("\n");
        }
        
        Assessment assessment = new Assessment(courseCode, type, LocalDate.now());
        assessment.submission = submission.toString();
        
        studentAssessments.computeIfAbsent(studentId, k -> new ArrayList<>()).add(assessment);
        System.out.println("Assessment submitted successfully!");
    }
}
