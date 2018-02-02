package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.domain.student.StudentRepresentative;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.UserUtil;
import ee.hitsa.ois.web.commandobject.student.StudentRepresentativeForm;

public class StudentRepresentativeDto extends StudentRepresentativeForm {

    private Long id;
    private Boolean userCanEdit;
    private Boolean userCanDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getUserCanEdit() {
        return userCanEdit;
    }

    public void setUserCanEdit(Boolean userCanEdit) {
        this.userCanEdit = userCanEdit;
    }

    public Boolean getUserCanDelete() {
        return userCanDelete;
    }

    public void setUserCanDelete(Boolean userCanDelete) {
        this.userCanDelete = userCanDelete;
    }

    public static StudentRepresentativeDto of(StudentRepresentative representative, HoisUserDetails user) {
        StudentRepresentativeDto dto = EntityUtil.bindToDto(representative, new StudentRepresentativeDto());
        dto.setPerson(EntityUtil.bindToDto(representative.getPerson(), new StudentRepresentativePersonDto()));
        dto.setUserCanEdit(Boolean.valueOf(UserUtil.canEditStudentRepresentative(user, representative)));
        dto.setUserCanDelete(Boolean.valueOf(UserUtil.canDeleteStudentRepresentative(user, representative)));
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
