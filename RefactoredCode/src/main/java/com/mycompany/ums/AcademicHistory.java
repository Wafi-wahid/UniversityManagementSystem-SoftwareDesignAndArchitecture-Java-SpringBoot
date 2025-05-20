package com.mycompany.ums;

import java.util.*;

// --- Strategy Interfaces (ISP, DIP) ---
interface ValidationStrategy {
    void validate(String studentId, Map<String, List<String>> academicHistory);
}

interface HistoryFetcherStrategy {
    List<String> fetch(String studentId, Map<String, List<String>> academicHistory);
}

interface HistoryPrinterStrategy {
    void print(List<String> history);
}

// --- Default Strategy Implementations (LSP compliant) ---
class DefaultValidation implements ValidationStrategy {
    @Override
    public void validate(String studentId, Map<String, List<String>> academicHistory) {
        if (studentId == null || studentId.isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        if (!academicHistory.containsKey(studentId)) {
            throw new IllegalArgumentException("No academic history found for student ID: " + studentId);
        }
    }
}

class DefaultFetcher implements HistoryFetcherStrategy {
    @Override
    public List<String> fetch(String studentId, Map<String, List<String>> academicHistory) {
        return academicHistory.getOrDefault(studentId, new ArrayList<>());
    }
}

class DefaultPrinter implements HistoryPrinterStrategy {
    @Override
    public void print(List<String> history) {
        if (history.isEmpty()) {
            System.out.println("No academic records found.");
        } else {
            System.out.println("Academic History:");
            for (String record : history) {
                System.out.println("- " + record);
            }
        }
    }
}

// --- AcademicHistory class (Applies OCP, DIP) ---
public final class AcademicHistory {
    private final ValidationStrategy validator;
    private final HistoryFetcherStrategy fetcher;
    private final HistoryPrinterStrategy printer;

    public AcademicHistory(ValidationStrategy validator,
                           HistoryFetcherStrategy fetcher,
                           HistoryPrinterStrategy printer) {
        this.validator = validator;
        this.fetcher = fetcher;
        this.printer = printer;
    }

    public void viewHistory(String studentId, Map<String, List<String>> academicHistory) {
        validator.validate(studentId, academicHistory);
        List<String> history = fetcher.fetch(studentId, academicHistory);
        printer.print(history);
    }

    // --- Example usage ---
    public static void main(String[] args) {
        Map<String, List<String>> academicHistoryMap = new HashMap<>();
        academicHistoryMap.put("S123", Arrays.asList("Math - A", "Science - B+"));
        academicHistoryMap.put("S456", Arrays.asList("English - B", "Physics - A"));

        // Injecting strategies (DIP)
        AcademicHistory historyViewer = new AcademicHistory(
            new DefaultValidation(),
            new DefaultFetcher(),
            new DefaultPrinter()
        );

        historyViewer.viewHistory("S123", academicHistoryMap);
    }
}
