package com.mycompany.ums;

import java.util.Scanner;

/**
 * FeedbackValidator class with SOLID applied:
 * - SRP: Har validator ka ek single responsibility hai.
 * - OCP: Naye validator types add kar sakte hain bina existing code change kiye.
 * - LSP: Validators interchangeable hain via Validator<T> interface.
 * - ISP: Client ko sirf required validator interface chahiye.
 * - DIP: High-level validation calls depend on abstractions, not concretions.
 */
public final class FeedbackValidator {

    private FeedbackValidator() {
        // Prevent instantiation
    }

    /**
     * Generic Validator interface for any type T.
     */
    public interface Validator<T> {
        void validate(T input) throws IllegalArgumentException;
    }

    /**
     * Validator for student ID Strings.
     */
    public static class StudentIdValidator implements Validator<String> {
        @Override
        public void validate(String studentId) {
            if (studentId == null || studentId.isBlank()) {
                throw new IllegalArgumentException("Student ID cannot be null or empty");
            }
        }
    }

    /**
     * Validator for generic storage objects (e.g., feedback map).
     */
    public static class StorageValidator implements Validator<Object> {
        @Override
        public void validate(Object storage) {
            if (storage == null) {
                throw new IllegalArgumentException("Feedback storage cannot be null");
            }
        }
    }

    /**
     * Validator for Scanner object.
     */
    public static class ScannerValidator implements Validator<Scanner> {
        @Override
        public void validate(Scanner scanner) {
            if (scanner == null) {
                throw new IllegalArgumentException("Scanner cannot be null");
            }
        }
    }

    // Static convenience methods using the validators above
    public static void validateStudentId(String studentId) {
        new StudentIdValidator().validate(studentId);
    }

    public static void validateStorage(Object storage) {
        new StorageValidator().validate(storage);
    }

    public static void validateScanner(Scanner scanner) {
        new ScannerValidator().validate(scanner);
    }
}
