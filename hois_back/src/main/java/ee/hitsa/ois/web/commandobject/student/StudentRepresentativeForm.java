package ee.hitsa.ois.web.commandobject.student;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.EstonianIdCode;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StudentRepresentativeForm extends VersionedCommand {

    @Valid
    @NotNull
    private StudentRepresentativePersonForm person;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.OPPURESINDAJA)
    private String relation;
    private Boolean isStudentVisible;

    public StudentRepresentativePersonForm getPerson() {
        return person;
    }

    public void setPerson(StudentRepresentativePersonForm person) {
        this.person = person;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Boolean getIsStudentVisible() {
        return isStudentVisible;
    }

    public void setIsStudentVisible(Boolean isStudentVisible) {
        this.isStudentVisible = isStudentVisible;
    }

    public static class StudentRepresentativePersonForm {

        @NotEmpty
        @Size(max = 255)
        private String firstname;
        @NotEmpty
        @Size(max = 255)
        private String lastname;
        @NotEmpty
        @EstonianIdCode
        private String idcode;
        @NotEmpty
        @Size(max = 100)
        private String phone;
        @NotEmpty
        @Size(max = 255)
        private String email;

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

        public String getIdcode() {
            return idcode;
        }

        public void setIdcode(String idcode) {
            this.idcode = idcode;
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
    }
}
