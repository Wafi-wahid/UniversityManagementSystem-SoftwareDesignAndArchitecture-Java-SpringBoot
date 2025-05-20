package com.mycompany.ums;

/**
 * Interface segregates validation behavior.
 */
interface Validator<T> {
    void validate(T value) throws IllegalArgumentException;
}

/**
 * GPA validation rule interface.
 * Open for extension (OCP), can add new rules without modifying existing code.
 */
interface GPAValidationRule extends Validator<Double> {}

/**
 * Validates GPA is not negative.
 * Follows Liskov Substitution Principle as it can be substituted by any other GPAValidationRule.
 */
class NonNegativeGPARule implements GPAValidationRule {
    @Override
    public void validate(Double gpa) {
        if (gpa == null) {
            throw new IllegalArgumentException("GPA cannot be null");
        }
        if (gpa < 0) {
            throw new IllegalArgumentException("GPA cannot be negative");
        }
    }
}

/**
 * GPAValidator class depends on abstraction of validation rules (DIP).
 */
public class GPAValidator {
    private final Validator<Double> rule;

    public GPAValidator(Validator<Double> rule) {
        if (rule == null) throw new IllegalArgumentException("Validation rule cannot be null");
        this.rule = rule;
    }

    public void validate(double gpa) {
        rule.validate(gpa);
    }

    public static void main(String[] args) {
        // Dependency injection of validation rule
        GPAValidator validator = new GPAValidator(new NonNegativeGPARule());

        // Valid input
        validator.validate(3.5);

        // Uncomment to test invalid input - will throw exception
        // validator.validate(-1.0);
    }
}
