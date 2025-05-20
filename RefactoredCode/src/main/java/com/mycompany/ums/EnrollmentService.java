package com.mycompany.ums;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Abstraction for enrollment data source.
 */
interface EnrollmentRepository {
    List<String> getEnrolledCourses(String studentId);
}

/**
 * Implementation of EnrollmentRepository backed by a Map.
 */
class MapEnrollmentRepository implements EnrollmentRepository {
    private final Map<String, List<String>> enrolledCourses;

    public MapEnrollmentRepository(Map<String, List<String>> enrolledCourses) {
        if (enrolledCourses == null) {
            throw new IllegalArgumentException("Enrolled courses map cannot be null");
        }
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public List<String> getEnrolledCourses(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        return enrolledCourses.getOrDefault(studentId, Collections.emptyList());
    }
}

/**
 * Service for managing enrollments, depends on EnrollmentRepository.
 */
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        if (enrollmentRepository == null) {
            throw new IllegalArgumentException("EnrollmentRepository cannot be null");
        }
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<String> getEnrolledCourses(String studentId) {
        // Could add further validation or business logic here if needed
        return enrollmentRepository.getEnrolledCourses(studentId);
    }
}
