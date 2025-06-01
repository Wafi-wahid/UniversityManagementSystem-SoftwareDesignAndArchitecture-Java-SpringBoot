package ocp;

public class StudentEnhancement {
    private AttendanceTracker attendanceTracker;
    private CourseBookmarker courseBookmarker;
    private StudyGroupManager studyGroupManager;
    
    public StudentEnhancement() {
        this.attendanceTracker = new AttendanceTracker();
        this.courseBookmarker = new CourseBookmarker();
        this.studyGroupManager = new StudyGroupManager();
    }
   
    public void markAttendance(String studentId, String courseCode) {
        attendanceTracker.markAttendance(studentId, courseCode);
    }
    
    public void viewAttendance(String studentId) {
        attendanceTracker.viewAttendance(studentId);
    }
    
    public void bookmarkCourse(String studentId, String courseCode) {
        courseBookmarker.bookmarkCourse(studentId, courseCode);
    }
    
    public void viewBookmarkedCourses(String studentId) {
        courseBookmarker.viewBookmarkedCourses(studentId);
    }
    
    public void createStudyGroup(String studentId, String groupName) {
        studyGroupManager.createStudyGroup(studentId, groupName);
    }
    
    public void joinStudyGroup(String studentId, String groupName) {
        studyGroupManager.joinStudyGroup(studentId, groupName);
    }
    
    public void viewStudyGroups(String studentId) {
        studyGroupManager.viewStudyGroups(studentId);
    }
}
