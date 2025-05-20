package com.mycompany.ums;

import java.util.Map;
import java.util.Objects;
import java.util.List;

/**
 * Interface for displaying scholarship info
 * Interface Segregation Principle: Focused interface for display responsibilities
 */
interface ScholarshipDisplayInterface {
    void showHeader();
    void showGPA(double gpa);
    void showMessage(String message);
}

/**
 * Concrete implementation for console output of scholarship info
 * Open for extension (OCP): New display types can implement this interface
 */
class ConsoleScholarshipDisplay implements ScholarshipDisplayInterface {
    private static final String SCHOLARSHIP_HEADER = "\nScholarship Status";
    private static final String GPA_FORMAT = "Your current GPA: %.2f%n";

    @Override
    public void showHeader() {
        System.out.println(SCHOLARSHIP_HEADER);
    }

    @Override
    public void showGPA(double gpa) {
        System.out.printf(GPA_FORMAT, gpa);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}

/**
 * Interface for calculating GPA and scholarship message.
 */
interface ScholarshipCalculatorInterface {
    double getGPA(String studentId, Map<String, Double> studentGPAs);
    String determineScholarshipMessage(double gpa);
}

/**
 * Implementation of ScholarshipCalculatorInterface
 */
class ScholarshipCalculator implements ScholarshipCalculatorInterface {
    private static final double DEFAULT_GPA = 0.0;

    private static final ScholarshipTier[] TIERS = {
        new ScholarshipTier(3.8, "Congratulations! You qualify for 100% scholarship."),
        new ScholarshipTier(3.5, "You qualify for 50% scholarship."),
        new ScholarshipTier(3.0, "You qualify for 25% scholarship.")
    };

    @Override
    public double getGPA(String studentId, Map<String, Double> studentGPAs) {
        return studentGPAs.getOrDefault(studentId, DEFAULT_GPA);
    }

    @Override
    public String determineScholarshipMessage(double gpa) {
        for (ScholarshipTier tier : TIERS) {
            if (gpa >= tier.threshold()) {
                return tier.message();
            }
        }
        return "You currently don't qualify for any scholarships.";
    }

    private record ScholarshipTier(double threshold, String message) {
        ScholarshipTier {
            Objects.requireNonNull(message, "Message cannot be null");
            if (threshold < 0) {
                throw new IllegalArgumentException("Threshold cannot be negative");
            }
            if (message.isBlank()) {
                throw new IllegalArgumentException("Message cannot be empty");
            }
        }
    }
}

/**
 * Controller class demonstrating Dependency Inversion Principle.
 * Depends on abstraction for calculation and display.
 */
public final class ScholarshipDisplay {
    private final ScholarshipCalculatorInterface calculator;
    private final ScholarshipDisplayInterface display;

    public ScholarshipDisplay(ScholarshipCalculatorInterface calculator, ScholarshipDisplayInterface display) {
        this.calculator = Objects.requireNonNull(calculator, "Calculator cannot be null");
        this.display = Objects.requireNonNull(display, "Display cannot be null");
    }

    public void checkScholarship(String studentId, Map<String, Double> studentGPAs) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (studentGPAs == null) {
            throw new IllegalArgumentException("Student GPAs map cannot be null");
        }

        display.showHeader();

        double gpa = calculator.getGPA(studentId, studentGPAs);
        display.showGPA(gpa);

        String message = calculator.determineScholarshipMessage(gpa);
        display.showMessage(message);
    }

    // Example main for demo
    public static void main(String[] args) {
        Map<String, Double> studentGPAs = Map.of(
            "S001", 3.9,
            "S002", 3.6,
            "S003", 2.9
        );

        ScholarshipCalculatorInterface calculator = new ScholarshipCalculator();
        ScholarshipDisplayInterface display = new ConsoleScholarshipDisplay();

        ScholarshipDisplay scholarship = new ScholarshipDisplay(calculator, display);

        List.of("S001", "S002", "S003").forEach(id -> {
            scholarship.checkScholarship(id, studentGPAs);
        });
    }
}
