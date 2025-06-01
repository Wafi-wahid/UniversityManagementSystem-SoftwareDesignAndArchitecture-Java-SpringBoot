
package ocp;

import java.util.ArrayList;
import java.util.List;

public class StudyGroupManager {
    private List<StudyGroup> studyGroups = new ArrayList<>();
    
    public void createStudyGroup(String studentId, String groupName) {
        for (StudyGroup group : studyGroups) {
            if (group.name.equals(groupName)) {
                if (!group.members.contains(studentId)) {
                    group.members.add(studentId);
                }
                System.out.println("Study group already exists. You've been added as a member.");
                return;
            }
        }
        
        StudyGroup newGroup = new StudyGroup(groupName);
        newGroup.members.add(studentId);
        studyGroups.add(newGroup);
        System.out.println("Study group '" + groupName + "' created successfully!");
    }
    
    public void joinStudyGroup(String studentId, String groupName) {
        for (StudyGroup group : studyGroups) {
            if (group.name.equals(groupName)) {
                if (!group.members.contains(studentId)) {
                    group.members.add(studentId);
                    System.out.println("Joined study group '" + groupName + "' successfully!");
                } else {
                    System.out.println("You're already in this study group!");
                }
                return;
            }
        }
        
        System.out.println("Study group not found!");
    }
    
    public void viewStudyGroups(String studentId) {
        System.out.println("\nAvailable Study Groups:");
        
        if (studyGroups.isEmpty()) {
            System.out.println("No study groups available.");
            return;
        }
        
        for (StudyGroup group : studyGroups) {
            System.out.println("\nGroup: " + group.name);
            System.out.println("Members: " + group.members.size());
            
            if (group.members.contains(studentId)) {
                System.out.println("(You are a member)");
            }
        }
    }
    
}
