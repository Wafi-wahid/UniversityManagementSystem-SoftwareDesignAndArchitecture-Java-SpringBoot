package com.mycompany.ums;

// --- Student class for context ---
class Student {
    private String name;
    private double gpa;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }
}

// --- GPA Validation Strategy (ISP) ---
interface GPAValidationStrategy {
    void validate(double gpa);
}

// --- Credit Decision Strategy (ISP) ---
interface CreditDecisionStrategy {
    int getMaxCredits(double gpa);
}

// --- OCP: Easily extensible validator ---
class DefaultGPAValidator implements GPAValidationStrategy {
    @Override
    public void validate(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("Invalid GPA: " + gpa);
        }
    }
}

// --- OCP: Easily extensible credit decision logic ---
class DefaultCreditDecisionMaker implements CreditDecisionStrategy {
    @Override
    public int getMaxCredits(double gpa) {
        if (gpa >= 3.5) return 24;
        else if (gpa >= 3.0) return 21;
        else if (gpa >= 2.5) return 18;
        else return 15;
    }
}

// --- DIP: Advisor depends on abstractions, not implementations ---
class Advisor {
    private final GPAValidationStrategy validator;
    private final CreditDecisionStrategy decisionMaker;

    public Advisor(GPAValidationStrategy validator, CreditDecisionStrategy decisionMaker) {
        this.validator = validator;
        this.decisionMaker = decisionMaker;
    }

    public int calculateMaxCredits(Student student) {
        validator.validate(student.getGpa());
        return decisionMaker.getMaxCredits(student.getGpa());
    }
}

// --- Example usage ---
public class AdvisorApp {
    public static void main(String[] args) {
        Student student1 = new Student("Ali", 3.6);
        Student student2 = new Student("Sara", 2.8);

        Advisor advisor = new Advisor(new DefaultGPAValidator(), new DefaultCreditDecisionMaker());

        System.out.println(student1.getName() + " max credits: " + advisor.calculateMaxCredits(student1));
        System.out.println(student2.getName() + " max credits: " + advisor.calculateMaxCredits(student2));
    }
}
