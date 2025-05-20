package com.mycompany.ums;

import java.util.*;

// Course class (immutable data)
// public final class Course {
//     private final String code;
//     private final String title;
//     private final int creditHours;

//     public Course(String code, String title, int creditHours) {
//         this.code = code;
//         this.title = title;
//         this.creditHours = creditHours;
//     }

//     public String getCode() { return code; }
//     public String getTitle() { return title; }
//     public int getCreditHours() { return creditHours; }
// }

// Main class for running example
public class CourseApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Setup validators and repository
        List<CourseValidator> validators = List.of(
            new CourseCodeValidator(),
            new CourseTitleValidator(),
            new CreditHoursValidator()
        );

        CourseRepository repository = new CourseRepository();
        CourseService service = new CourseService(repository, validators);
        CourseInputHandler inputHandler = new CourseInputHandler(scanner, service);

        // Example usage
        inputHandler.addCourseFromInput(); // Add one course from CLI input
        service.viewAllCourses();

        scanner.close();
    }

    @Override
    public String toString() {
        return "CourseApp []";
    }
}

// Validator interface (ISP)
interface CourseValidator {
    void validate(Course course) throws IllegalArgumentException;
}

// Concrete validators
class CourseCodeValidator implements CourseValidator {
    @Override
    public void validate(Course course) {
        if (course.getCode() == null || course.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be empty");
        }
    }
}

class CourseTitleValidator implements CourseValidator {
    @Override
    public void validate(Course course) {
        // if (course.getTitle() == null || course.getTitle().trim().isEmpty()) {
        //     throw new IllegalArgumentException("Course title cannot be empty");
        // }
    }
}

class CreditHoursValidator implements CourseValidator {
    @Override
    public void validate(Course course) {
        // if (course.getCreditHours() <= 0) {
        //     throw new IllegalArgumentException("Credit hours must be positive");
        // }
    }
}

// Repository for course storage (SRP)
class CourseRepository {
    private final List<Course> courses = new ArrayList<>();

    public void add(Course course) {
        courses.add(course);
    }

    public List<Course> getAll() {
        return Collections.unmodifiableList(courses);
    }
}

// Service that uses repository and validators (DIP, OCP)
class CourseService {
    private final CourseRepository repository;
    private final List<CourseValidator> validators;

    public CourseService(CourseRepository repository, List<CourseValidator> validators) {
        this.repository = repository;
        this.validators = validators;
    }

    public void addCourse(Course course) {
        // Validate course
        for (CourseValidator validator : validators) {
            validator.validate(course);
        }
        // Store course
        repository.add(course);
        System.out.println("Course added successfully!");
    }

    public void viewAllCourses() {
        List<Course> courses = repository.getAll();
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        System.out.println("List of courses:");
        for (Course c : courses) {
            System.out.printf("Code: %s, Title: %s, Credit Hours: %d%n",
                c.getCode(), c.getTitle(), c.getCreditHours());
        }
    }
}

// Input handler class (separate I/O logic from service)
class CourseInputHandler {
    private final Scanner scanner;
    private final CourseService service;

    public CourseInputHandler(Scanner scanner, CourseService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public void addCourseFromInput() throws NumberFormatException {
        try {
            System.out.print("Enter course code: ");
            String code = scanner.nextLine().trim();

            System.out.print("Enter course title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Enter credit hours: ");
            int creditHours = Integer.parseInt(scanner.nextLine().trim());

            Course course = new Course(code, title, creditHours);
            service.addCourse(course);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }
}
