package com.mycompany.ums;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Generic validator interface
interface Validator<T> {
    void validate(T obj) throws IllegalArgumentException;
}

// Concrete validators:

class CourseCodeValidator implements Validator<Assessment> {
    @Override
    public void validate(Assessment assessment) {
        String code = assessment.getCourseCode();
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be empty");
        }
    }
}

class TypeValidator implements Validator<Assessment> {
    private static final List<String> VALID_TYPES = List.of("quiz", "assignment", "exam");

    @Override
    public void validate(Assessment assessment) {
        String type = assessment.getType();
        if (type == null || !VALID_TYPES.contains(type.toLowerCase())) {
            throw new IllegalArgumentException("Invalid assessment type");
        }
    }
}

class DueDateValidator implements Validator<Assessment> {
    @Override
    public void validate(Assessment assessment) {
        LocalDate date = assessment.getDueDate();
        if (date == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
    }
}

class SubmissionValidator implements Validator<Assessment> {
    @Override
    public void validate(Assessment assessment) {
        String submission = assessment.getSubmission();
        if (submission == null || submission.trim().isEmpty()) {
            throw new IllegalArgumentException("Submission text cannot be empty");
        }
    }
}

// Composite validator that runs all validators
public final class AssessmentValidator implements Validator<Assessment> {
    private final List<Validator<Assessment>> validators;

    public AssessmentValidator(List<Validator<Assessment>> validators) {
        this.validators = List.copyOf(validators); // Immutable copy
    }

    @Override
    public void validate(Assessment assessment) {
        for (Validator<Assessment> validator : validators) {
            validator.validate(assessment);
        }
    }
}
