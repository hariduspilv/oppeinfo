package ee.hitsa.ois.web.commandobject.directive;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class DirectiveForm extends VersionedCommand {

    @NotEmpty
    @ClassifierRestriction(MainClassCode.KASKKIRI)
    private String type;
    @NotEmpty
    @Size(max = 500)
    private String headline;
    @Size(max = 4000)
    private String addInfo;
    private Long directiveCoordinator;
    @Valid
    private List<? extends DirectiveFormStudent> students;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Long getDirectiveCoordinator() {
        return directiveCoordinator;
    }

    public void setDirectiveCoordinator(Long directiveCoordinator) {
        this.directiveCoordinator = directiveCoordinator;
    }

    public List<? extends DirectiveFormStudent> getStudents() {
        return students;
    }

    public void setStudents(List<? extends DirectiveFormStudent> students) {
        this.students = students;
    }

    public static class DirectiveFormStudent {
        private Long id;
        private String idcode;
        private String firstname;
        private String lastname;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getIdcode() {
            return idcode;
        }

        public void setIdcode(String idcode) {
            this.idcode = idcode;
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

        public static DirectiveFormStudent of(DirectiveStudent student) {
            return EntityUtil.bindToDto(student, new DirectiveFormStudent());
        }
    }
}
