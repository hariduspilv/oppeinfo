package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;

public class StudentAbsenceDto extends StudentAbsenceForm {

    private Long id;
    private Boolean isAccepted;
    private Boolean userCanEdit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Boolean getUserCanEdit() {
        return userCanEdit;
    }

    public void setUserCanEdit(Boolean userCanEdit) {
        this.userCanEdit = userCanEdit;
    }
}
