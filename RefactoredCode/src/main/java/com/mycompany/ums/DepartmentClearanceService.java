package com.mycompany.ums;

import java.util.List;
import java.util.Map;

// -------------------------
// DepartmentChecker Interface & Implementations
// -------------------------

interface DepartmentChecker {
    String getDepartmentName();
    boolean isClear();
}

class FinanceDepartmentChecker implements DepartmentChecker {
    @Override
    public String getDepartmentName() {
        return "Finance";
    }
    @Override
    public boolean isClear() {
        // Real logic here
        return true;
    }
}

class AcademicsDepartmentChecker implements DepartmentChecker {
    @Override
    public String getDepartmentName() {
        return "Academics";
    }
    @Override
    public boolean isClear() {
        // Real logic here
        return true;
    }
}

class LibraryDepartmentChecker implements DepartmentChecker {
    @Override
    public String getDepartmentName() {
        return "Library";
    }
    @Override
    public boolean isClear() {
        // Real logic here
        return true;
    }
}

// -------------------------
// DepartmentClearanceService
// -------------------------

final class DepartmentClearanceService {
    private static final String HEADER = "Checking clearance from all departments...";
    private static final String FORMAT = "- %s: %s";

    private final List<DepartmentChecker> departmentCheckers;

    public DepartmentClearanceService(List<DepartmentChecker> departmentCheckers) {
        this.departmentCheckers = departmentCheckers;
    }

    public void verifyDepartmentClearances() {
        System.out.println(HEADER);
        for (DepartmentChecker checker : departmentCheckers) {
            String status = checker.isClear() ? "Clear" : "Pending";
            System.out.println(String.format(FORMAT, checker.getDepartmentName(), status));
        }
    }
}

// -------------------------
// CreditRule Interface & Implementations
// -------------------------

interface CreditRule {
    boolean applies(double gpa);
    int credits();
}

class LowGpaRule implements CreditRule {
    public boolean applies(double gpa) {
        return gpa < 2.0;
    }
    public int credits() {
        return 12;
    }
}

class MidGpaRule implements CreditRule {
    public boolean applies(double gpa) {
        return gpa >= 2.0 && gpa < 3.0;
    }
    public int credits() {
        return 15;
    }
}

class HighGpaRule implements CreditRule {
    public boolean applies(double gpa) {
        return gpa >= 3.0;
    }
    public int credits() {
        return 18;
    }
}

// -------------------------
// CreditDecisionMaker
// -------------------------

final class CreditDecisionMaker {
    private final List<CreditRule> rules;

    public CreditDecisionMaker(List<CreditRule> rules) {
        this.rules = rules;
    }

    public int getMaxCredits(double gpa) {
        for (CreditRule rule : rules) {
            if (rule.applies(gpa)) {
                return rule.credits();
            }
        }
        throw new IllegalArgumentException("Invalid GPA: " + gpa);
    }
}
