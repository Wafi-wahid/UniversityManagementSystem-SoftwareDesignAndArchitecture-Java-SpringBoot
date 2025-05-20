package com.mycompany.ums;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Interface for fetching academic history (ISP).
 */
interface AcademicHistoryFetcher {
    List<String> fetchHistory(String studentId);
}

/**
 * Interface for printing academic history (ISP).
 */
interface AcademicHistoryPrinter {
    void printHistory(List<String> history);
}

/**
 * Abstract base class for history fetchers (OCP, LSP).
 */
abstract class BaseHistoryFetcher implements AcademicHistoryFetcher {
    protected final Map<String, List<String>> academicHistory;

    protected BaseHistoryFetcher(Map<String, List<String>> academicHistory) {
        if (academicHistory == null) throw new IllegalArgumentException("Academic history map cannot be null");
        this.academicHistory = academicHistory;
    }
}

/**
 * Concrete Map-based history fetcher (LSP, DIP).
 */
class MapHistoryFetcher extends BaseHistoryFetcher {

    public MapHistoryFetcher(Map<String, List<String>> academicHistory) {
        super(academicHistory);
    }

    @Override
    public List<String> fetchHistory(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        return academicHistory.getOrDefault(studentId, Collections.emptyList());
    }
}

/**
 * Concrete history printer implementation (LSP, DIP).
 */
class ConsoleHistoryPrinter implements AcademicHistoryPrinter {
    private static final String NO_HISTORY_MESSAGE = "No academic history available.";
    private static final String HISTORY_HEADER = "\nAcademic History";

    @Override
    public void printHistory(List<String> history) {
        System.out.println(HISTORY_HEADER);
        if (history == null || history.isEmpty()) {
            System.out.println(NO_HISTORY_MESSAGE);
        } else {
            history.forEach(System.out::println);
        }
    }
}

/**
 * Client class that depends on abstractions (DIP).
 */
public class HistoryClient {
    private final AcademicHistoryFetcher fetcher;
    private final AcademicHistoryPrinter printer;

    public HistoryClient(AcademicHistoryFetcher fetcher, AcademicHistoryPrinter printer) {
        if (fetcher == null || printer == null) {
            throw new IllegalArgumentException("Fetcher and Printer cannot be null");
        }
        this.fetcher = fetcher;
        this.printer = printer;
    }

    public void displayStudentHistory(String studentId) {
        List<String> history = fetcher.fetchHistory(studentId);
        printer.printHistory(history);
    }

    public static void main(String[] args) {
        Map<String, List<String>> data = Map.of(
            "S001", List.of("Math", "Physics", "Chemistry"),
            "S002", List.of("Biology", "English Literature")
        );

        AcademicHistoryFetcher fetcher = new MapHistoryFetcher(data);
        AcademicHistoryPrinter printer = new ConsoleHistoryPrinter();

        HistoryClient client = new HistoryClient(fetcher, printer);
        client.displayStudentHistory("S001");  // Prints history
        client.displayStudentHistory("S003");  // Prints no history message
    }
}
