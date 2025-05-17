
package ocp;

import java.util.ArrayList;
import java.util.List;

public class CourseBookmarker {
    private List<BookmarkRecord> bookmarkedCourses = new ArrayList<>();
    
    public void bookmarkCourse(String studentId, String courseCode) {
        for (BookmarkRecord bookmark : bookmarkedCourses) {
            if (bookmark.studentId.equals(studentId) && bookmark.courseCode.equals(courseCode)) {
                System.out.println("Course already bookmarked!");
                return;
            }
        }
        
        bookmarkedCourses.add(new BookmarkRecord(studentId, courseCode));
        System.out.println("Course bookmarked successfully!");
    }
    
    public void viewBookmarkedCourses(String studentId) {
        System.out.println("\nYour Bookmarked Courses:");
        boolean found = false;
        
        for (BookmarkRecord bookmark : bookmarkedCourses) {
            if (bookmark.studentId.equals(studentId)) {
                System.out.println(bookmark.courseCode);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No bookmarked courses found.");
        }
    }
    
   
}
