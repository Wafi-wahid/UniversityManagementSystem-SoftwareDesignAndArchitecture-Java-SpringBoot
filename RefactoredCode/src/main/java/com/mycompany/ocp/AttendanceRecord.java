package ocp;

class AttendanceRecord {
        String studentId;
        String courseCode;
        int count;
        
        AttendanceRecord(String studentId, String courseCode) {
            this.studentId = studentId;
            this.courseCode = courseCode;
            this.count = 1;
        }
    }
