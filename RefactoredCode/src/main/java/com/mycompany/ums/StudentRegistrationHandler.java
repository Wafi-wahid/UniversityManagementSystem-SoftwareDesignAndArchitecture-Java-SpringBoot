package com.mycompany.ums;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

// Student entity class
final class Student {
    private final String id;
    private final String name;
    private final String email;
    private final String phone;

    public Student(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}

// Validator class with static methods
final class StudentValidator {

    public static String validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
        // Add more rules if needed, e.g. format checks
        return id;
    }

    public static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        return name;
    }

    public static String validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.contains("@")) { // Very simple email check
            throw new IllegalArgumentException("Email must contain '@'");
        }
        return email;
    }

    public static String validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be empty");
        }
        if (!phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Phone must be 10 digits");
        }
        return phone;
    }
}

// Main handler class for registration
public final class StudentRegistrationHandler {

    public static void registerStudent(List<Student> students, Scanner scanner) {
        validateInput(students, scanner);

        System.out.println("\nStudent Registration");

        String id = promptForInput(scanner, "Enter Student ID: ", StudentValidator::validateId);
        String name = promptForInput(scanner, "Enter Full Name: ", StudentValidator::validateName);
        String email = promptForInput(scanner, "Enter Email: ", StudentValidator::validateEmail);
        String phone = promptForInput(scanner, "Enter Phone Number (10 digits): ", StudentValidator::validatePhone);

        Student newStudent = new Student(id, name, email, phone);
        students.add(newStudent);

        System.out.println("Student registered successfully!");
    }

    private static void validateInput(List<Student> students, Scanner scanner) {
        if (students == null) {
            throw new IllegalArgumentException("Students list cannot be null");
        }
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
    }

    private static String promptForInput(Scanner scanner, String prompt, Function<String, String> validator) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return validator.apply(input);  // Validation happens here
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
