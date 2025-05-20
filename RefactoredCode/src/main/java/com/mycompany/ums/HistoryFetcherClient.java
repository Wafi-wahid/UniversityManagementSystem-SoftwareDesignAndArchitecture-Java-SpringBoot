package com.mycompany.ums;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Interface segregates fetching behavior (ISP).
 */
interface AcademicHistoryFetcher {
    List<String> fetchHistory(String studentId);
}

/**
 * Interface segregates printing behavior (ISP).
 */
interface AcademicHistoryPrinter {
    void printHistory(List<String> history);
}

/**
 * Abstract base class for academic history fetchers.
 * Can be extended to fetch from different data sources (OCP).
 */
abstract class BaseHistoryFetcher implements AcademicHistoryFetcher {
    protected final Map<String, List<String>> academicHistory;

    protected BaseHistoryFetcher(Map<String, List<String>> academicHistory) {
        if (academicHistory == null) throw new IllegalArgumentException("Academic history map cannot be null");
        this.academicHistory = academicHistory;
    }
}

/**
 * Concrete implementation of history fetcher from a Map.
 * Complies with LSP and DIP since it depends on abstractions/interfaces.
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
 * Concrete implementation of history printer.
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
 * Client class depending on abstractions (DIP).
 */
public class HistoryFetcherClient {
    private final AcademicHistoryFetcher fetcher;
    private final AcademicHistoryPrinter printer;

    public HistoryFetcherClient(AcademicHistoryFetcher fetcher, AcademicHistoryPrinter printer) {
        if (fetcher == null || printer == null) {
            throw new IllegalArgumentException("Fetcher and Printer cannot be null");
        }
        this.fetcher = fetcher;
        this.printer = printer;
    }

    public void displayAcademicHistory(String studentId) {
        List<String> history = fetcher.fetchHistory(studentId);
        printer.printHistory(history);
    }

    // Main for demonstration
    public static void main(String[] args) {
        Map<String, List<String>> data = Map.of(
            "S001", List.of("Math", "Physics"),
            "S002", List.of("Biology", "Chemistry")
        );

        AcademicHistoryFetcher fetcher = new MapHistoryFetcher(data);
        AcademicHistoryPrinter printer = new ConsoleHistoryPrinter();

        HistoryFetcherClient client = new HistoryFetcherClient(fetcher, printer);

        client.displayAcademicHistory("S001");
        client.displayAcademicHistory("S003");  // No history message
    }
}
