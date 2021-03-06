package ee.AD.demo;

public class TimetableEventTeacherDto {
    
    public TimetableEventTeacherDto() {}
    
    public TimetableEventTeacherDto(Long teacherId, String firstname, String lastname) {
        this.teacherId = teacherId;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    private Long teacherId;
    private String firstname;
    private String lastname;
    
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
