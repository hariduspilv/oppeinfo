package ee.hitsa.ois.web.commandobject.student;

import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.EstonianIdCode;
import ee.hitsa.ois.validation.NotEmpty;

public class StudentRepresentativeApplicationForm {

    @EstonianIdCode
    private String studentIdcode;
    @NotEmpty
    @Size(max = 100)
    private String phone;
    @NotEmpty
    @Size(max = 255)
    private String email;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.OPPURESINDAJA)
    private String relation;

    public String getStudentIdcode() {
        return studentIdcode;
    }

    public void setStudentIdcode(String studentIdcode) {
        this.studentIdcode = studentIdcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
