package ee.hitsa.ois.domain.student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Contract;
import ee.hitsa.ois.domain.directive.DirectiveStudent;

@Entity
public class StudentAbsence extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    private LocalDate validFrom;
    private LocalDate validThru;
    private String cause;
    private String acceptedBy;
    private Boolean isAccepted;
    private Boolean isRejected;
    private Boolean isLessonAbsence;
    private String rejectReason;

    @OneToOne
    private Contract contract;

//    @OneToOne
    @ManyToOne
    private DirectiveStudent directiveStudent;

    @OneToMany(mappedBy="studentAbsence", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentAbsenceLesson> studentAbsenceLessons = new ArrayList<>();

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
    
    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
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

    public Boolean getIsLessonAbsence() {
        return isLessonAbsence;
    }

    public void setIsLessonAbsence(Boolean isLessonAbsence) {
        this.isLessonAbsence = isLessonAbsence;
    }

    public List<StudentAbsenceLesson> getStudentAbsenceLessons() {
        return studentAbsenceLessons;
    }

    public void setStudentAbsenceLessons(List<StudentAbsenceLesson> studentAbsenceLessons) {
        this.studentAbsenceLessons = studentAbsenceLessons;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public DirectiveStudent getDirectiveStudent() {
        return directiveStudent;
    }

    public void setDirectiveStudent(DirectiveStudent directiveStudent) {
        this.directiveStudent = directiveStudent;
    }

}
