package ee.hitsa.ois.domain.subject.subjectprogram;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;

@Entity
public class SubjectProgram extends BaseEntityWithId {

    private String independentStudy;
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    private SubjectStudyPeriodTeacher subjectStudyPeriodTeacher;
    private String assessmentDescription;
    private String studyLiterature;
    private String studyDescription;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier studyContentType;
    @Column(name="is_public_all", nullable = false)
    private Boolean publicAll;
    @Column(name="is_public_hois", nullable = false)
    private Boolean publicHois;
    @Column(name="is_public_student", nullable = false)
    private Boolean publicStudent;
    private LocalDateTime confirmed;
    private String confirmedBy;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    
    private String passDescription;
    private String npassDescription;
    @Column(name="grade0_description")
    private String grade0Description;
    @Column(name="grade1_description")
    private String grade1Description;
    @Column(name="grade2_description")
    private String grade2Description;
    @Column(name="grade3_description")
    private String grade3Description;
    @Column(name="grade4_description")
    private String grade4Description;
    @Column(name="grade5_description")
    private String grade5Description;
    
    private String rejectInfo;
    private String addInfo;
    
    @OneToMany(mappedBy="subjectProgram", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<SubjectProgramStudyContent> studyContents = new HashSet<>();
    
    public String getIndependentStudy() {
        return independentStudy;
    }
    public void setIndependentStudy(String independentStudy) {
        this.independentStudy = independentStudy;
    }
    public SubjectStudyPeriodTeacher getSubjectStudyPeriodTeacher() {
        return subjectStudyPeriodTeacher;
    }
    public void setSubjectStudyPeriodTeacher(SubjectStudyPeriodTeacher subjectStudyPeriodTeacher) {
        this.subjectStudyPeriodTeacher = subjectStudyPeriodTeacher;
    }
    public String getAssessmentDescription() {
        return assessmentDescription;
    }
    public void setAssessmentDescription(String assessmentDescription) {
        this.assessmentDescription = assessmentDescription;
    }
    public String getStudyLiterature() {
        return studyLiterature;
    }
    public void setStudyLiterature(String studyLiterature) {
        this.studyLiterature = studyLiterature;
    }
    public String getStudyDescription() {
        return studyDescription;
    }
    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }
    public Classifier getStudyContentType() {
        return studyContentType;
    }
    public void setStudyContentType(Classifier studyContentType) {
        this.studyContentType = studyContentType;
    }
    public Boolean getPublicAll() {
        return publicAll;
    }
    public void setPublicAll(Boolean publicAll) {
        this.publicAll = publicAll;
    }
    public Boolean getPublicHois() {
        return publicHois;
    }
    public void setPublicHois(Boolean publicHois) {
        this.publicHois = publicHois;
    }
    public Boolean getPublicStudent() {
        return publicStudent;
    }
    public void setPublicStudent(Boolean publicStudent) {
        this.publicStudent = publicStudent;
    }
    public LocalDateTime getConfirmed() {
        return confirmed;
    }
    public void setConfirmed(LocalDateTime confirmed) {
        this.confirmed = confirmed;
    }
    public String getConfirmedBy() {
        return confirmedBy;
    }
    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }
    public Classifier getStatus() {
        return status;
    }
    public void setStatus(Classifier status) {
        this.status = status;
    }
    public String getPassDescription() {
        return passDescription;
    }
    public void setPassDescription(String passDescription) {
        this.passDescription = passDescription;
    }
    public String getNpassDescription() {
        return npassDescription;
    }
    public void setNpassDescription(String npassDescription) {
        this.npassDescription = npassDescription;
    }
    public String getGrade0Description() {
        return grade0Description;
    }
    public void setGrade0Description(String grade0Description) {
        this.grade0Description = grade0Description;
    }
    public String getGrade1Description() {
        return grade1Description;
    }
    public void setGrade1Description(String grade1Description) {
        this.grade1Description = grade1Description;
    }
    public String getGrade2Description() {
        return grade2Description;
    }
    public void setGrade2Description(String grade2Description) {
        this.grade2Description = grade2Description;
    }
    public String getGrade3Description() {
        return grade3Description;
    }
    public void setGrade3Description(String grade3Description) {
        this.grade3Description = grade3Description;
    }
    public String getGrade4Description() {
        return grade4Description;
    }
    public void setGrade4Description(String grade4Description) {
        this.grade4Description = grade4Description;
    }
    public String getGrade5Description() {
        return grade5Description;
    }
    public void setGrade5Description(String grade5Description) {
        this.grade5Description = grade5Description;
    }
    public String getRejectInfo() {
        return rejectInfo;
    }
    public void setRejectInfo(String rejectInfo) {
        this.rejectInfo = rejectInfo;
    }
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    /**
     * @return the contents
     */
    public Set<SubjectProgramStudyContent> getStudyContents() {
        return studyContents == null ? (studyContents = new HashSet<>()) : studyContents;
    }
    /**
     * @param contents the contents to set
     */
    public void setStudyContents(Set<SubjectProgramStudyContent> contents) {
        getStudyContents().clear();
        getStudyContents().addAll(contents);
    }
    
}
