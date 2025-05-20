package com.mycompany.ums;

/**
 * Interface defining contract for providing exam schedule information.
 * 
 * - ISP: Interface sirf exam schedule related methods rakhta hai.
 * - OCP: Naye schedule providers bina interface change kiye add ho sakte hain.
 */
public interface ExamScheduleProvider {
    String getExamDate(String courseCode);
    String getExamTime(String courseCode);
}

/**
 * Default implementation of ExamScheduleProvider that returns fixed exam date and time.
 * 
 * - SRP: Sirf exam schedule provide karta hai.
 * - LSP: ExamScheduleProvider ke jagah use ho sakta hai.
 * - DIP: Client abstraction pe depend karein, concrete class pe nahi.
 */
// public class DefaultExamScheduleProvider implements ExamScheduleProvider {
//     private static final String DEFAULT_EXAM_DATE = "2025-05-20";
//     private static final String DEFAULT_EXAM_TIME = "09:00 AM";

//     public static String getDefaultExamDate() {
//         return DEFAULT_EXAM_DATE;
//     }

//     public static String getDefaultExamTime() {
//         return DEFAULT_EXAM_TIME;
//     }

//     @Override
//     public String getExamDate(String courseCode) {
//         validateCourseCode(courseCode);
//         // Future enhancements: course-specific exam date logic yahan dal sakte hain
//         return DEFAULT_EXAM_DATE;
//     }

//     @Override
//     public String getExamTime(String courseCode) {
//         validateCourseCode(courseCode);
//         // Future enhancements: course-specific exam time logic yahan dal sakte hain
//         return DEFAULT_EXAM_TIME;
//     }

//     @Override
//     public String toString() {
//         return "DefaultExamScheduleProvider []";
//     }

//     private void validateCourseCode(String courseCode) {
//         if (courseCode == null || courseCode.isBlank()) {
//             throw new IllegalArgumentException("Course code cannot be null or empty");
//         }
//     }
// }
