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

import ee.hitsa.ois.domain.gradingschema.GradingSchemaRow;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.apelapplication.ApelApplicationRecord;
import ee.hitsa.ois.domain.apelapplication.ApelSchool;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.validation.Required;

@Entity
public class StudentVocationalResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private ApelApplicationRecord apelApplicationRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersionOccupationModule curriculumVersionOmodule;

    private String moduleNameEt;
    private String moduleNameEn;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private ApelSchool apelSchool;

    @Column(name = "grade")
    private String gradeValue;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier grade;

    private Short gradeMark;

    @Required
    private BigDecimal credits;

    private String teachers;
    private LocalDate gradeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProtocolStudent protocolStudent;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime inserted;

    @LastModifiedDate
    private LocalDateTime changed;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyYear studyYear;

    @ManyToOne(fetch = FetchType.LAZY)
    private GradingSchemaRow gradingSchemaRow;
    
    @OneToOne(mappedBy="studentVocationalResult", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, updatable = false)
    private StudentVocationalResultOmodule changedModule;
    
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

    public ApelApplicationRecord getApelApplicationRecord() {
        return apelApplicationRecord;
    }

    public void setApelApplicationRecord(ApelApplicationRecord apelApplicationRecord) {
        this.apelApplicationRecord = apelApplicationRecord;
    }

    public CurriculumVersionOccupationModule getCurriculumVersionOmodule() {
        return curriculumVersionOmodule;
    }

    public void setCurriculumVersionOmodule(CurriculumVersionOccupationModule curriculumVersionOmodule) {
        this.curriculumVersionOmodule = curriculumVersionOmodule;
    }

    public String getModuleNameEt() {
        return moduleNameEt;
    }

    public void setModuleNameEt(String moduleNameEt) {
        this.moduleNameEt = moduleNameEt;
    }

    public String getModuleNameEn() {
        return moduleNameEn;
    }

    public void setModuleNameEn(String moduleNameEn) {
        this.moduleNameEn = moduleNameEn;
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

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
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

    public ProtocolStudent getProtocolStudent() {
        return protocolStudent;
    }

    public void setProtocolStudent(ProtocolStudent protocolStudent) {
        this.protocolStudent = protocolStudent;
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

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public GradingSchemaRow getGradingSchemaRow() {
        return gradingSchemaRow;
    }

    public void setGradingSchemaRow(GradingSchemaRow gradingSchemaRow) {
        this.gradingSchemaRow = gradingSchemaRow;
    }

    public StudentVocationalResultOmodule getChangedModule() {
        return changedModule;
    }

    public void setChangedModule(StudentVocationalResultOmodule changedModule) {
        this.changedModule = changedModule;
    }
    
}
