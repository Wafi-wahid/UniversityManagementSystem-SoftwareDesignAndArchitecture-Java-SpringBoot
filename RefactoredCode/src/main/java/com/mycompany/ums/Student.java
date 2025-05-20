package com.mycompany.ums;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Student data class with only data properties.
 * SRP: only responsible for student data.
 */
public final class Student {
    private final String id;
    private final String name;
    private final String email;
    private final String phone;

    public Student(String id, String name, String email, String phone) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.phone = Objects.requireNonNull(phone, "Phone cannot be null");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}

/**
 * Interface for Student registration logic.
 * ISP: Separate registration logic from Student data.
 */
interface StudentRegistration {
    Student register(List<Student> students, Scanner scanner);
}

/**
 * Interface for dues verification.
 */
interface DuesVerification {
    boolean areDuesCleared(Student student);
}

/**
 * Concrete implementation for registering students via console input.
 * OCP: can extend or replace without modifying Student class.
 */
class ConsoleStudentRegistration implements StudentRegistration {
    @Override
    public Student register(List<Student> students, Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();

        // Check duplicate ID
        if (students.stream().anyMatch(s -> s.getId().equalsIgnoreCase(id))) {
            System.out.println("Student ID already exists.");
            return null;
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine().trim();

        Student newStudent = new Student(id, name, email, phone);
        students.add(newStudent);
        System.out.println("Student registered successfully: " + name);
        return newStudent;
    }
}

/**
 * Concrete implementation of dues verification.
 */
class SimpleDuesVerification implements DuesVerification {
    @Override
    public boolean areDuesCleared(Student student) {
        // TODO: Implement actual dues check, here returns true by default
        return true;
    }
}
