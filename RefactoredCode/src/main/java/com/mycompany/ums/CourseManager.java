package com.mycompany.ums;

import java.util.List;
import java.util.Scanner;

// Interface for validation logic
interface CourseInputValidator {
    void validate(Course course) throws IllegalArgumentException;
}

// Concrete validator: validates all fields (could be split further)
class DefaultCourseValidator implements CourseInputValidator {
    @Override
    public void validate(Course course) {
        if (course.getCode() == null || course.getCode().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be empty");
        }
        if (course.getTitle() == null || ((String) course.getTitle()).isEmpty()) {
            throw new IllegalArgumentException("Course title cannot be empty");
        }
        // if (course.getCreditHours() <= 0) {
        //     throw new IllegalArgumentException("Credit hours must be positive");
        // }
    }
}

// Service class for course operations
class CourseService {
    private final CourseInputValidator validator;

    // Dependency Inversion Principle (DIP):
    // CourseService depends on abstraction (CourseInputValidator), not on concrete implementation
    public CourseService(CourseInputValidator validator) {
        this.validator = validator;
    }

    public void addCourse(List<Course> courses, Course course) {
        validator.validate(course);
        courses.add(course);
        System.out.println("Course added successfully!");
    }

    public void viewAllCourses(List<Course> courses) {
        System.out.println("\nAvailable Courses:");
        System.out.printf("%-10s %-30s %-5s\n", "Code", "Title", "Credits");
        for (Course course : courses) {
            System.out.printf("%-10s %-30s %-5d\n",
                    course.getCode(), course.getTitle(), course.getCreditHours());
        }
    }
}

// CourseManager handles input/output (user interaction only)
public final class CourseManager {
    private static final String ADD_COURSE_HEADER = "\nAdd New Course";

    private final CourseService courseService;

    // Dependency Inversion Principle (DIP):
    // CourseManager depends on CourseService abstraction
    public CourseManager(CourseService courseService) {
        this.courseService = courseService;
    }

    public void addCourse(List<Course> courses, Scanner scanner) {
        System.out.println(ADD_COURSE_HEADER);

        String code = promptForNonEmptyInput(scanner, "Enter Course Code: ");
        String title = promptForNonEmptyInput(scanner, "Enter Course Title: ");
        int creditHours = promptForPositiveInt(scanner, "Enter Credit Hours: ");

        Course newCourse = new Course(code, title, creditHours);
        courseService.addCourse(courses, newCourse);
    }

    public void viewAllCourses(List<Course> courses) {
        courseService.viewAllCourses(courses);
    }

    private String promptForNonEmptyInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    private int promptForPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0) return value;
                System.out.println("Credit hours must be positive");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
}
