package com.mycompany.ums;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Data class for holding FYP details
class FYPDetails {
    private String title;
    private String supervisor;

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }
}

// Interface for input fields (Open for Extension)
interface FYPFieldCollector {
    void collectInput(Scanner scanner, FYPDetails details);
}

// Title input collector
class TitleCollector implements FYPFieldCollector {
    private static final String TITLE_PROMPT = "Enter FYP title: ";

    @Override
    public void collectInput(Scanner scanner, FYPDetails details) {
        String title;
        do {
            System.out.print(TITLE_PROMPT);
            title = scanner.nextLine().trim();
        } while (title.isEmpty());
        details.setTitle(title);
    }
}

// Supervisor input collector
class SupervisorCollector implements FYPFieldCollector {
    private static final String SUPERVISOR_PROMPT = "Enter supervisor name: ";

    @Override
    public void collectInput(Scanner scanner, FYPDetails details) {
        String supervisor;
        do {
            System.out.print(SUPERVISOR_PROMPT);
            supervisor = scanner.nextLine().trim();
        } while (supervisor.isEmpty());
        details.setSupervisor(supervisor);
    }
}

// Main input handler using OCP
public final class FYPInputHandler {
    private final List<FYPFieldCollector> collectors = new ArrayList<>();

    public FYPInputHandler() {
        collectors.add(new TitleCollector());
        collectors.add(new SupervisorCollector());
    }

    public FYPDetails collectFYPDetails(Scanner scanner) {
        FYPDetails details = new FYPDetails();
        for (FYPFieldCollector collector : collectors) {
            collector.collectInput(scanner, details);
        }
        return details;
    }
}
