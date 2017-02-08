package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.domain.StudentAbsence;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StudentAbsenceForm;

public class StudentAbsenceDto extends StudentAbsenceForm {

    private Long id;
    private Boolean isAccepted;

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

    public static StudentAbsenceDto of(StudentAbsence absence) {
        return EntityUtil.bindToDto(absence, new StudentAbsenceDto());
    }
}
