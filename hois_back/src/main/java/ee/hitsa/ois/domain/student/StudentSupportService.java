package ee.hitsa.ois.domain.student;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;

@Entity
public class StudentSupportService extends BaseEntityWithId {

    private LocalDate entryDate;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;
    private String nameEt;
    private String content;
    private Boolean isPublic;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier validity;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private OisFile oisFile;
    @Column(name = "is_ehis")
    private Boolean ehis;

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Classifier getValidity() {
        return validity;
    }

    public void setValidity(Classifier validity) {
        this.validity = validity;
    }

    public OisFile getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFile oisFile) {
        this.oisFile = oisFile;
    }

    public Boolean getEhis() {
        return ehis;
    }

    public void setEhis(Boolean ehis) {
        this.ehis = ehis;
    }
}
