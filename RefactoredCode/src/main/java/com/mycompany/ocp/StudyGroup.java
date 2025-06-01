package ocp;

import java.util.ArrayList;
import java.util.List;
class StudyGroup {
        String name;
        List<String> members;
        
        StudyGroup(String name) {
            this.name = name;
            this.members = new ArrayList<>();
        }
    }
