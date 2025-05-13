/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Course Material class

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class CourseMaterial {
    public static void accessMaterials(String studentId, Map<String, List<String>> enrolledCourses,
                                     Map<String, List<String>> courseMaterials, Scanner scanner) {
        System.out.println("\nAccess Course Materials");
        
        List<String> enrolled = enrolledCourses.getOrDefault(studentId, new ArrayList<>());
        if (enrolled.isEmpty()) {
            System.out.println("You are not enrolled in any courses.");
            return;
        }
        
        System.out.println("Your enrolled courses:");
        enrolled.forEach(c -> System.out.println("- " + c));
        
        System.out.print("\nEnter course code to access materials: ");
        String courseCode = scanner.nextLine();
        
        if (!enrolled.contains(courseCode)) {
            System.out.println("You are not enrolled in this course.");
            return;
        }
        
        List<String> materials = courseMaterials.getOrDefault(courseCode, new ArrayList<>());
        if (materials.isEmpty()) {
            System.out.println("No materials available for this course yet.");
            return;
        }
        
        System.out.println("\nMaterials for " + courseCode + ":");
        materials.forEach(System.out::println);
    }
}