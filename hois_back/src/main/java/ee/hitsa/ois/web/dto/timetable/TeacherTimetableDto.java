package ee.hitsa.ois.web.dto.timetable;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;

public class TeacherTimetableDto {
    private Long teacherId;
    private String firstname;
    private String lastname;
    
    public TeacherTimetableDto(Object[] row) {
        this.teacherId =  resultAsLong(row, 0);
        this.firstname = (String) row[1];
        this.lastname = (String) row[2];
    }
    
    public Long getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    
    public String getFirstname() {
        return firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
}
