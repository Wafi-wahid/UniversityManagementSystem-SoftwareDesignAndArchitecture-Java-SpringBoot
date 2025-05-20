package com.mycompany.ums;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a student's timetable.
 * Immutable and encapsulated.
 */
public final class Timetable {
    private final Map<String, String> dayCourseMap;

    public Timetable(Map<String, String> dayCourseMap) {
        this.dayCourseMap = Objects.requireNonNull(dayCourseMap, "Timetable map cannot be null");
    }

    public Map<String, String> getDayCourseMap() {
        return Collections.unmodifiableMap(dayCourseMap);
    }
}

/**
 * Interface segregated for timetable validation.
 */
interface TimetableValidator {
    void validateStudentId(String studentId);
    void validateTimetables(Map<String, Map<String, String>> timetables);
}

/**
 * Concrete implementation of TimetableValidator.
 */
class TimetableValidatorImpl implements TimetableValidator {

    @Override
    public void validateStudentId(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
    }

    @Override
    public void validateTimetables(Map<String, Map<String, String>> timetables) {
        if (timetables == null) {
            throw new IllegalArgumentException("Timetables map cannot be null");
        }
    }
}

/**
 * Interface for displaying timetable (ISP).
 */
interface TimetableDisplay {
    void display(Timetable timetable);
}

/**
 * Concrete class for displaying timetable on console.
 */
class ConsoleTimetableDisplay implements TimetableDisplay {
    private static final String HEADER_FORMAT = "%-10s %-15s %-10s%n";
    private static final String DAY_HEADER = "Day";
    private static final String TIME_HEADER = "Time";
    private static final String COURSE_HEADER = "Course";
    private static final String DEFAULT_TIME_SLOT = "09:00-11:00";
    private static final String NO_TIMETABLE_MSG = "Timetable not available yet.";
    private static final String TIMETABLE_HEADER = "\nYour Weekly Timetable";

    @Override
    public void display(Timetable timetable) {
        System.out.println(TIMETABLE_HEADER);
        Map<String, String> map = timetable.getDayCourseMap();
        if (map.isEmpty()) {
            System.out.println(NO_TIMETABLE_MSG);
            return;
        }
        System.out.printf(HEADER_FORMAT, DAY_HEADER, TIME_HEADER, COURSE_HEADER);
        map.forEach((day, course) -> System.out.printf(HEADER_FORMAT, day, DEFAULT_TIME_SLOT, course));
    }
}

/**
 * High-level interface for timetable service.
 * Depends on abstraction (DIP).
 */
interface TimetableService {
    void viewTimetable(String studentId, Map<String, Map<String, String>> timetables);
}

/**
 * Concrete timetable service that follows DIP and OCP.
 */
class TimetableServiceImpl implements TimetableService {

    private final TimetableValidator validator;
    private final TimetableDisplay display;

    public TimetableServiceImpl(TimetableValidator validator, TimetableDisplay display) {
        this.validator = Objects.requireNonNull(validator, "Validator cannot be null");
        this.display = Objects.requireNonNull(display, "Display cannot be null");
    }

    @Override
    public void viewTimetable(String studentId, Map<String, Map<String, String>> timetables) {
        validator.validateStudentId(studentId);
        validator.validateTimetables(timetables);

        Map<String, String> studentMap = timetables.getOrDefault(studentId, Collections.emptyMap());
        Timetable timetable = new Timetable(studentMap);

        display.display(timetable);
    }
}
