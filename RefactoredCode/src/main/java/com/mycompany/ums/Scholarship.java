package com.mycompany.ums;

import java.util.Map;

/** 
 * Interface for validation logic 
 */
interface ScholarshipValidatorInterface {
    void validate(String studentId, Map<String, Double> studentGPAs);
}

/** 
 * Concrete implementation for Scholarship validation 
 */
class ScholarshipValidator implements ScholarshipValidatorInterface {
    @Override
    public void validate(String studentId, Map<String, Double> studentGPAs) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (studentGPAs == null) {
            throw new IllegalArgumentException("Student GPA map cannot be null");
        }
        if (!studentGPAs.containsKey(studentId)) {
            throw new IllegalArgumentException("No GPA record found for student ID: " + studentId);
        }
    }
}

/** 
 * Interface for displaying Scholarship information 
 */
interface ScholarshipDisplayInterface {
    void showHeader();
    void showGPA(double gpa);
    void showMessage(String message);
}

/** 
 * Console implementation of ScholarshipDisplayInterface 
 */
class ScholarshipDisplay implements ScholarshipDisplayInterface {
    private static final String HEADER = "\nScholarship Status";
    
    @Override
    public void showHeader() {
        System.out.println(HEADER);
    }
    
    @Override
    public void showGPA(double gpa) {
        System.out.printf("GPA: %.2f%n", gpa);
    }
    
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}

/** 
 * Interface for scholarship calculation logic 
 */
interface ScholarshipCalculatorInterface {
    double getGPA(String studentId, Map<String, Double> studentGPAs);
    String determineScholarshipMessage(double gpa);
}

/** 
 * Concrete implementation for Scholarship calculation 
 */
class ScholarshipCalculator implements ScholarshipCalculatorInterface {
    @Override
    public double getGPA(String studentId, Map<String, Double> studentGPAs) {
        return studentGPAs.getOrDefault(studentId, 0.0);
    }

    @Override
    public String determineScholarshipMessage(double gpa) {
        if (gpa >= 3.5) {
            return "Congratulations! You qualify for the scholarship.";
        } else if (gpa >= 3.0) {
            return "You are on the waiting list for the scholarship.";
        } else {
            return "You do not qualify for the scholarship.";
        }
    }
}

/** 
 * High-level module: Scholarship manager depends on abstractions, not concretions
 */
public final class Scholarship {
    private final ScholarshipValidatorInterface validator;
    private final ScholarshipDisplayInterface display;
    private final ScholarshipCalculatorInterface calculator;

    public Scholarship(ScholarshipValidatorInterface validator,
                       ScholarshipDisplayInterface display,
                       ScholarshipCalculatorInterface calculator) {
        if (validator == null || display == null || calculator == null) {
            throw new IllegalArgumentException("Components cannot be null");
        }
        this.validator = validator;
        this.display = display;
        this.calculator = calculator;
    }

   

    public void checkScholarship(String studentId, Map<String, Double> studentGPAs) {
        validator.validate(studentId, studentGPAs);

        display.showHeader();

        double gpa = calculator.getGPA(studentId, studentGPAs);
        display.showGPA(gpa);

        String message = calculator.determineScholarshipMessage(gpa);
        display.showMessage(message);
    }

    // Example usage
    public static void main(String[] args) {
        Map<String, Double> gpas = Map.of(
            "S001", 3.7,
            "S002", 3.2,
            "S003", 2.8
        );

        Scholarship scholarship = new Scholarship(
            new ScholarshipValidator(),
            new ScholarshipDisplay(),
            new ScholarshipCalculator()
        );

        scholarship.checkScholarship("S001", gpas);
        scholarship.checkScholarship("S002", gpas);
        scholarship.checkScholarship("S003", gpas);
    }
}
