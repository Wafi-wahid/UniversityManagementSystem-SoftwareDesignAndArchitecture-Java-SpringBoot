package com.mycompany.ums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * FeedbackService handles feedback submission logic.
 * 
 * - SRP: Sirf feedback submit aur store ka kaam karta hai.
 * - DIP: Validation and input handled by abstractions (interfaces).
 * - OCP: Naye validators/input helpers easily extend kar sakte hain.
 */
public final class FeedbackService {

    private final FeedbackValidator validator;
    private final InputHelper inputHelper;

    public FeedbackService(FeedbackValidator validator, InputHelper inputHelper) {
        this.validator = validator;
        this.inputHelper = inputHelper;
    }

    public void submitFeedback(String studentId, Map<String, List<String>> feedbackMap, Scanner scanner) {
        validator.validateStudentId(studentId);
        validator.validateFeedbackMap(feedbackMap);
        validator.validateScanner(scanner);

        System.out.println("\nSubmit Feedback");

        String courseCode = inputHelper.promptForNonEmptyInput(scanner, "Enter course code: ");
        String feedbackText = inputHelper.promptForNonEmptyInput(scanner, "Enter your feedback: ");

        storeFeedback(studentId, courseCode, feedbackText, feedbackMap);

        System.out.println("Thank you for your feedback!");
    }

    private void storeFeedback(String studentId, String courseCode, String feedbackText,
                               Map<String, List<String>> feedbackMap) {
        String formattedFeedback = String.format("%s: %s", courseCode, feedbackText);
        feedbackMap.computeIfAbsent(studentId, k -> new ArrayList<>()).add(formattedFeedback);
    }
}

/**
 * Interface for feedback validation.
 * Interface Segregation Principle: Separate interface for validation.
 */
interface FeedbackValidator {
    void validateStudentId(String studentId);
    void validateFeedbackMap(Map<String, List<String>> feedbackMap);
    void validateScanner(Scanner scanner);
}

/**
 * Concrete implementation of FeedbackValidator.
 */
class DefaultFeedbackValidator implements FeedbackValidator {
    @Override
    public void validateStudentId(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
    }

    @Override
    public void validateFeedbackMap(Map<String, List<String>> feedbackMap) {
        if (feedbackMap == null) {
            throw new IllegalArgumentException("Feedback map cannot be null");
        }
    }

    @Override
    public void validateScanner(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("Scanner cannot be null");
        }
    }
}

/**
 * Interface for input helpers.
 */
interface InputHelper {
    String promptForNonEmptyInput(Scanner scanner, String prompt);
}

/**
 * Default implementation of InputHelper.
 */
class ConsoleInputHelper implements InputHelper {
    @Override
    public String promptForNonEmptyInput(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }
}
