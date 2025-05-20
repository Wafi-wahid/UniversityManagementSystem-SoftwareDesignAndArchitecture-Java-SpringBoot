package com.mycompany.ums;

import java.util.Map;
import java.util.Objects;

/**
 * Controller class demonstrating Dependency Inversion Principle.
 * Depends on abstractions for calculation, display, and validation.
 */
public final class ScholarshipValidator {
    public static void main(String[] args) {
        Map<String, Double> studentGPAs = Map.of(
            "S001", 3.9,
            "S002", 3.6,
            "S003", 2.9
        );

        ScholarshipCalculatorInterface calculator = new ScholarshipCalculator();
        ScholarshipDisplayInterface display = new ConsoleScholarshipDisplay();
        ScholarshipValidator validator = new ScholarshipValidator();

    }
    private ScholarshipCalculatorInterface calculator = null;
    private ScholarshipDisplayInterface display = null;

    private ScholarshipValidator validator = new ScholarshipValidator();

    public void Scholarship(ScholarshipCalculatorInterface calculator,
                       ScholarshipDisplayInterface display,
                       ScholarshipValidator validator) {
        this.calculator = Objects.requireNonNull(calculator, "Calculator cannot be null");
        this.display = Objects.requireNonNull(display, "Display cannot be null");
        this.validator = Objects.requireNonNull(validator, "Validator cannot be null");
    }

    public void checkScholarship(String studentId, Map<String, Double> studentGPAs) {
        // validator.validate(studentId);

        display.showHeader();

        double gpa = calculator.getGPA(studentId, studentGPAs);
        display.showGPA(gpa);

        String message = calculator.determineScholarshipMessage(gpa);
        display.showMessage(message);
    }

    // private void validate(String studentId, Map<String studentGPAs) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'validate'");
    // }

    public ScholarshipCalculatorInterface getCalculator() {
        return calculator;
    }

    public ScholarshipDisplayInterface getDisplay() {
        return display;
    }

    public ScholarshipValidator getValidator() {
        return validator;
    }
}

/**
 * Interface for displaying scholarship information.
 * Interface Segregation Principle: Focused on display only.
 */
interface ScholarshipDisplayInterface {
    void showHeader();
    void showGPA(double gpa);
    void showMessage(String message);
}

/**
 * Concrete display implementation for console output.
 * Open for extension (OCP).
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
 * Interface for scholarship GPA calculation and message determination.
 */
interface ScholarshipCalculatorInterface {
    double getGPA(String studentId, Map<String, Double> studentGPAs);
    String determineScholarshipMessage(double gpa);
}

/**
 * Concrete implementation for GPA and scholarship message calculation.
 */
class ScholarshipCalculator implements ScholarshipCalculatorInterface {
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
}

/**
 * Validator class responsible for input validation.
 * Single Responsibility Principle: Only validation logic here.
 */
// class ScholarshipValidator {
//     public void validate(String studentId, Map<String, Double> studentGPAs) {
//         if (studentId == null || studentId.isBlank()) {
//             throw new IllegalArgumentException("Student ID cannot be null or empty");
//         }
//         if (studentGPAs == null) {
//             throw new IllegalArgumentException("GPA map cannot be null");
//         }
//     }
// }
