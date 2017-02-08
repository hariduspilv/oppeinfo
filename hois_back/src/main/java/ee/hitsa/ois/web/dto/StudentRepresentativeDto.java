package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.StudentRepresentative;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudentRepresentativeForm;

public class StudentRepresentativeDto extends StudentRepresentativeForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static StudentRepresentativeDto of(StudentRepresentative studentRepresentative) {
        StudentRepresentativeDto dto = EntityUtil.bindToDto(studentRepresentative, new StudentRepresentativeDto());
        dto.setPerson(EntityUtil.bindToDto(studentRepresentative.getPerson(), new StudentRepresentativePersonDto()));
        return dto;
    }

    public static class StudentRepresentativePersonDto extends StudentRepresentativePersonForm {
        private String fullname;

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }
    }
}
