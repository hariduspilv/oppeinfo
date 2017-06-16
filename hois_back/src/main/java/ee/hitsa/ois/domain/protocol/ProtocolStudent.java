package ee.hitsa.ois.domain.protocol;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.timetable.JournalStudent;

@Entity
public class ProtocolStudent extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Protocol protocol;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = true)
    private Student student;

    /**
     * Grade representation value. In most use cases this is same as Classifier
     * grade.value, but in does not have to be.
     */
    @Size(max = 3)
    @Column(name = "grade")
    private String gradeValue;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier grade;

    private Integer gradeMark;

    private LocalDate gradeDate;

    @Size(max = 255)
    private String addInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "protocol_student_id", nullable = false, updatable = false)
    private List<ProtocolStudentHistory> protocolStudentHistories;

    @OneToMany
    @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false, updatable = false, insertable = false)
    private List<JournalStudent> journalStudents;

    public ProtocolStudent() {
    }

    public ProtocolStudent(Student student) {
        this.student = student;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public Classifier getGrade() {
        return grade;
    }

    public void setGrade(Classifier grade) {
        this.grade = grade;
    }

    public Integer getGradeMark() {
        return gradeMark;
    }

    public void setGradeMark(Integer gradeMark) {
        this.gradeMark = gradeMark;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public List<ProtocolStudentHistory> getProtocolStudentHistories() {
        return protocolStudentHistories;
    }

    public void setProtocolStudentHistories(List<ProtocolStudentHistory> protocolStudentHistories) {
        this.protocolStudentHistories = protocolStudentHistories;
    }

    public List<JournalStudent> getJournalStudents() {
        return journalStudents;
    }

    public void setJournalStudents(List<JournalStudent> journalStudents) {
        this.journalStudents = journalStudents;
    }

}
