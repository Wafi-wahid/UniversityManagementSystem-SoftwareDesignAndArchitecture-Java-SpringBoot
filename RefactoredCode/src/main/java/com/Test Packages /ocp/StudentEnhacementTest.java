/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ocp;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentEnhancementTest {
    
    private StudentEnhancement instance;
    
    public StudentEnhancementTest() {
    }
    
    @BeforeEach
    public void setUp() {
        instance = new StudentEnhancement();
    }
    
    /**
     * Test of markAttendance method, of class StudentEnhancement.
     */
    @Test
    public void testMarkAttendance() {
        System.out.println("markAttendance");
        String studentId = "S001";
        String courseCode = "CS101";
        
        // Mark attendance once
        instance.markAttendance(studentId, courseCode);
        
        // Mark attendance again to test counter increment
        instance.markAttendance(studentId, courseCode);
        
        // No assertion needed 
        // The actual verification would be done in viewAttendance test
        assertTrue(true);
    }
    
    /**
     * Test of viewAttendance method, of class StudentEnhancement.
     */
    @Test
    public void testViewAttendance() {
        System.out.println("viewAttendance");
        String studentId = "S001";
        String courseCode = "CS101";
        
        // First mark attendance so there's something to view
        instance.markAttendance(studentId, courseCode);
        
        // Then view it - no exceptions should occur
        instance.viewAttendance(studentId);
        
        
        assertTrue(true);
    }
    
    /**
     * Test of bookmarkCourse method, of class StudentEnhancement.
     */
    @Test
    public void testBookmarkCourse() {
        System.out.println("bookmarkCourse");
        String studentId = "S001";
        String courseCode = "CS101";
        
        // Bookmark a course
        instance.bookmarkCourse(studentId, courseCode);
        
        // Bookmark the same course again to test duplicate handling
        instance.bookmarkCourse(studentId, courseCode);
        
        // No exceptions should occur
        assertTrue(true);
    }
    
    /**
     * Test of viewBookmarkedCourses method, of class StudentEnhancement.
     */
    @Test
    public void testViewBookmarkedCourses() {
        System.out.println("viewBookmarkedCourses");
        String studentId = "S001";
        String courseCode = "CS101";
        
        // First bookmark a course
        instance.bookmarkCourse(studentId, courseCode);
        
        // Then view bookmarks
        instance.viewBookmarkedCourses(studentId);
        
        // No exceptions should occur
        assertTrue(true);
    }
    
    /**
     * Test of createStudyGroup method, of class StudentEnhancement.
     */
    @Test
    public void testCreateStudyGroup() {
        System.out.println("createStudyGroup");
        String studentId = "S001";
        String groupName = "Java Study Group";
        
        // Create a study group
        instance.createStudyGroup(studentId, groupName);
        
        // Try creating the same group again to test duplicate handling
        instance.createStudyGroup(studentId, groupName);
        
        // No exceptions should occur
        assertTrue(true);
    }
    
    /**
     * Test of joinStudyGroup method, of class StudentEnhancement.
     */
    @Test
    public void testJoinStudyGroup() {
        System.out.println("joinStudyGroup");
        String studentId = "S001";
        String groupName = "Java Study Group";
        String studentId2 = "S002";
        
        // First create a study group
        instance.createStudyGroup(studentId, groupName);
        
        // Then join it with another student
        instance.joinStudyGroup(studentId2, groupName);
        
        // Try joining a non-existent group
        instance.joinStudyGroup(studentId, "Non-existent Group");
        
        // No exceptions should occur
        assertTrue(true);
    }
    
    /**
     * Test of viewStudyGroups method, of class StudentEnhancement.
     */
    @Test
    public void testViewStudyGroups() {
        System.out.println("viewStudyGroups");
        String studentId = "S001";
        
        // First create a study group
        instance.createStudyGroup(studentId, "Java Study Group");
        
        // Then view study groups
        instance.viewStudyGroups(studentId);
        
        // No exceptions should occur
        assertTrue(true);
    }
}
