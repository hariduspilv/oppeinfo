package ee.hitsa.ois.domain.protocol;

import java.time.LocalDate;
import java.util.ArrayList;
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
import ee.hitsa.ois.domain.curriculum.CurriculumGrade;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodExamStudent;

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

    private Short gradeMark;

    private LocalDate gradeDate;

    @Size(max = 255)
    private String addInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubjectStudyPeriodExamStudent subjectStudyPeriodExamStudent;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumGrade curriculumGrade;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "protocol_student_id", nullable = false, updatable = false)
    private List<ProtocolStudentHistory> protocolStudentHistories;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "protocol_student_id", nullable = false, updatable = false)
    private List<ProtocolStudentOccupation> protocolStudentOccupations = new ArrayList<>();

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

    public Short getGradeMark() {
        return gradeMark;
    }

    public void setGradeMark(Short gradeMark) {
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

    public SubjectStudyPeriodExamStudent getSubjectStudyPeriodExamStudent() {
        return subjectStudyPeriodExamStudent;
    }

    public void setSubjectStudyPeriodExamStudent(SubjectStudyPeriodExamStudent subjectStudyPeriodExamStudent) {
        this.subjectStudyPeriodExamStudent = subjectStudyPeriodExamStudent;
    }
    
    public CurriculumGrade getCurriculumGrade() {
        return curriculumGrade;
    }

    public void setCurriculumGrade(CurriculumGrade curriculumGrade) {
        this.curriculumGrade = curriculumGrade;
    }

    public List<ProtocolStudentHistory> getProtocolStudentHistories() {
        return protocolStudentHistories;
    }

    public void setProtocolStudentHistories(List<ProtocolStudentHistory> protocolStudentHistories) {
        this.protocolStudentHistories = protocolStudentHistories;
    }

    public List<ProtocolStudentOccupation> getProtocolStudentOccupations() {
        return protocolStudentOccupations;
    }

    public void setProtocolStudentOccupations(List<ProtocolStudentOccupation> protocolStudentOccupations) {
        this.protocolStudentOccupations = protocolStudentOccupations;
    }
    
}
