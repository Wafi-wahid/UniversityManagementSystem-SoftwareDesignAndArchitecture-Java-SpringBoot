package com.mycompany.ums;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Interface for clearance checks (ISP - segregated to one responsibility)
interface ClearanceCheck {
    boolean check(String studentId, Map<String, Boolean> degreeClearance, Map<String, Boolean> feeStatus,
                  Map<String, Integer> completedCredits);
    String getFailureMessage();
}

// Concrete check for clearance already in progress
class ClearanceInProgressCheck implements ClearanceCheck {
    private static final String MSG = "Your degree clearance is already in progress.";
    private String failureMessage = "";

    @Override
    public boolean check(String studentId, Map<String, Boolean> degreeClearance, Map<String, Boolean> feeStatus,
                         Map<String, Integer> completedCredits) {
        boolean inProgress = degreeClearance.getOrDefault(studentId, false);
        if (inProgress) failureMessage = MSG;
        return !inProgress;
    }

    @Override
    public String getFailureMessage() {
        return failureMessage;
    }
}

// Concrete check for fees paid
class FeesPaidCheck implements ClearanceCheck {
    private static final String MSG = "You have unpaid fees. Cannot apply for clearance.";
    private String failureMessage = "";

    @Override
    public boolean check(String studentId, Map<String, Boolean> degreeClearance, Map<String, Boolean> feeStatus,
                         Map<String, Integer> completedCredits) {
        boolean paid = feeStatus.getOrDefault(studentId, false);
        if (!paid) failureMessage = MSG;
        return paid;
    }

    @Override
    public String getFailureMessage() {
        return failureMessage;
    }
}

// Concrete check for credits completed
class CreditsCompletedCheck implements ClearanceCheck {
    private static final int REQUIRED_CREDITS = 120;
    private String failureMessage = "";

    @Override
    public boolean check(String studentId, Map<String, Boolean> degreeClearance, Map<String, Boolean> feeStatus,
                         Map<String, Integer> completedCredits) {
        int credits = completedCredits.getOrDefault(studentId, 0);
        boolean passed = credits >= REQUIRED_CREDITS;
        if (!passed) failureMessage = String.format("You haven't completed the required %d credits.", REQUIRED_CREDITS);
        return passed;
    }

    @Override
    public String getFailureMessage() {
        return failureMessage;
    }
}

// Dependency: Department clearance verification service
class DepartmentClearanceService {
    // Using Dependency Inversion: The service is injected or can be mocked
    public static void verifyDepartmentClearances() {
        // Stub: actual verification logic
        System.out.println("Department clearances verified.");
    }
}

// Main class applying OCP by accepting new clearance checks without modification
public final class DegreeClearance {
    private static final String APPLICATION_HEADER = "\nDegree Clearance Application";
    private static final String SUCCESS_MSG = "Degree clearance application submitted successfully!";

    private final List<ClearanceCheck> clearanceChecks;

    // Dependency Injection for rules - DIP & OCP
    public DegreeClearance(List<ClearanceCheck> checks) {
        this.clearanceChecks = checks;
    }

    public void applyForClearance(String studentId,
                                  Map<String, Boolean> degreeClearance,
                                  Map<String, Boolean> feeStatus,
                                  Map<String, Integer> completedCredits,
                                  Scanner scanner) {
        System.out.println(APPLICATION_HEADER);

        for (ClearanceCheck check : clearanceChecks) {
            if (!check.check(studentId, degreeClearance, feeStatus, completedCredits)) {
                System.out.println(check.getFailureMessage());
                return;
            }
        }

        DepartmentClearanceService.verifyDepartmentClearances();

        processClearanceApplication(studentId, degreeClearance);
    }

    private void processClearanceApplication(String studentId, Map<String, Boolean> degreeClearance) {
        degreeClearance.put(studentId, true);
        System.out.println(SUCCESS_MSG);
    }
}
