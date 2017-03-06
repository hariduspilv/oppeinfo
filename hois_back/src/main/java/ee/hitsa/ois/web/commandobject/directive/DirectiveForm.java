package ee.hitsa.ois.web.commandobject.directive;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class DirectiveForm extends VersionedCommand {

    @ClassifierRestriction(MainClassCode.KASKKIRI)
    private String type;
    private String headline;
    @Valid
    private List<DirectiveFormStudent> students;

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

    public List<DirectiveFormStudent> getStudents() {
        return students;
    }

    public void setStudents(List<DirectiveFormStudent> students) {
        this.students = students;
    }

    public static DirectiveForm of(Directive directive) {
        DirectiveForm dto = EntityUtil.bindToDto(directive, new DirectiveForm());
        dto.setStudents(directive.getStudents().stream().map(DirectiveFormStudent::of).collect(Collectors.toList()));
        return dto;
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
