package com.mycompany.ums;

import java.util.List;

// // Interface for providing exam schedules for given courses
// public interface ExamScheduleProvider {
//     List<ExamScheduleProvider> getExamSchedule(List<String> courseCodes);
// }

// // Interface for displaying exam schedules
// public interface ExamView {
//     void displayExamSchedule(List<ExamScheduleProvider> schedules);
// }

// The Exam class depends on abstractions to comply with OCP
public class Exam {
    private final EnrollmentService enrollmentService;
    private final ExamScheduleProvider scheduleProvider;
    private final ExamView examView;

    public Exam(EnrollmentService enrollmentService, ExamScheduleProvider scheduleProvider, ExamView examView) {
        if (enrollmentService == null || scheduleProvider == null || examView == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.enrollmentService = enrollmentService;
        this.scheduleProvider = scheduleProvider;
        this.examView = examView;
    }

    public void viewExams(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        List<String> courses = enrollmentService.getEnrolledCourses(studentId);
        // List<ExamScheduleProvider> schedules = scheduleProvider.getExamSchedule(courses);
        // examView.displayExamSchedule(schedules);
    }
}
