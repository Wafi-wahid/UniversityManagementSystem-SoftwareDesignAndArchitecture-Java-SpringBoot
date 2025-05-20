package com.mycompany.ums;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Immutable Assessment data class
final class Assessment {
    private final String courseCode;
    private final String type;
    private final LocalDate dueDate;
    private final String submission;

    public Assessment(String courseCode, String type, LocalDate dueDate, String submission) {
        this.courseCode = courseCode;
        this.type = type;
        this.dueDate = dueDate;
        this.submission = submission;
    }

    public String getCourseCode() { return courseCode; }
    public String getType() { return type; }
    public LocalDate getDueDate() { return dueDate; }
    public String getSubmission() { return submission; }
}

// Validator interface (ISP)
interface AssessmentValidator {
    void validate(Assessment assessment);
}

// Default validator implementation (OCP)
class DefaultAssessmentValidator implements AssessmentValidator {
    @Override
    public void validate(Assessment assessment) {
        if (assessment.getCourseCode() == null || assessment.getCourseCode().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be empty.");
        }
        if (!(assessment.getType().equals("quiz") || assessment.getType().equals("assignment") || assessment.getType().equals("exam"))) {
            throw new IllegalArgumentException("Assessment type must be quiz, assignment, or exam.");
        }
        if (assessment.getSubmission() == null || assessment.getSubmission().trim().isEmpty()) {
            throw new IllegalArgumentException("Submission cannot be empty.");
        }
    }
}

// Repository for storing assessments (could be extended to interface for DIP)
class AssessmentRepository {
    public static void storeAssessment(String studentId, Assessment assessment, Map<String, List<Assessment>> studentAssessments) {
        studentAssessments.computeIfAbsent(studentId, k -> new java.util.ArrayList<>()).add(assessment);
    }
}

// Strategy interface for assessment submission (ISP + DIP)
interface AssessmentSubmissionStrategy {
    void submitAssessment(String studentId, Map<String, List<Assessment>> studentAssessments, Scanner scanner);
}

// Text-based submission handler (OCP)
class TextAssessmentInputHandler implements AssessmentSubmissionStrategy {
    private static final String END_MARKER = "END";
    private static final String SUBMISSION_PROMPT = "\nSubmit Assessment";
    private static final String COURSE_CODE_PROMPT = "Enter course code: ";
    private static final String TYPE_PROMPT = "Enter assessment type (quiz/assignment/exam): ";
    private static final String SUBMISSION_TEXT_PROMPT = "Enter submission text (end with 'END' on a new line):";
    private static final String SUCCESS_MESSAGE = "Assessment submitted successfully!";

    private final AssessmentValidator validator;

    public TextAssessmentInputHandler(AssessmentValidator validator) {
        this.validator = validator;
    }

    @Override
    public void submitAssessment(String studentId, Map<String, List<Assessment>> studentAssessments, Scanner scanner) {
        System.out.println(SUBMISSION_PROMPT);

        String courseCode = promptForInput(scanner, COURSE_CODE_PROMPT);
        String type = promptForInput(scanner, TYPE_PROMPT).toLowerCase();
        String submission = collectSubmissionText(scanner);

        Assessment assessment = new Assessment(courseCode, type, LocalDate.now(), submission);

        validator.validate(assessment);

        AssessmentRepository.storeAssessment(studentId, assessment, studentAssessments);

        System.out.println(SUCCESS_MESSAGE);
    }

    private String promptForInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String collectSubmissionText(Scanner scanner) {
        System.out.println(SUBMISSION_TEXT_PROMPT);
        StringBuilder submission = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals(END_MARKER)) {
            submission.append(line).append("\n");
        }
        return submission.toString();
    }
}

// Example usage and test harness
class AssessmentInputHandlerTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, List<Assessment>> assessments = new java.util.HashMap<>();

        AssessmentValidator validator = new DefaultAssessmentValidator();
        AssessmentSubmissionStrategy inputHandler = new TextAssessmentInputHandler(validator);

        inputHandler.submitAssessment("student001", assessments, scanner);

        scanner.close();
    }
}
