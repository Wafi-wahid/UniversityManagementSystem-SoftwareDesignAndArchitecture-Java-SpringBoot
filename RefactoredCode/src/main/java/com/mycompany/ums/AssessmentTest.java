package com.mycompany.ums;

import java.time.LocalDate;
import java.util.*;

// Assessment data class
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

// Strategy interface
interface AssessmentSubmissionStrategy {
    void submit(String studentId, AssessmentRepository repository, Scanner scanner);
}

// Repository class encapsulating the assessments storage (DIP)
class AssessmentRepository {
    private final Map<String, List<Assessment>> assessments = new HashMap<>();

    public void addAssessment(String studentId, Assessment assessment) {
        assessments.computeIfAbsent(studentId, k -> new ArrayList<>()).add(assessment);
    }

    public Map<String, List<Assessment>> getAllAssessments() {
        return Collections.unmodifiableMap(assessments);
    }
}

// Text submission strategy
class TextAssessmentSubmission implements AssessmentSubmissionStrategy {
    @Override
    public void submit(String studentId, AssessmentRepository repository, Scanner scanner) {
        System.out.println("Text Submission for: " + studentId);
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter type: ");
        String type = scanner.nextLine();
        System.out.print("Enter submission text: ");
        String submission = scanner.nextLine();

        Assessment assessment = new Assessment(courseCode, type, LocalDate.now(), submission);
        repository.addAssessment(studentId, assessment);

        System.out.println("Text assessment submitted.");
    }
}

// File upload submission strategy
class FileUploadAssessmentSubmission implements AssessmentSubmissionStrategy {
    @Override
    public void submit(String studentId, AssessmentRepository repository, Scanner scanner) {
        System.out.println("File Submission for: " + studentId);
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter type: ");
        String type = scanner.nextLine();
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();

        Assessment assessment = new Assessment(courseCode, type, LocalDate.now(), filePath);
        repository.addAssessment(studentId, assessment);

        System.out.println("File assessment submitted.");
    }
}

// Handler depends on abstraction
class AssessmentSubmissionHandler {
    private final AssessmentSubmissionStrategy strategy;

    public AssessmentSubmissionHandler(AssessmentSubmissionStrategy strategy) {
        this.strategy = strategy;
    }

    public void submit(String studentId, AssessmentRepository repository, Scanner scanner) {
        strategy.submit(studentId, repository, scanner);
    }
}

// Main example usage
public class AssessmentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AssessmentRepository repository = new AssessmentRepository();

        AssessmentSubmissionHandler handler = new AssessmentSubmissionHandler(new TextAssessmentSubmission());
        handler.submit("student123", repository, scanner);

        handler = new AssessmentSubmissionHandler(new FileUploadAssessmentSubmission());
        handler.submit("student123", repository, scanner);

        scanner.close();

        System.out.println("All submissions: " + repository.getAllAssessments());
    }
}
