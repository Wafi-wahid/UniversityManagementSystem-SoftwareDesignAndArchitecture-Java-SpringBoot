/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Course class for course management

import java.util.List;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private int creditHours;
    
    public Course(String code, String title, int creditHours) {
        this.code = code;
        this.title = title;
        this.creditHours = creditHours;
    }
    
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCreditHours() { return creditHours; }
    
    public static void addCourse(List<Course> courses, Scanner scanner) {
        System.out.println("\nAdd New Course");
        
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter Credit Hours: ");
        int creditHours = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Course newCourse = new Course(code, title, creditHours);
        courses.add(newCourse);
        
        System.out.println("Course added successfully!");
    }
    
    public static void viewAllCourses(List<Course> courses) {
        System.out.println("\nAvailable Courses:");
        System.out.printf("%-10s %-30s %-5s\n", "Code", "Title", "Credits");
        for (Course c : courses) {
            System.out.printf("%-10s %-30s %-5d\n", c.getCode(), c.getTitle(), c.getCreditHours());
        }
    }
}
