package com.mycompany.ums;

// --- Domain Model ---
class Student {
    private String id;
    private String name;
    private String department;
    private String level;

    public Student(String id, String name, String department, String level) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.level = level;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getLevel() { return level; }
}

// --- Strategy Interfaces (ISP + DIP) ---
interface StudentValidationStrategy {
    void validate(Student student);
}

interface AccessDecisionStrategy {
    boolean isAccessGranted(Student student);
}

interface AccessMessageStrategy {
    void printMessage(Student student, boolean granted);
}

// --- Default Strategy Implementations (LSP compliant) ---
class DefaultStudentValidator implements StudentValidationStrategy {
    @Override
    public void validate(Student student) {
        if (student.getId() == null || student.getId().isEmpty()) {
            throw new IllegalArgumentException("Student ID is required.");
        }
        if (student.getName() == null || student.getName().isEmpty()) {
            throw new IllegalArgumentException("Student name is required.");
        }
    }
}

class DefaultAccessDecisionMaker implements AccessDecisionStrategy {
    @Override
    public boolean isAccessGranted(Student student) {
        // Dummy logic: grant access if level is "Undergraduate"
        return "Undergraduate".equalsIgnoreCase(student.getLevel());
    }
}

class DefaultAccessMessenger implements AccessMessageStrategy {
    @Override
    public void printMessage(Student student, boolean granted) {
        String message = granted ?
            "Access granted to " + student.getName() :
            "Access denied for " + student.getName();
        System.out.println(message);
    }
}

// --- AccessControl Class (OCP + DIP) ---
public final class AccessControl {
    private final StudentValidationStrategy validator;
    private final AccessDecisionStrategy decisionMaker;
    private final AccessMessageStrategy messenger;

    public AccessControl(StudentValidationStrategy validator,
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
        Student student = new Student("S001", "Alice", "CS", "Undergraduate");

        AccessControl accessControl = new AccessControl(
            new DefaultStudentValidator(),
            new DefaultAccessDecisionMaker(),
            new DefaultAccessMessenger()
        );

        boolean access = accessControl.grantAccess(student);
        System.out.println("Access granted? " + access);
    }
}
