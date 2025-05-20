package com.mycompany.ums;

// --- Main Class using all strategies (DIP) ---
public class AccessMessenger {
    // --- Example usage ---
    public static void main(String[] args) {
        Student student1 = new Student(true, true);
        Student student2 = new Student(true, false);
       
        // Using strategy that only checks dues
        // AccessControl control1 = new AccessControl(
        //     new DefaultStudentValidator(),
        //     new DefaultAccessDecisionMaker(),
        //     new DefaultAccessMessenger()
        // );
        // control1.grantAccess(student1); // granted
        // control1.grantAccess(student2); // granted

        // // Using strategy that checks both dues and active
        // AccessControl control2 = new AccessControl(
        //     new DefaultStudentValidator(),
        //     new ActiveAndDuesClearedDecision(),
        //     new DefaultAccessMessenger()
        // );
        // control2.grantAccess(student1); // granted
        // control2.grantAccess(student2); // denied
    }

    private StudentValidationStrategy validator = null;
    private AccessDecisionStrategy decisionMaker = null;
    private AccessMessenger messenger = new AccessMessenger();

    public void AccessControl(StudentValidationStrategy validator,
                         AccessDecisionStrategy decisionMaker,
                         AccessMessenger messenger) {
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

    public StudentValidationStrategy getValidator() {
        return validator;
    }

    public AccessDecisionStrategy getDecisionMaker() {
        return decisionMaker;
    }

    public AccessMessenger getMessenger() {
        return messenger;
    }

    private void printMessage(Student student, boolean granted) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printMessage'");
    }

    public void setValidator(StudentValidationStrategy validator) {
        this.validator = validator;
    }

    public void setDecisionMaker(AccessDecisionStrategy decisionMaker) {
        this.decisionMaker = decisionMaker;
    }

    public void setMessenger(AccessMessenger messenger) {
        this.messenger = messenger;
    }
}

// --- Domain Class ---
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

// --- AccessMessenger Interface (ISP, DIP) ---
// interface AccessMessenger {
//     void printMessage(Student student, boolean granted);
// }

// // --- Default Implementation (LSP) ---
// class DefaultAccessMessenger implements AccessMessenger {
//     @Override
//     public void printMessage(Student student, boolean granted) {
//         System.out.println("Access " + (granted ? "granted" : "denied") + " for student.");
//     }
// }

// --- Validator Interface (ISP) ---
interface StudentValidationStrategy {
    void validate(Student student);
}

// --- Validator Implementation (LSP) ---
class DefaultStudentValidator implements StudentValidationStrategy {
    @Override
    public void validate(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
    }
}

// --- Access Decision Strategy (ISP, DIP) ---
interface AccessDecisionStrategy {
    boolean isAccessGranted(Student student);
}

// --- Strategy 1: Only dues check (LSP, OCP) ---
class DefaultAccessDecisionMaker implements AccessDecisionStrategy {
    @Override
    public boolean isAccessGranted(Student student) {
        return student.areDuesCleared();
    }
}

// --- Strategy 2: Dues + Active status (LSP, OCP) ---
class ActiveAndDuesClearedDecision implements AccessDecisionStrategy {
    @Override
    public boolean isAccessGranted(Student student) {
        return student.areDuesCleared() && student.isActive();
    }
}
