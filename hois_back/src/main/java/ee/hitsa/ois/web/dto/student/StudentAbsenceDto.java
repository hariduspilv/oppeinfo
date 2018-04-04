package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentAbsenceDto extends StudentAbsenceForm {

    private Long id;
    private Boolean isAccepted;
    private Boolean isRejected;
    private Boolean userCanEdit;
    private Boolean canChangeStatus;
    private AutocompleteResult student;
    private String applicant;
    private String acceptor;
    
    public static StudentAbsenceDto of(StudentAbsence studentAbsence) {
        StudentAbsenceDto dto = EntityUtil.bindToDto(studentAbsence, new StudentAbsenceDto(), "student");
        Student s = studentAbsence.getStudent();
        String fullname = PersonUtil.fullname(s.getPerson());
        dto.setStudent(new AutocompleteResult(s.getId(), fullname, fullname));
        dto.setApplicant(PersonUtil.stripIdcodeFromFullnameAndIdcode(studentAbsence.getInsertedBy()));
        if (studentAbsence.getIsRejected() != null) {
            dto.setIsRejected(Boolean.FALSE);
        }
        if(Boolean.TRUE.equals(studentAbsence.getIsAccepted()) || Boolean.TRUE.equals(studentAbsence.getIsRejected())) {
            dto.setAcceptor(studentAbsence.getAcceptedBy() != null ? studentAbsence.getAcceptedBy()
                    : PersonUtil.stripIdcodeFromFullnameAndIdcode(studentAbsence.getChangedBy()));
        }
        return dto;
    }

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

    public Boolean getIsRejected() {
        return isRejected;
    }

    public void setIsRejected(Boolean isRejected) {
        this.isRejected = isRejected;
    }

    public Boolean getUserCanEdit() {
        return userCanEdit;
    }

    public void setUserCanEdit(Boolean userCanEdit) {
        this.userCanEdit = userCanEdit;
    }

    public AutocompleteResult getStudent() {
        return student;
    }

    public void setStudent(AutocompleteResult student) {
        this.student = student;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }

    public Boolean getCanChangeStatus() {
        return canChangeStatus;
    }

    public void setCanChangeStatus(Boolean canChangeStatus) {
        this.canChangeStatus = canChangeStatus;
    }
    
}
