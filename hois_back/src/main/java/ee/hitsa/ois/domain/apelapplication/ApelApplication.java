package ee.hitsa.ois.domain.apelapplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentCurriculumModuleOutcomesResult;

@Entity
public class ApelApplication extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Student student;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private School school;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;

    private LocalDate submitted;
    private String submittedBy;
    private LocalDateTime confirmed;
    private String confirmedBy;
    private Boolean isVocational;
    private String decision;

    @ManyToOne(fetch = FetchType.LAZY)
    private Committee committee;

    private LocalDate newNominalStudyEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier nominalType;

    private LocalDate oldNominalStudyEnd;
    private Boolean isEhisSent;

    @OneToMany(mappedBy="apelApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationRecord> records = new ArrayList<>();
    
    @OneToMany(mappedBy="apelApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationComment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy="apelApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationFile> files = new ArrayList<>();

    @OneToMany(mappedBy="apelApplication", cascade = CascadeType.ALL)
    private List<StudentCurriculumModuleOutcomesResult> outcomeResults = new ArrayList<>();

    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public School getSchool() {
        return school;
    }
    
    public void setSchool(School school) {
        this.school = school;
    }
    
    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }
    
    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }
    
    public LocalDateTime getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(LocalDateTime confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Committee getCommittee() {
        return committee;
    }

    public void setCommittee(Committee committee) {
        this.committee = committee;
    }

    public LocalDate getNewNominalStudyEnd() {
        return newNominalStudyEnd;
    }

    public void setNewNominalStudyEnd(LocalDate newNominalStudyEnd) {
        this.newNominalStudyEnd = newNominalStudyEnd;
    }

    public Classifier getNominalType() {
        return nominalType;
    }

    public void setNominalType(Classifier nominalType) {
        this.nominalType = nominalType;
    }

    public LocalDate getOldNominalStudyEnd() {
        return oldNominalStudyEnd;
    }

    public void setOldNominalStudyEnd(LocalDate oldNominalStudyEnd) {
        this.oldNominalStudyEnd = oldNominalStudyEnd;
    }

    public Boolean getIsEhisSent() {
        return isEhisSent;
    }

    public void setIsEhisSent(Boolean isEhisSent) {
        this.isEhisSent = isEhisSent;
    }

    public List<ApelApplicationRecord> getRecords() {
        return records;
    }

    public void setRecords(List<ApelApplicationRecord> records) {
        this.records = records;
    }
    
    public List<ApelApplicationComment> getComments() {
        return comments;
    }

    public void setComments(List<ApelApplicationComment> comments) {
        this.comments = comments;
    }

    public List<ApelApplicationFile> getFiles() {
        return files;
    }

    public void setFiles(List<ApelApplicationFile> files) {
        this.files = files;
    }

    public List<StudentCurriculumModuleOutcomesResult> getOutcomeResults() {
        return outcomeResults;
    }

    public void setOutcomeResults(List<StudentCurriculumModuleOutcomesResult> outcomeResults) {
        this.outcomeResults = outcomeResults;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public LocalDate getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDate submitted) {
        this.submitted = submitted;
    }
}
