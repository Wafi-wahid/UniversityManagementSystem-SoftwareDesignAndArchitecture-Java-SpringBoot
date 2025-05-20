package com.mycompany.ums;

import java.util.Map;

/**
 * Interface for calculating GPA and scholarship message.
 */
interface ScholarshipCalculatorInterface {
    double getGPA(String studentId, Map<String, Double> studentGPAs);
    String determineScholarshipMessage(double gpa);
}

/**
 * Implementation of ScholarshipCalculatorInterface
 * Open-Closed Principle: you can extend scholarship tiers without modifying this class.
 */
class ScholarshipCalculator implements ScholarshipCalculatorInterface {
    private static final double DEFAULT_GPA = 0.0;

    // Scholarship tiers with thresholds and messages (can be extended)
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

    /**
     * Inner record to hold scholarship tier info.
     * Liskov Substitution Principle: ScholarshipTier behaves consistently and safely.
     */
    private record ScholarshipTier(double threshold, String message) {
        ScholarshipTier {
            if (threshold < 0) {
                throw new IllegalArgumentException("Threshold cannot be negative");
            }
            if (message == null || message.isBlank()) {
                throw new IllegalArgumentException("Message cannot be null or empty");
            }
        }
    }
}

/**
 * Example usage demonstrating Dependency Inversion Principle
 * This class depends on the abstraction ScholarshipCalculatorInterface.
 */
public final class ScholarshipDemo {
    private final ScholarshipCalculatorInterface calculator;

    public ScholarshipDemo(ScholarshipCalculatorInterface calculator) {
        if (calculator == null) throw new IllegalArgumentException("Calculator cannot be null");
        this.calculator = calculator;
    }

    public void showScholarshipStatus(String studentId, Map<String, Double> studentGPAs) {
        double gpa = calculator.getGPA(studentId, studentGPAs);
        String message = calculator.determineScholarshipMessage(gpa);

        System.out.printf("\nStudent ID: %s\nGPA: %.2f\nStatus: %s\n", studentId, gpa, message);
    }

    public static void main(String[] args) {
        Map<String, Double> studentGPAs = Map.of(
            "S001", 3.9,
            "S002", 3.6,
            "S003", 2.9
        );

        ScholarshipCalculatorInterface calculator = new ScholarshipCalculator();
        ScholarshipDemo demo = new ScholarshipDemo(calculator);

        demo.showScholarshipStatus("S001", studentGPAs);
        demo.showScholarshipStatus("S002", studentGPAs);
        demo.showScholarshipStatus("S003", studentGPAs);
    }
}
