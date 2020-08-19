package ee.hitsa.ois.web;

import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.Student;

public class FotoBoxStudentResultDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String usercode;
    private Boolean success;

    public FotoBoxStudentResultDto(Student student) {
        id = student.getId();
        Person person = student.getPerson();
        firstname = person.getFirstname();
        lastname = person.getLastname();
        usercode = person.getUsercode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public Boolean getSuccess() {
        return success != null ? success : Boolean.FALSE;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
