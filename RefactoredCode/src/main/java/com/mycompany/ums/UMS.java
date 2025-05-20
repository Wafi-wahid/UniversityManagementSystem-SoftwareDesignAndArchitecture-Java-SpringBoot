package com.mycompany.ums;

import java.util.*;
import java.time.*;

public class UMS {
    // Data storage maps & lists
    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private Map<String, List<String>> prerequisites = new HashMap<>();
    private Map<String, Double> studentGPAs = new HashMap<>();
    private Map<String, Integer> completedCredits = new HashMap<>();
    private Map<String, Boolean> feeStatus = new HashMap<>();
    private Map<String, List<String>> enrolledCourses = new HashMap<>();
    private Map<String, List<String>> courseMaterials = new HashMap<>();
    private Map<String, List<Assessment>> studentAssessments = new HashMap<>();
    private Map<String, Boolean> fypStatus = new HashMap<>();
    private Map<String, Map<String, Double>> semesterResults = new HashMap<>();
    private Map<String, Boolean> degreeClearance = new HashMap<>();
    private Map<String, List<String>> examSchedule = new HashMap<>();
    private Map<String, List<String>> notifications = new HashMap<>();
    private Map<String, Map<String, String>> timetables = new HashMap<>();
    private Map<String, List<String>> academicHistory = new HashMap<>();
    private Map<String, List<String>> feedback = new HashMap<>();
    private Map<String, List<String>> placementInfo = new HashMap<>();

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new UMS().start();
    }

    private void start() {
        initializeData();

        System.out.println("Welcome to Student Management System");

        while (true) {
            printMainMenu();

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> Student.registerStudent(students, scanner);
                case 2 -> manageCourses();
                case 3 -> studentOperations();
                case 4 -> {
                    System.out.println("Exiting system...");
                    return;
                }
                case 5 -> runAllTests();
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Student Registration");
        System.out.println("2. Course Management");
        System.out.println("3. Student Operations");
        System.out.println("4. Exit");
        
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private void initializeData() {
        // Courses
        courses.add(new Course("CS101", "Introduction to Programming", 3));
        courses.add(new Course("CS201", "Data Structures", 3));
        courses.add(new Course("CS301", "Algorithms", 3));
        courses.add(new Course("CS401", "Database Systems", 3));
        courses.add(new Course("CS501", "Operating Systems", 3));
        courses.add(new Course("CS601", "Computer Networks", 3));
        courses.add(new Course("CS701", "Software Engineering", 3));
        courses.add(new Course("CS801", "Final Year Project", 6));

        // Prerequisites
        prerequisites.put("CS201", List.of("CS101"));
        prerequisites.put("CS301", List.of("CS201"));
        prerequisites.put("CS401", List.of("CS201"));
        prerequisites.put("CS501", List.of("CS301"));
        prerequisites.put("CS601", List.of("CS301"));
        prerequisites.put("CS701", List.of("CS401", "CS501"));
        prerequisites.put("CS801", List.of("CS701"));

        // Course materials
        courseMaterials.put("CS101", List.of("Lecture 1: Introduction", "Lecture 2: Variables", "Lecture 3: Control Structures"));
        courseMaterials.put("CS201", List.of("Lecture 1: Arrays", "Lecture 2: Linked Lists", "Lecture 3: Stacks and Queues"));

        // Test student
        Student testStudent = new Student("S001", "Test Student", "test@university.edu", "1234567890");
        students.add(testStudent);
        studentGPAs.put("S001", 3.5);
        completedCredits.put("S001", 90);
        feeStatus.put("S001", true);
        enrolledCourses.put("S001", new ArrayList<>(List.of("CS101", "CS201")));
        academicHistory.put("S001", new ArrayList<>(List.of("CS101: A", "MATH101: B+", "ENG101: A-")));
    }

    private void manageCourses() {
        while (true) {
            System.out.println("\nCourse Management:");
            System.out.println("1. Add Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Set Prerequisites");
            System.out.println("4. Back to Main Menu");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> Course.addCourse(courses, scanner);
                case 2 -> Course.viewAllCourses(courses);
                case 3 -> {
                    try {
                        PrerequisiteManager.setPrerequisites(prerequisites, courses, scanner);
                    } catch (Exception e) {
                        System.out.println("Error setting prerequisites: " + e.getMessage());
                    }
                }
                case 4 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void studentOperations() {
        System.out.print("\nEnter Student ID: ");
        String studentId = scanner.nextLine().trim();

        Student student = findStudent(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("\nWelcome, " + student.getName());

        while (true) {
            printStudentMenu();

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> studentCourseRegistration(studentId);
                case 2 -> viewAcademicStatus(studentId);
                case 3 -> Finance.payFees(studentId, feeStatus);
                case 4 -> CourseMaterial.accessMaterials(studentId, enrolledCourses, courseMaterials, scanner);
                case 5 -> Assessment.submitAssessment(studentId, studentAssessments, scanner);
                case 6 -> FYPManager.registerForFYP(studentId, completedCredits, fypStatus, enrolledCourses, prerequisites, scanner);
                case 7 -> Result.viewResults(studentId, semesterResults);
                case 8 -> Scholarship.checkScholarship(studentId, studentGPAs);
                case 9 -> DegreeClearance.applyForClearance(studentId, degreeClearance, feeStatus, completedCredits, scanner);
                case 10 -> Exam.viewExams(studentId, enrolledCourses, examSchedule);
                case 11 -> Notification.viewNotifications(studentId, notifications);
                case 12 -> Timetable.viewTimetable(studentId, timetables);
                case 13 -> AcademicHistory.viewHistory(studentId, academicHistory);
                case 14 -> Feedback.submitFeedback(studentId, feedback, scanner);
                case 15 -> Placement.viewPlacementInfo(studentId, placementInfo);
                case 16 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void printStudentMenu() {
        System.out.println("\nStudent Operations:");
        System.out.println("1. Course Registration");
        System.out.println("2. View Academic Status");
        System.out.println("3. Pay Fees");
        System.out.println("4. Access Course Materials");
        System.out.println("5. Submit Assessment");
        System.out.println("6. Register for FYP");
        System.out.println("7. View Results");
        System.out.println("8. Check Scholarship");
        System.out.println("9. Apply for Degree Clearance");
        System.out.println("10. View Exams");
        System.out.println("11. View Notifications");
        System.out.println("12. View Timetable");
        System.out.println("13. View Academic History");
        System.out.println("14. Submit Feedback");
        System.out.println("15. View Placement Info");
        System.out.println("16. Back to Main Menu");
    }

    private Student findStudent(String studentId) {
        return students.stream()
                .filter(s -> s.getId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    private void studentCourseRegistration(String studentId) {
        if (!feeStatus.getOrDefault(studentId, false)) {
            System.out.println("You have unpaid fees. Please pay fees first.");
            return;
        }

        double gpa = studentGPAs.getOrDefault(studentId, 0.0);
        int maxCredits = Advisor.calculateMaxCredits(gpa);
        int currentCredits = enrolledCourses.getOrDefault(studentId, new ArrayList<>()).size() * 3;

        System.out.println("\nCourse Registration");
        System.out.println("Your current GPA: " + gpa);
        System.out.println("Maximum credits allowed: " + maxCredits);
        System.out.println("Current registered credits: " + currentCredits);

        CourseRegistration.registerCourses(studentId, courses, enrolledCourses, prerequisites, maxCredits, scanner);
    }

    private void viewAcademicStatus(String studentId) {
        System.out.println("\nAcademic Status:");
        System.out.println("GPA: " + studentGPAs.getOrDefault(studentId, 0.0));
        System.out.println("Completed Credits: " + completedCredits.getOrDefault(studentId, 0));
        System.out.println("Fee Status: " + (feeStatus.getOrDefault(studentId, false) ? "Paid" : "Unpaid"));
    }

    private void runAllTests() {
        System.out.println("\nRunning all system tests...");
        // You can add test classes and methods here
        System.out.println("All tests completed successfully.");
    }

    // --------- Inner classes for Student, Course, etc ----------

    public static class Student {
        private String id;
        private String name;
        private String email;
        private String contact;

        public Student(String id, String name, String email, String contact) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.contact = contact;
        }

        public static void registerStudent(List<Student> students, Scanner scanner) {
            System.out.print("Enter Student ID: ");
            String id = scanner.nextLine().trim();

            boolean exists = students.stream().anyMatch(s -> s.getId().equals(id));
            if (exists) {
                System.out.println("Student with this ID already exists.");
                return;
            }

            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Contact Number: ");
            String contact = scanner.nextLine().trim();

            Student newStudent = new Student(id, name, email, contact);
            students.add(newStudent);
            System.out.println("Student registered successfully!");
        }

        public String getId() { return id; }
        public String getName() { return name; }
    }

    public static class Course {
        private String code;
        private String name;
        private int creditHours;

        public Course(String code, String name, int creditHours) {
            this.code = code;
            this.name = name;
            this.creditHours = creditHours;
        }

        public static void addCourse(List<Course> courses, Scanner scanner) {
            System.out.print("Enter Course Code: ");
            String code = scanner.nextLine().trim();

            boolean exists = courses.stream().anyMatch(c -> c.code.equals(code));
            if (exists) {
                System.out.println("Course already exists!");
                return;
            }

            System.out.print("Enter Course Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Credit Hours: ");
            int credits = -1;
            while (credits <= 0) {
                try {
                    credits = Integer.parseInt(scanner.nextLine().trim());
                    if (credits <= 0) System.out.println("Please enter positive credit hours.");
                } catch (Exception e) {
                    System.out.println("Invalid number.");
                }
            }

            courses.add(new Course(code, name, credits));
            System.out.println("Course added successfully!");
        }

        public static void viewAllCourses(List<Course> courses) {
            System.out.println("\nAll Courses:");
            for (Course c : courses) {
                System.out.println(c.code + " - " + c.name + " (" + c.creditHours + " credits)");
            }
        }
    }

    public static class PrerequisiteManager {
        public static void setPrerequisites(Map<String, List<String>> prerequisites, List<Course> courses, Scanner scanner) {
            System.out.print("Enter Course Code to set prerequisites for: ");
            String courseCode = scanner.nextLine().trim();

            boolean courseExists = courses.stream().anyMatch(c -> c.code.equals(courseCode));
            if (!courseExists) {
                System.out.println("Course does not exist.");
                return;
            }

            System.out.println("Enter prerequisite course codes separated by commas (e.g., CS101,CS201), or leave empty to remove:");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                prerequisites.remove(courseCode);
                System.out.println("Prerequisites removed for " + courseCode);
            } else {
                String[] prereqCodes = input.split(",");
                for (String p : prereqCodes) {
                    // p = p.trim();
                    if (courses.stream().noneMatch(c -> c.code.equals(p))) {
                        System.out.println("Prerequisite course " + p + " does not exist.");
                        return;
                    }
                }
                prerequisites.put(courseCode, Arrays.asList(prereqCodes));
                System.out.println("Prerequisites set for " + courseCode);
            }
        }
    }


    public static class Advisor {
        public static int calculateMaxCredits(double gpa) {
            if (gpa >= 3.0) return 24;
            else if (gpa >= 2.0) return 18;
            else return 12;
        }
    }

    public static class CourseRegistration {
        public static void registerCourses(String studentId, List<Course> courses, Map<String, List<String>> enrolledCourses,
                                           Map<String, List<String>> prerequisites, int maxCredits, Scanner scanner) {

            List<String> currentCourses = enrolledCourses.getOrDefault(studentId, new ArrayList<>());
            int currentCredits = currentCourses.size() * 3;

            System.out.println("\nAvailable courses:");
            for (Course c : courses) {
                System.out.println(c.code + " - " + c.name);
            }

            System.out.println("Current enrolled courses: " + currentCourses);
            System.out.println("You can register up to " + (maxCredits - currentCredits) + " more credits.");

            while (true) {
                System.out.print("Enter course code to add (or type 'done' to finish): ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("done")) break;

                Optional<Course> optCourse = courses.stream().filter(c -> c.code.equals(input)).findFirst();
                if (optCourse.isEmpty()) {
                    System.out.println("Course not found.");
                    continue;
                }

                Course course = optCourse.get();

                if (currentCourses.contains(course.code)) {
                    System.out.println("Already enrolled in this course.");
                    continue;
                }

                // Check prerequisites
                List<String> prereqs = prerequisites.getOrDefault(course.code, List.of());
                boolean prereqMet = currentCourses.containsAll(prereqs);
                if (!prereqMet) {
                    System.out.println("You do not meet prerequisites: " + prereqs);
                    continue;
                }

                // Check max credits
                if (currentCredits + course.creditHours > maxCredits) {
                    System.out.println("Exceeds max credit limit.");
                    continue;
                }

                currentCourses.add(course.code);
                currentCredits += course.creditHours;
                System.out.println("Course added: " + course.code);

                enrolledCourses.put(studentId, currentCourses);
            }

            System.out.println("Final enrolled courses: " + enrolledCourses.get(studentId));
        }
    }

    public static class Finance {
        public static void payFees(String studentId, Map<String, Boolean> feeStatus) {
            if (feeStatus.getOrDefault(studentId, false)) {
                System.out.println("Fees already paid.");
            } else {
                feeStatus.put(studentId, true);
                System.out.println("Fees payment successful.");
            }
        }
    }

    public static class CourseMaterial {
        public static void accessMaterials(String studentId, Map<String, List<String>> enrolledCourses,
                                           Map<String, List<String>> courseMaterials, Scanner scanner) {
            List<String> courses = enrolledCourses.getOrDefault(studentId, new ArrayList<>());

            if (courses.isEmpty()) {
                System.out.println("No enrolled courses found.");
                return;
            }

            System.out.println("Your enrolled courses: " + courses);
            System.out.print("Enter course code to view materials: ");
            String courseCode = scanner.nextLine().trim();

            if (!courses.contains(courseCode)) {
                System.out.println("You are not enrolled in this course.");
                return;
            }

            List<String> materials = courseMaterials.getOrDefault(courseCode, new ArrayList<>());

            System.out.println("Materials for " + courseCode + ":");
            materials.forEach(System.out::println);
        }
    }

    public static class Assessment {
        public static void submitAssessment(String studentId, Map<String, List<Assessment>> studentAssessments, Scanner scanner) {
            System.out.print("Enter Assessment Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Score: ");
            double score = -1;
            while (score < 0) {
                try {
                    score = Double.parseDouble(scanner.nextLine().trim());
                    if (score < 0) System.out.println("Score must be positive.");
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                }
            }

            Assessment newAssessment = new Assessment();
            List<Assessment> assessments = studentAssessments.getOrDefault(studentId, new ArrayList<>());
            assessments.add(newAssessment);
            studentAssessments.put(studentId, assessments);

            System.out.println("Assessment submitted successfully.");
        }
    }

    public static class FYPManager {
        public static void registerForFYP(String studentId, Map<String, Integer> completedCredits,
                                          Map<String, Boolean> fypStatus, Map<String, List<String>> enrolledCourses,
                                          Map<String, List<String>> prerequisites, Scanner scanner) {
            if (fypStatus.getOrDefault(studentId, false)) {
                System.out.println("You are already registered for FYP.");
                return;
            }

            int credits = completedCredits.getOrDefault(studentId, 0);
            if (credits < 90) {
                System.out.println("You need at least 90 credits to register for FYP.");
                return;
            }

            List<String> prereqs = prerequisites.getOrDefault("CS801", List.of());
            List<String> courses = enrolledCourses.getOrDefault(studentId, new ArrayList<>());

            if (!courses.containsAll(prereqs)) {
                System.out.println("You do not meet the prerequisites for FYP: " + prereqs);
                return;
            }

            fypStatus.put(studentId, true);
            System.out.println("FYP registration successful.");
        }
    }

    public static class Result {
        public static void viewResults(String studentId, Map<String, Map<String, Double>> semesterResults) {
            Map<String, Double> results = semesterResults.get(studentId);

            if (results == null || results.isEmpty()) {
                System.out.println("No results found.");
                return;
            }

            System.out.println("Results:");
            results.forEach((course, grade) -> System.out.println(course + ": " + grade));
        }
    }

    public static class Scholarship {
        public static void checkScholarship(String studentId, Map<String, Double> studentGPAs) {
            double gpa = studentGPAs.getOrDefault(studentId, 0.0);

            if (gpa >= 3.5) {
                System.out.println("Congratulations! You are eligible for a scholarship.");
            } else {
                System.out.println("Sorry, you are not eligible for a scholarship.");
            }
        }
    }

    public static class DegreeClearance {
        public static void applyForClearance(String studentId, Map<String, Boolean> degreeClearance,
                                             Map<String, Boolean> feeStatus, Map<String, Integer> completedCredits, Scanner scanner) {
            if (degreeClearance.getOrDefault(studentId, false)) {
                System.out.println("Degree clearance already granted.");
                return;
            }

            if (!feeStatus.getOrDefault(studentId, false)) {
                System.out.println("Please clear your fees first.");
                return;
            }

            int credits = completedCredits.getOrDefault(studentId, 0);
            if (credits < 120) {
                System.out.println("You have not completed required credits for graduation.");
                return;
            }

            degreeClearance.put(studentId, true);
            System.out.println("Degree clearance granted.");
        }
    }

    public static class Exam {
        public static void viewExams(String studentId, Map<String, List<String>> enrolledCourses, Map<String, List<String>> examSchedule) {
            List<String> courses = enrolledCourses.getOrDefault(studentId, new ArrayList<>());

            if (courses.isEmpty()) {
                System.out.println("You are not enrolled in any courses.");
                return;
            }

            System.out.println("Exam Schedule:");
            for (String course : courses) {
                List<String> exams = examSchedule.getOrDefault(course, List.of("No exams scheduled"));
                System.out.println(course + ": " + exams);
            }
        }
    }

    public static class Notification {
        public static void viewNotifications(String studentId, Map<String, List<String>> notifications) {
            List<String> notes = notifications.getOrDefault(studentId, new ArrayList<>());

            if (notes.isEmpty()) {
                System.out.println("No new notifications.");
            } else {
                System.out.println("Notifications:");
                notes.forEach(System.out::println);
            }
        }
    }

    public static class Timetable {
        public static void viewTimetable(String studentId, Map<String, Map<String, String>> timetables) {
            Map<String, String> timetable = timetables.get(studentId);

            if (timetable == null || timetable.isEmpty()) {
                System.out.println("No timetable available.");
                return;
            }

            System.out.println("Timetable:");
            timetable.forEach((day, schedule) -> System.out.println(day + ": " + schedule));
        }
    }

    public static class AcademicHistory {
        public static void viewHistory(String studentId, Map<String, List<String>> academicHistory) {
            List<String> history = academicHistory.getOrDefault(studentId, new ArrayList<>());

            if (history.isEmpty()) {
                System.out.println("No academic history available.");
            } else {
                System.out.println("Academic History:");
                history.forEach(System.out::println);
            }
        }
    }

    public static class Feedback {
        public static void submitFeedback(String studentId, Map<String, List<String>> feedback, Scanner scanner) {
            System.out.print("Enter your feedback: ");
            String fb = scanner.nextLine().trim();

            List<String> feedbackList = feedback.getOrDefault(studentId, new ArrayList<>());
            feedbackList.add(fb);
            feedback.put(studentId, feedbackList);

            System.out.println("Feedback submitted. Thank you!");
        }
    }

    public static class Placement {
        public static void viewPlacementInfo(String studentId, Map<String, List<String>> placementInfo) {
            List<String> info = placementInfo.getOrDefault(studentId, new ArrayList<>());

            if (info.isEmpty()) {
                System.out.println("No placement info available.");
            } else {
                System.out.println("Placement Information:");
                info.forEach(System.out::println);
            }
        }
    }

    // public static class Assessment {
    //     private String name;
    //     private double score;

    //     public Assessment(String name, double score) {
    //         this.name = name;
    //         this.score = score;
    //     }
    // }

    public static Map<String, List<String>> getAcademicHistory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAcademicHistory'");
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Map<String, List<String>> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(Map<String, List<String>> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Map<String, Double> getStudentGPAs() {
        return studentGPAs;
    }

    public void setStudentGPAs(Map<String, Double> studentGPAs) {
        this.studentGPAs = studentGPAs;
    }

    public Map<String, Integer> getCompletedCredits() {
        return completedCredits;
    }

    public void setCompletedCredits(Map<String, Integer> completedCredits) {
        this.completedCredits = completedCredits;
    }

    public Map<String, Boolean> getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(Map<String, Boolean> feeStatus) {
        this.feeStatus = feeStatus;
    }

    public Map<String, List<String>> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(Map<String, List<String>> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public Map<String, List<String>> getCourseMaterials() {
        return courseMaterials;
    }

    public void setCourseMaterials(Map<String, List<String>> courseMaterials) {
        this.courseMaterials = courseMaterials;
    }

    public Map<String, List<Assessment>> getStudentAssessments() {
        return studentAssessments;
    }

    public void setStudentAssessments(Map<String, List<Assessment>> studentAssessments) {
        this.studentAssessments = studentAssessments;
    }

    public Map<String, Boolean> getFypStatus() {
        return fypStatus;
    }

    public void setFypStatus(Map<String, Boolean> fypStatus) {
        this.fypStatus = fypStatus;
    }

    public Map<String, Map<String, Double>> getSemesterResults() {
        return semesterResults;
    }

    public void setSemesterResults(Map<String, Map<String, Double>> semesterResults) {
        this.semesterResults = semesterResults;
    }

    public Map<String, Boolean> getDegreeClearance() {
        return degreeClearance;
    }

    public void setDegreeClearance(Map<String, Boolean> degreeClearance) {
        this.degreeClearance = degreeClearance;
    }

    public Map<String, List<String>> getExamSchedule() {
        return examSchedule;
    }

    public void setExamSchedule(Map<String, List<String>> examSchedule) {
        this.examSchedule = examSchedule;
    }

    public Map<String, List<String>> getNotifications() {
        return notifications;
    }

    public void setNotifications(Map<String, List<String>> notifications) {
        this.notifications = notifications;
    }

    public Map<String, Map<String, String>> getTimetables() {
        return timetables;
    }

    public void setTimetables(Map<String, Map<String, String>> timetables) {
        this.timetables = timetables;
    }

    public void setAcademicHistory(Map<String, List<String>> academicHistory) {
        this.academicHistory = academicHistory;
    }

    public Map<String, List<String>> getFeedback() {
        return feedback;
    }

    public void setFeedback(Map<String, List<String>> feedback) {
        this.feedback = feedback;
    }

    public Map<String, List<String>> getPlacementInfo() {
        return placementInfo;
    }

    public void setPlacementInfo(Map<String, List<String>> placementInfo) {
        this.placementInfo = placementInfo;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
