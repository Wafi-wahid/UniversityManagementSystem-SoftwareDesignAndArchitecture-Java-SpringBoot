package com.mycompany.ums;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class CourseMaterial<EnrollmentProvider> {
    private static final String ACCESS_HEADER = "\nAccess Course Materials";
    private static final String NOT_ENROLLED_MSG = "You are not enrolled in any courses.";
    private static final String ENROLLED_COURSES_HEADER = "Your enrolled courses:";
    private static final String COURSE_PROMPT = "\nEnter course code to access materials: ";
    private static final String NOT_ENROLLED_IN_COURSE_MSG = "You are not enrolled in this course.";
    private static final String NO_MATERIALS_MSG = "No materials available for this course yet.";
    private static final String MATERIALS_HEADER_FORMAT = "\nMaterials for %s:";
    private static final String COURSE_DISPLAY_FORMAT = "- %s";

    private final EnrollmentProvider enrollmentProvider;
    private final CourseMaterial materialsProvider;

    public CourseMaterial(final EnrollmentProvider enrollmentProvider, final CourseMaterial materialsProvider) {
        this.enrollmentProvider = enrollmentProvider;
        this.materialsProvider = materialsProvider;
    }

    public void accessMaterials(final String studentId, final Scanner scanner) {
        System.out.println(ACCESS_HEADER);

        // final List<String> enrolled = ((MapEnrollmentProvider) enrollmentProvider).getEnrolledCourses(studentId);
        // if (enrolled.isEmpty()) {
        //     System.out.println(NOT_ENROLLED_MSG);
        //     return;
        // }

        // displayEnrolledCourses(enrolled);

        // final String courseCode = promptForCourseCode(scanner);

        // if (!enrolled.contains(courseCode)) {
        //     System.out.println(NOT_ENROLLED_IN_COURSE_MSG);
        //     return;
        // }

        // displayCourseMaterials(courseCode);
    }

    private void displayEnrolledCourses(final List<String> enrolledCourses) {
        System.out.println(ENROLLED_COURSES_HEADER);
        enrolledCourses.forEach(course -> System.out.println(String.format(COURSE_DISPLAY_FORMAT, course)));
    }

    private String promptForCourseCode(final Scanner scanner) {
        System.out.print(COURSE_PROMPT);
        return scanner.nextLine().trim();
    }

    private void displayCourseMaterials(final String courseCode) {
        final List<String> materials = materialsProvider.getMaterials(courseCode);
        if (materials.isEmpty()) {
            System.out.println(NO_MATERIALS_MSG);
            return;
        }
        System.out.println(String.format(MATERIALS_HEADER_FORMAT, courseCode));
        materials.forEach(System.out::println);
    }

    private List<String> getMaterials(String courseCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMaterials'");
    }
}

// Concrete implementations remain same but add final fields for immutability:
// class MapEnrollmentProvider implements EnrollmentProvider {
//     private final Map<String, List<String>> enrolledCourses;

//     public MapEnrollmentProvider(final Map<String, List<String>> enrolledCourses) {
//         this.enrolledCourses = enrolledCourses;
//     }

//     @Override
//     public List<String> getEnrolledCourses(final String studentId) {
//         return enrolledCourses.getOrDefault(studentId, Collections.emptyList());
//     }
// }

// class MapCourseMaterialsProvider implements CourseMaterialsProvider {
//     private final Map<String, List<String>> courseMaterials;

//     public MapCourseMaterialsProvider(final Map<String, List<String>> courseMaterials) {
//         this.courseMaterials = courseMaterials;
//     }

//     @Override
//     public List<String> getMaterials(final String courseCode) {
//         return courseMaterials.getOrDefault(courseCode, Collections.emptyList());
//     }

//     public Map<String, List<String>> getCourseMaterials() {
//         return courseMaterials;
//     }
// }
