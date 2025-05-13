/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ums;
import java.util.*;
import java.time.*;

// Main class to run the application
public class UMS {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Student> students = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static Map<String, List<String>> prerequisites = new HashMap<>();
    private static Map<String, Double> studentGPAs = new HashMap<>();
    private static Map<String, Integer> completedCredits = new HashMap<>();
    private static Map<String, Boolean> feeStatus = new HashMap<>();
    private static Map<String, List<String>> enrolledCourses = new HashMap<>();
    private static Map<String, List<String>> courseMaterials = new HashMap<>();
    private static Map<String, List<Assessment>> studentAssessments = new HashMap<>();
    private static Map<String, Boolean> fypStatus = new HashMap<>();
    private static Map<String, Map<String, Double>> semesterResults = new HashMap<>();
    private static Map<String, Boolean> degreeClearance = new HashMap<>();
    private static Map<String, List<String>> examSchedule = new HashMap<>();
    private static Map<String, List<String>> notifications = new HashMap<>();
    private static Map<String, Map<String, String>> timetables = new HashMap<>();
    static Map<String, List<String>> academicHistory = new HashMap<>();
    private static Map<String, List<String>> feedback = new HashMap<>();
    private static Map<String, List<String>> placementInfo = new HashMap<>();

    public static void main(String[] args) {
        initializeData();
        
        System.out.println("Welcome to Student Management System");
        
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Student Registration");
            System.out.println("2. Course Management");
            System.out.println("3. Student Operations");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    Student.registerStudent(students, scanner);
                    break;
                case 2:
                    manageCourses();
                    break;
                case 3:
                    studentOperations();
                    break;
                case 4:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private static void initializeData() {
        // Initialize some courses
        courses.add(new Course("CS101", "Introduction to Programming", 3));
        courses.add(new Course("CS201", "Data Structures", 3));
        courses.add(new Course("CS301", "Algorithms", 3));
        courses.add(new Course("CS401", "Database Systems", 3));
        courses.add(new Course("CS501", "Operating Systems", 3));
        courses.add(new Course("CS601", "Computer Networks", 3));
        courses.add(new Course("CS701", "Software Engineering", 3));
        courses.add(new Course("CS801", "Final Year Project", 6));
        
        // Set prerequisites
        prerequisites.put("CS201", Arrays.asList("CS101"));
        prerequisites.put("CS301", Arrays.asList("CS201"));
        prerequisites.put("CS401", Arrays.asList("CS201"));
        prerequisites.put("CS501", Arrays.asList("CS301"));
        prerequisites.put("CS601", Arrays.asList("CS301"));
        prerequisites.put("CS701", Arrays.asList("CS401", "CS501"));
        prerequisites.put("CS801", Arrays.asList("CS701"));
        
        // Initialize course materials
        courseMaterials.put("CS101", Arrays.asList("Lecture 1: Introduction", "Lecture 2: Variables", "Lecture 3: Control Structures"));
        courseMaterials.put("CS201", Arrays.asList("Lecture 1: Arrays", "Lecture 2: Linked Lists", "Lecture 3: Stacks and Queues"));
        
        // Initialize some students for testing
        Student testStudent = new Student("S001", "Test Student", "test@university.edu", "1234567890");
        students.add(testStudent);
        studentGPAs.put("S001", 3.5);
        completedCredits.put("S001", 90);
        feeStatus.put("S001", true);
        enrolledCourses.put("S001", new ArrayList<>(Arrays.asList("CS101", "CS201")));
        academicHistory.put("S001", new ArrayList<>(Arrays.asList("CS101: A", "MATH101: B+", "ENG101: A-")));
    }
    
    private static void manageCourses() {
        System.out.println("\nCourse Management:");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Set Prerequisites");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                Course.addCourse(courses, scanner);
                break;
            case 2:
                Course.viewAllCourses(courses);
                break;
            case 3:
                PrerequisiteManager.setPrerequisites(prerequisites, courses, scanner);
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    private static void studentOperations() {
        System.out.print("\nEnter Student ID: ");
        String studentId = scanner.nextLine();
        
        Student student = findStudent(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("\nWelcome, " + student.getName());
        
        while (true) {
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
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    studentCourseRegistration(studentId);
                    break;
                case 2:
                    viewAcademicStatus(studentId);
                    break;
                case 3:
                    Finance.payFees(studentId, feeStatus, scanner);
                    break;
                case 4:
                    CourseMaterial.accessMaterials(studentId, enrolledCourses, courseMaterials, scanner);
                    break;
                case 5:
                    Assessment.submitAssessment(studentId, studentAssessments, scanner);
                    break;
                case 6:
                    FYPManager.registerForFYP(studentId, completedCredits, fypStatus, enrolledCourses, prerequisites, scanner);
                    break;
                case 7:
                    Result.viewResults(studentId, semesterResults);
                    break;
                case 8:
                    Scholarship.checkScholarship(studentId, studentGPAs);
                    break;
                case 9:
                    DegreeClearance.applyForClearance(studentId, degreeClearance, feeStatus, completedCredits, scanner);
                    break;
                case 10:
                    Exam.viewExams(studentId, enrolledCourses, examSchedule);
                    break;
                case 11:
                    Notification.viewNotifications(studentId, notifications);
                    break;
                case 12:
                    Timetable.viewTimetable(studentId, timetables);
                    break;
                case 13:
                    AcademicHistory.viewHistory(studentId, academicHistory);
                    break;
                case 14:
                    Feedback.submitFeedback(studentId, feedback, scanner);
                    break;
                case 15:
                    Placement.viewPlacementInfo(studentId, placementInfo);
                    break;
                case 16:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
    
    private static void studentCourseRegistration(String studentId) {
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
        System.out.println("Current enrolled credits: " + currentCredits);
        
        Course.viewAllCourses(courses);
        
        System.out.print("\nEnter course code to register (or 'done' to finish): ");
        String courseCode = scanner.nextLine();
        
        while (!courseCode.equalsIgnoreCase("done")) {
            if (currentCredits >= maxCredits) {
                System.out.println("You have reached your maximum credit limit.");
                break;
            }
            
            if (PrerequisiteManager.checkPrerequisites(studentId, courseCode, prerequisites, academicHistory)) {
                enrolledCourses.computeIfAbsent(studentId, k -> new ArrayList<>()).add(courseCode);
                currentCredits += 3;
                System.out.println("Successfully registered for " + courseCode);
            } else {
                System.out.println("Cannot register for " + courseCode + ". Prerequisites not met.");
            }
            
            System.out.print("Enter next course code (or 'done' to finish): ");
            courseCode = scanner.nextLine();
        }
    }
    
    private static void viewAcademicStatus(String studentId) {
        System.out.println("\nAcademic Status for " + studentId);
        System.out.println("Current GPA: " + studentGPAs.getOrDefault(studentId, 0.0));
        System.out.println("Completed Credits: " + completedCredits.getOrDefault(studentId, 0));
        
        List<String> enrolled = enrolledCourses.getOrDefault(studentId, new ArrayList<>());
        System.out.println("\nCurrently Enrolled Courses (" + enrolled.size() * 3 + " credits):");
        enrolled.forEach(c -> System.out.println("- " + c));
        
        if (fypStatus.getOrDefault(studentId, false)) {
            System.out.println("\nStatus: Registered for Final Year Project");
        }
    }
    
    private static Student findStudent(String studentId) {
        for (Student s : students) {
            if (s.getId().equals(studentId)) {
                return s;
            }
        }
        return null;
    }
}





















