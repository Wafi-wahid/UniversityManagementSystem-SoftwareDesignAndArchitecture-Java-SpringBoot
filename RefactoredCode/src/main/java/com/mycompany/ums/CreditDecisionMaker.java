package com.mycompany.ums;

import java.util.List;

// Interface for a credit rule
interface CreditRule {
    boolean applies(double gpa);
    int credits();
}

// Concrete rule: GPA < 2.0 -> 12 credits
class LowGpaRule implements CreditRule {
    public boolean applies(double gpa) {
        return gpa < 2.0;
    }
    public int credits() {
        return 12;
    }
}

// Concrete rule: 2.0 <= GPA < 3.0 -> 15 credits
class MidGpaRule implements CreditRule {
    public boolean applies(double gpa) {
        return gpa >= 2.0 && gpa < 3.0;
    }
    public int credits() {
        return 15;
    }
}

// Concrete rule: GPA >= 3.0 -> 18 credits
class HighGpaRule implements CreditRule {
    public boolean applies(double gpa) {
        return gpa >= 3.0;
    }
    public int credits() {
        return 18;
    }
}

// Manager class applying the rules with injected dependencies
public class CreditDecisionMaker {
    private final List<CreditRule> rules;

    // Inject rules from outside to comply with DIP and OCP
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

    // Example main method to test the rules
    public static void main(String[] args) {
        List<CreditRule> rules = List.of(
            new LowGpaRule(),
            new MidGpaRule(),
            new HighGpaRule()
        );
        CreditDecisionMaker decisionMaker = new CreditDecisionMaker(rules);

        System.out.println("Max credits for GPA 1.5: " + decisionMaker.getMaxCredits(1.5));
        System.out.println("Max credits for GPA 2.5: " + decisionMaker.getMaxCredits(2.5));
        System.out.println("Max credits for GPA 3.5: " + decisionMaker.getMaxCredits(3.5));
    }
}
