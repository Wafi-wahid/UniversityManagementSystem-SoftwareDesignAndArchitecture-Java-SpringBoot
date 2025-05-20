package com.mycompany.ums;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Course abstraction for LSP compliance.
 */
class Course {
    private final String code;

    public Course(String code, String title, int creditHours) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null or blank");
        }
        this.code = code.toUpperCase();
    }

    // public Course(String code2, String title, int creditHours) {
    //     //TODO Auto-generated constructor stub
    // }

    public String getCode() {
        return code;
    }

    public Object getTitle() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTitle'");
    }

    public Object getCreditHours() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCreditHours'");
    }
}

/**
 * Interface segregation: Input handler interface for prerequisites.
 */
interface PrerequisiteInputHandler {
    String promptForValidCourseCode(Scanner scanner, List<Course> courses);
    List<String> collectPrerequisites(Scanner scanner);
}

/**
 * Concrete implementation for console-based prerequisite input.
 * Open for extension, closed for modification.
 */
class ConsolePrerequisiteInputHandler implements PrerequisiteInputHandler {
    private static final String COURSE_CODE_PROMPT = "\nEnter course code to set prerequisites for: ";
    private static final String PREREQ_PROMPT = "Enter prerequisite course codes (one per line, enter 'done' to finish):";
    private static final String DONE_COMMAND = "done";

    @Override
    public String promptForValidCourseCode(Scanner scanner, List<Course> courses) {
        String courseCode;
        do {
            System.out.print(COURSE_CODE_PROMPT);
            courseCode = scanner.nextLine().trim();
        } while (!isValidCourseCode(courseCode, courses));
        return courseCode.toUpperCase();
    }

    private boolean isValidCourseCode(String code, List<Course> courses) {
        return courses.stream()
                      .anyMatch(c -> c.getCode().equalsIgnoreCase(code));
    }

    @Override
    public List<String> collectPrerequisites(Scanner scanner) {
        System.out.println(PREREQ_PROMPT);
        List<String> prereqs = new ArrayList<>();
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase(DONE_COMMAND)) {
                break;
            }
            if (!input.isEmpty()) {
                prereqs.add(input.toUpperCase());
            }
        }
        return prereqs;
    }
}

/**
 * Client class demonstrating Dependency Inversion Principle.
 * Depends on the abstraction PrerequisiteInputHandler.
 */
public class PrerequisiteInputClient {
    private final PrerequisiteInputHandler inputHandler;

    public PrerequisiteInputClient(PrerequisiteInputHandler inputHandler) {
        if (inputHandler == null) {
            throw new IllegalArgumentException("Input handler cannot be null");
        }
        this.inputHandler = inputHandler;
    }

    public void handleInput(Scanner scanner, List<Course> courses) {
        String courseCode = inputHandler.promptForValidCourseCode(scanner, courses);
        List<String> prereqs = inputHandler.collectPrerequisites(scanner);

        System.out.println("Course: " + courseCode);
        System.out.println("Prerequisites: " + prereqs);
    }

    public static void main(String[] args) {
        List<Course> courses = List.of(
            // 
        );

        PrerequisiteInputHandler handler = new ConsolePrerequisiteInputHandler();
        PrerequisiteInputClient client = new PrerequisiteInputClient(handler);

        try (Scanner scanner = new Scanner(System.in)) {
            client.handleInput(scanner, courses);
        }
    }
}
