package com.mycompany.ums;

// --- Student class (Domain Model) ---
class Student {
    private boolean duesCleared;
    private boolean active;

    public Student(boolean duesCleared, boolean active) {
        this.duesCleared = duesCleared;
        this.active = active;
    }

    public boolean areDuesCleared() {
        return duesCleared;
    }

    public boolean isActive() {
        return active;
    }
}

// --- Access Decision Strategy (ISP, DIP) ---
interface AccessDecisionStrategy {
    boolean isAccessGranted(Student student);
}

// --- Default Implementation (LSP) ---
class DefaultAccessDecisionMaker implements AccessDecisionStrategy {
    @Override
    public boolean isAccessGranted(Student student) {
        // Only checks dues
        return student.areDuesCleared();
    }
}

// --- Alternative Implementation (OCP, LSP) ---
class ActiveAndDuesClearedDecision implements AccessDecisionStrategy {
    @Override
    public boolean isAccessGranted(Student student) {
        // Requires dues to be cleared and student to be active
        return student.areDuesCleared() && student.isActive();
    }
}

// --- Student Validation Strategy (ISP) ---
interface StudentValidationStrategy {
    void validate(Student student);
}

// --- Default Validator Implementation ---
class DefaultStudentValidator implements StudentValidationStrategy {
    @Override
    public void validate(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        // Add more validation logic if needed
    }
}

// --- Message Strategy (ISP) ---
interface AccessMessageStrategy {
    void printMessage(Student student, boolean granted);
}

// --- Default Messenger Implementation ---
class DefaultAccessMessenger implements AccessMessageStrategy {
    @Override
    public void printMessage(Student student, boolean granted) {
        System.out.println("Access " + (granted ? "granted" : "denied") + " for student.");
    }
}

// --- AccessDecisionMaker class (OCP, DIP) ---
public final class AccessDecisionMaker {
    private final StudentValidationStrategy validator;
    private final AccessDecisionStrategy decisionMaker;
    private final AccessMessageStrategy messenger;

    public AccessDecisionMaker(StudentValidationStrategy validator,
                               AccessDecisionStrategy decisionMaker,
                               AccessMessageStrategy messenger) {
        this.validator = validator;
        this.decisionMaker = decisionMaker;
        this.messenger = messenger;
    }

    public boolean grantAccess(Student student) {
        validator.validate(student);
        boolean granted = decisionMaker.isAccessGranted(student);
        messenger.printMessage(student, granted);
        return granted;
    }

    // --- Example usage ---
    public static void main(String[] args) {
        Student student1 = new Student(true, true);
        Student student2 = new Student(true, false);

        // Using default strategy (only dues check)
        AccessDecisionMaker accessControl1 = new AccessDecisionMaker(
            new DefaultStudentValidator(),
            new DefaultAccessDecisionMaker(),
            new DefaultAccessMessenger()
        );

        accessControl1.grantAccess(student1); // Access granted
        accessControl1.grantAccess(student2); // Access granted

        // Using extended strategy (dues + active check)
        AccessDecisionMaker accessControl2 = new AccessDecisionMaker(
            new DefaultStudentValidator(),
            new ActiveAndDuesClearedDecision(),
            new DefaultAccessMessenger()
        );

        accessControl2.grantAccess(student1); // Access granted
        accessControl2.grantAccess(student2); // Access denied
    }
}
