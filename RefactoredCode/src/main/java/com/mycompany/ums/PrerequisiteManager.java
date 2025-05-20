package com.mycompany.ums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Manages course prerequisites and prerequisite checking.
 */
public final class PrerequisiteManager {
    private static final String SET_PREREQ_HEADER = "\nSet Course Prerequisites";
    private static final String COURSE_CODE_PROMPT = "\nEnter course code to set prerequisites for: ";
    private static final String PREREQ_PROMPT = "Enter prerequisite course codes (one per line, enter 'done' to finish):";
    private static final String DONE_COMMAND = "done";
    private static final String SUCCESS_MSG = "Prerequisites set successfully for %s";

    /**
     * Sets prerequisites for a course.
     */
    public static void setPrerequisites(Map<String, List<String>> prerequisites,
                                      List<Course> courses,
                                      Scanner scanner) {
        validateInput(prerequisites, courses, scanner);
        
        System.out.println(SET_PREREQ_HEADER);
        Course.viewAllCourses(courses);
        
        String courseCode = promptForValidCourseCode(scanner, courses);
        List<String> prereqs = collectPrerequisites(scanner);
        
        prerequisites.put(courseCode, prereqs);
        System.out.println(String.format(SUCCESS_MSG, courseCode));
    }

    /**
     * Checks if a student has met all prerequisites for a course.
     */
    public static boolean checkPrerequisites(String studentId,
                                           String courseCode,
                                           Map<String, List<String>> prerequisites,
                                           Map<String, List<String>> academicHistory) {
        validatePrerequisiteCheckInput(studentId, courseCode, prerequisites, academicHistory);
        
        if (!prerequisites.containsKey(courseCode)) {
            return true; // no prerequisites
        }
        
        List<String> required = prerequisites.get(courseCode);
        List<String> completed = academicHistory.getOrDefault(studentId, Collections.emptyList());
        
        return hasCompletedAllPrerequisites(required, completed);
    }

    private static void validateInput(Map<String, List<String>> prerequisites,
                                    List<Course> courses,
                                    Scanner scanner) {
        if (prerequisites == null || courses == null || scanner == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
    }

    private static String promptForValidCourseCode(Scanner scanner, List<Course> courses) {
        String courseCode;
        do {
            System.out.print(COURSE_CODE_PROMPT);
            courseCode = scanner.nextLine().trim();
        } while (!isValidCourseCode(courseCode, courses));
        return courseCode;
    }

    private static boolean isValidCourseCode(String code, List<Course> courses) {
        return courses.stream().anyMatch(c -> c.getCode().equalsIgnoreCase(code));
    }

    private static List<String> collectPrerequisites(Scanner scanner) {
        System.out.println(PREREQ_PROMPT);
        List<String> prereqs = new ArrayList<>();
        
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase(DONE_COMMAND)) {
                break;
            }
            if (!input.isEmpty()) {
                prereqs.add(input);
            }
        }
        return prereqs;
    }

    private static void validatePrerequisiteCheckInput(String studentId,
                                                     String courseCode,
                                                     Map<String, List<String>> prerequisites,
                                                     Map<String, List<String>> academicHistory) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        if (prerequisites == null || academicHistory == null) {
            throw new IllegalArgumentException("Maps cannot be null");
        }
    }

    private static boolean hasCompletedAllPrerequisites(List<String> required,
                                                      List<String> completed) {
        return required.stream()
                      .allMatch(prereq -> completed.stream()
                                                   .anyMatch(course -> course.startsWith(prereq)));
    }
}