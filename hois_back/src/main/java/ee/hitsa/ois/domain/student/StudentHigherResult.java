package ee.hitsa.ois.domain.student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.validation.Required;

@Entity
public class StudentHigherResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersionHigherModule curriculumVersionHmodule;
    
    @Required
    private String subjectNameEt;
    
    @Required
    private String subjectNameEn;
    
    private String subjectCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private ApelSchool apelSchool;
    
    @Column(name = "grade")
    private String gradeValue;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier grade;
    
    private Short gradeMark;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private ProtocolStudent protocolStudent;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private ApelApplicationRecord apelApplicationRecord;
    
    @Required
    private BigDecimal credits;
    
    @Required
    private Boolean isOptional;
    
    private String teachers;
    private LocalDate gradeDate;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime inserted;
    
    @LastModifiedDate
    private LocalDateTime changed;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyPeriod studyPeriod;
    
    @OneToOne(mappedBy="studentHigherResult", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, updatable = false)
    private StudentHigherResultModule changedModule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public CurriculumVersionHigherModule getCurriculumVersionHmodule() {
        return curriculumVersionHmodule;
    }

    public void setCurriculumVersionHmodule(CurriculumVersionHigherModule curriculumVersionHmodule) {
        this.curriculumVersionHmodule = curriculumVersionHmodule;
    }

    public String getSubjectNameEt() {
        return subjectNameEt;
    }

    public void setSubjectNameEt(String subjectNameEt) {
        this.subjectNameEt = subjectNameEt;
    }

    public String getSubjectNameEn() {
        return subjectNameEn;
    }

    public void setSubjectNameEn(String subjectNameEn) {
        this.subjectNameEn = subjectNameEn;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public ApelSchool getApelSchool() {
        return apelSchool;
    }

    public void setApelSchool(ApelSchool apelSchool) {
        this.apelSchool = apelSchool;
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

    public ProtocolStudent getProtocolStudent() {
        return protocolStudent;
    }

    public void setProtocolStudent(ProtocolStudent protocolStudent) {
        this.protocolStudent = protocolStudent;
    }

    public ApelApplicationRecord getApelApplicationRecord() {
        return apelApplicationRecord;
    }

    public void setApelApplicationRecord(ApelApplicationRecord apelApplicationRecord) {
        this.apelApplicationRecord = apelApplicationRecord;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public Boolean getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(Boolean isOptional) {
        this.isOptional = isOptional;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public StudentHigherResultModule getChangedModule() {
        return changedModule;
    }

    public void setChangedModule(StudentHigherResultModule changedModule) {
        this.changedModule = changedModule;
    }
    
}
