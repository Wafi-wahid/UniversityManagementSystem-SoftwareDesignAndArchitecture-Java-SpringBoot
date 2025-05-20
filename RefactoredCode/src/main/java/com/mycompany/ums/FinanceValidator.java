package com.mycompany.ums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Interface for fee validation rules.
 * 
 * SOLID applied:
 * - SRP: Each validation rule class has a single responsibility (one validation).
 * - OCP: FinanceValidator can add new rules without modification (open for extension).
 * - ISP: Validation interface is minimal, only one method.
 * - DIP: FinanceValidator depends on abstraction FeeValidationRule, not concrete rules.
 */
interface FeeValidationRule {
    void validate(String studentId, Map<String, Boolean> feeStatus);
}

/**
 * Validates that student ID is not null or empty.
 */
class StudentIdValidationRule implements FeeValidationRule {
    @Override
    public void validate(String studentId, Map<String, Boolean> feeStatus) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
    }
}

/**
 * Validates that fee status map is not null.
 */
class FeeStatusValidationRule implements FeeValidationRule {
    @Override
    public void validate(String studentId, Map<String, Boolean> feeStatus) {
        if (feeStatus == null) {
            throw new IllegalArgumentException("Fee status map cannot be null");
        }
    }
}

/**
 * Composite validator that applies all fee validation rules.
 */
public class FinanceValidator {
    private final List<FeeValidationRule> rules = new ArrayList<>();

    public FinanceValidator() {
        // Default rules
        rules.add(new StudentIdValidationRule());
        rules.add(new FeeStatusValidationRule());
    }

    /**
     * Allows injection of custom rules for extensibility.
     */
    public FinanceValidator(List<FeeValidationRule> customRules) {
        if (customRules != null) {
            rules.addAll(customRules);
        }
    }

    /**
     * Applies all validation rules.
     * Throws exception if any rule fails.
     */
    public void validate(String studentId, Map<String, Boolean> feeStatus) {
        for (FeeValidationRule rule : rules) {
            rule.validate(studentId, feeStatus);
        }
    }
}
