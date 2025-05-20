package com.mycompany.ums;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Manager class demonstrating Dependency Inversion Principle by
 * depending on PrerequisiteValidatorInterface abstraction instead of concrete.
 */
public final class PrerequisiteValidator {
    private static PrerequisiteValidatorInterface validator = null;

    public static PrerequisiteValidatorInterface getValidator() {
        return validator;
    }

    public static boolean checkPrerequisites(String studentId,
                                      String courseCode,
                                      Map<String, List<String>> prerequisites,
                                      Map<String, List<String>> academicHistory) {
        validator.validateCheckInputs(studentId, courseCode, prerequisites, academicHistory);

        if (!prerequisites.containsKey(courseCode)) {
            return true; // no prereqs => allowed
        }

        List<String> required = prerequisites.get(courseCode);
        List<String> completed = academicHistory.getOrDefault(studentId, List.of());

        return validator.hasCompletedAll(required, completed);
    }

    // Constructor Dependency Injection (DIP)
    public void PrerequisiteManager(PrerequisiteValidatorInterface validator) {
        if (validator == null) throw new IllegalArgumentException("Validator cannot be null");
        PrerequisiteValidator.validator = validator;
    }

    public void setPrerequisites(Map<String, List<String>> prerequisites,
                                 List<Course> courses,
                                 Scanner scanner,
                                 PrerequisiteInputHandler inputHandler) {
        validator.validateSetInputs(prerequisites, courses, scanner);

        System.out.println("\nSet Course Prerequisites");
        Course.viewAllCourses(courses);

        String courseCode = inputHandler.promptForValidCourseCode(scanner, courses);
        List<String> prereqs = inputHandler.collectPrerequisites(scanner);

        prerequisites.put(courseCode, prereqs);
        System.out.println(String.format("Prerequisites set successfully for %s", courseCode));
    }

    public static void setValidator(PrerequisiteValidatorInterface validator) {
        PrerequisiteValidator.validator = validator;
    }
}

/**
 * Interface segregation: Validator interface for prerequisite validation.
 */
interface PrerequisiteValidatorInterface {
    void validateSetInputs(Map<String, List<String>> prerequisites, List<Course> courses, Scanner scanner);
    void validateCheckInputs(String studentId, String courseCode, Map<String, List<String>> prerequisites, Map<String, List<String>> academicHistory);
    boolean hasCompletedAll(List<String> required, List<String> completed);
}

/**
 * Concrete implementation of validator.
 * Open for extension, closed for modification.
 */
// 

/**
 * Sample Course class for completeness.
 */
class Course {
    public static void viewAllCourses(List<Course> courses) {
        System.out.println("\nAvailable Courses:");
        courses.forEach(c -> System.out.println("- " + c.getCode()));
    }

    private final String code;

    public Course(String code, String title, int creditHours) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        this.code = code.toUpperCase();
    }

    public String getCode() {
        return code;
    }
}

/**
 * Input handler interface (ISP)
 */
interface PrerequisiteInputHandler {
    String promptForValidCourseCode(Scanner scanner, List<Course> courses);
    List<String> collectPrerequisites(Scanner scanner);
}

/**
 * Console-based input handler
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

    @Override
    public List<String> collectPrerequisites(Scanner scanner) {
        System.out.println(PREREQ_PROMPT);
        List<String> prereqs = new java.util.ArrayList<>();
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

    private boolean isValidCourseCode(String code, List<Course> courses) {
        return courses.stream()
                      .anyMatch(c -> c.getCode().equalsIgnoreCase(code));
    }
}
