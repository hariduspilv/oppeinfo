package ee.hitsa.ois.domain.apelapplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;

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
    
    private String confirmedBy;
    private LocalDateTime confirmed;
    
    @NotNull
    private Boolean isVocational;
    
    @OneToMany(mappedBy="apelApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationRecord> records = new ArrayList<>();
    
    @OneToMany(mappedBy="apelApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationComment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy="apelApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApelApplicationFile> files = new ArrayList<>();
    
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
    
    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public void setConfirmed(LocalDateTime confirmed) {
        this.confirmed = confirmed;
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
    
}
