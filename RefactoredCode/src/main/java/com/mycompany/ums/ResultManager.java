package com.mycompany.ums;

import java.util.Collections;
import java.util.Map;

/**
 * Interface segregation: Separate interface for result viewing UI.
 */
interface ResultView {
    void showHeader();
    void displayResults(Map<String, Double> results);
    void showNoResultsMessage();
}

/**
 * Concrete implementation of ResultView for console output.
 */
class ConsoleResultView implements ResultView {
    private static final String RESULTS_HEADER = "\nAcademic Results";
    private static final String NO_RESULTS_MSG = "No results available yet.";
    private static final String RESULTS_FORMAT = "%-10s %-5s\n";
    private static final String RESULT_ROW_FORMAT = "%-10s %-5.2f\n";
    private static final String COURSE_HEADER = "Course";
    private static final String GRADE_HEADER = "Grade";

    @Override
    public void showHeader() {
        System.out.println(RESULTS_HEADER);
        System.out.printf(RESULTS_FORMAT, COURSE_HEADER, GRADE_HEADER);
    }

    @Override
    public void displayResults(Map<String, Double> results) {
        results.forEach((course, grade) -> System.out.printf(RESULT_ROW_FORMAT, course, grade));
    }

    @Override
    public void showNoResultsMessage() {
        System.out.println(NO_RESULTS_MSG);
    }
}

/**
 * Validator interface for result input validation (ISP).
 */
interface ResultValidatorInterface {
    void validateInputs(String studentId, Map<String, Map<String, Double>> semesterResults);
}

/**
 * Concrete validator implementation.
 */
class ResultValidator implements ResultValidatorInterface {

    @Override
    public void validateInputs(String studentId, Map<String, Map<String, Double>> semesterResults) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (semesterResults == null) {
            throw new IllegalArgumentException("Results map cannot be null");
        }
    }
}

/**
 * ResultManager demonstrating DIP by depending on abstractions.
 */
public final class ResultManager {
    private final ResultValidatorInterface validator;
    private final ResultView view;

    public ResultManager(ResultValidatorInterface validator, ResultView view) {
        if (validator == null || view == null) {
            throw new IllegalArgumentException("Validator and View cannot be null");
        }
        this.validator = validator;
        this.view = view;
    }

    public void viewResults(String studentId, Map<String, Map<String, Double>> semesterResults) {
        validator.validateInputs(studentId, semesterResults);

        Map<String, Double> studentResults = semesterResults.getOrDefault(studentId, Collections.emptyMap());

        view.showHeader();

        if (studentResults.isEmpty()) {
            view.showNoResultsMessage();
        } else {
            view.displayResults(studentResults);
        }
    }
}
