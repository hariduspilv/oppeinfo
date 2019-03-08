package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentAbsence;
import ee.hitsa.ois.domain.student.StudentAbsenceLesson;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.commandobject.student.StudentAbsenceForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentAbsenceDto extends StudentAbsenceForm {

    private Long id;
    private Boolean isAccepted;
    private Boolean isRejected;
    private String rejectReason;
    private Boolean userCanEdit;
    private Boolean canAccept;
    private Boolean canReject;
    private AutocompleteResult student;
    private String studentGroup;
    private String applicant;
    private String acceptor;
    private Boolean isLessonAbsence;
    private Map<LocalDate, Map<Long, Boolean>> acceptedLessonsByDate;
    private Long maxLessonNr;
    private Long contractId;
    
    public static StudentAbsenceDto of(StudentAbsence studentAbsence) {
        StudentAbsenceDto dto = EntityUtil.bindToDto(studentAbsence, new StudentAbsenceDto(), "student");
        Student s = studentAbsence.getStudent();
        String fullname = PersonUtil.fullname(s.getPerson());
        dto.setStudent(new AutocompleteResult(s.getId(), fullname, fullname));
        dto.setStudentGroup(s.getStudentGroup() != null ? s.getStudentGroup().getCode() : null);
        dto.setApplicant(PersonUtil.stripIdcodeFromFullnameAndIdcode(studentAbsence.getInsertedBy()));
        if (studentAbsence.getIsRejected() != null) {
            dto.setIsRejected(Boolean.FALSE);
        }
        if(Boolean.TRUE.equals(studentAbsence.getIsAccepted()) || Boolean.TRUE.equals(studentAbsence.getIsRejected())) {
            dto.setAcceptor(studentAbsence.getAcceptedBy() != null ? studentAbsence.getAcceptedBy()
                    : PersonUtil.stripIdcodeFromFullnameAndIdcode(studentAbsence.getChangedBy()));
        }
        dto.setIsLessonAbsence(studentAbsence.getIsLessonAbsence());
        
        Map<LocalDate, Map<Long, Boolean>> acceptedLessonsByDate = new HashMap<>();
        Map<LocalDate, Map<Long, List<StudentAbsenceLesson>>> lessonsGroupedByDateAnDLessonNr = studentAbsence
                .getStudentAbsenceLessons().stream().collect(Collectors.groupingBy(StudentAbsenceLesson::getAbsence,
                        Collectors.groupingBy(StudentAbsenceLesson::getLessonNr)));
        for (LocalDate date : lessonsGroupedByDateAnDLessonNr.keySet()) {
            Map<Long, Boolean> acceptedLessons = new HashMap<>();
            for (Long lessonNr : lessonsGroupedByDateAnDLessonNr.get(date).keySet()) {
                acceptedLessons.put(lessonNr, Boolean.TRUE);
            }
            acceptedLessonsByDate.put(date, acceptedLessons);
        }
        
        dto.setAcceptedLessonsByDate(acceptedLessonsByDate);
        dto.setMaxLessonNr(studentAbsence.getStudentAbsenceLessons().stream().map(StudentAbsenceLesson::getLessonNr)
                .max(Comparator.comparing(Long::valueOf)).orElse(Long.valueOf(0)));
        dto.setContractId(EntityUtil.getNullableId(studentAbsence.getContract()));
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
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

    public Boolean getCanAccept() {
        return canAccept;
    }

    public void setCanAccept(Boolean canAccept) {
        this.canAccept = canAccept;
    }

    public Boolean getCanReject() {
        return canReject;
    }

    public void setCanReject(Boolean canReject) {
        this.canReject = canReject;
    }

    public Boolean getIsLessonAbsence() {
        return isLessonAbsence;
    }

    public void setIsLessonAbsence(Boolean isLessonAbsence) {
        this.isLessonAbsence = isLessonAbsence;
    }

    public Map<LocalDate, Map<Long, Boolean>> getAcceptedLessonsByDate() {
        return acceptedLessonsByDate;
    }

    public void setAcceptedLessonsByDate(Map<LocalDate, Map<Long, Boolean>> acceptedLessonsByDate) {
        this.acceptedLessonsByDate = acceptedLessonsByDate;
    }

    public Long getMaxLessonNr() {
        return maxLessonNr;
    }

    public void setMaxLessonNr(Long maxLessonNr) {
        this.maxLessonNr = maxLessonNr;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

}
