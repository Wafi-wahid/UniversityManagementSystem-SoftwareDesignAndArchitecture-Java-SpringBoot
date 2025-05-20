package com.mycompany.ums;

import java.util.Collections;
import java.util.Map;

/**
 * High-level module depends on abstractions for validation and UI.
 */
// 

/**
 * Interface segregates validation behavior.
 */
interface ResultValidatorInterface {
    void validate(String studentId, Map<String, Map<String, Double>> semesterResults);
}

/**
 * Concrete implementation of ResultValidatorInterface.
 */
class ResultValidator implements ResultValidatorInterface {
    @Override
    public void validate(String studentId, Map<String, Map<String, Double>> semesterResults) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (semesterResults == null) {
            throw new IllegalArgumentException("Results map cannot be null");
        }
    }
}

/**
 * Interface segregates UI display behavior.
 */
interface ResultUI {
    void displayResults(String studentId, Map<String, Map<String, Double>> semesterResults);
}

/**
 * Concrete console-based implementation of ResultUI.
 */
class ConsoleResultUI implements ResultUI {
    private static final String RESULTS_HEADER = "\nAcademic Results";
    private static final String NO_RESULTS_MSG = "No results available yet.";
    private static final String RESULTS_FORMAT = "%-10s %-5s\n";
    private static final String RESULT_ROW_FORMAT = "%-10s %-5.2f\n";
    private static final String COURSE_HEADER = "Course";
    private static final String GRADE_HEADER = "Grade";

    @Override
    public void displayResults(String studentId, Map<String, Map<String, Double>> semesterResults) {
        System.out.println(RESULTS_HEADER);
        Map<String, Double> studentResults = semesterResults.getOrDefault(studentId, Collections.emptyMap());

        if (studentResults.isEmpty()) {
            System.out.println(NO_RESULTS_MSG);
            return;
        }

        System.out.printf(RESULTS_FORMAT, COURSE_HEADER, GRADE_HEADER);
        studentResults.forEach((course, grade) -> System.out.printf(RESULT_ROW_FORMAT, course, grade));
    }
}
