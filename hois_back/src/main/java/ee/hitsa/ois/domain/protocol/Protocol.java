package ee.hitsa.ois.domain.protocol;

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
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;

@Entity
public class Protocol extends BaseEntityWithId {

    @NotNull
    private Boolean isVocational;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    @NotNull
    private String protocolNr;
    private LocalDate confirmDate;
    private String confirmer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "protocol_id", nullable = false, updatable = false)
    private List<ProtocolStudent> protocolStudents = new ArrayList<>();

    @OneToOne(mappedBy = "protocol", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, updatable = false)
    private ProtocolVdata protocolVdata;
    
    @OneToOne(mappedBy = "protocol", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = false, updatable = false)
    private ProtocolHdata protocolHdata;

    public ProtocolHdata getProtocolHdata() {
        return protocolHdata;
    }

    public void setProtocolHdata(ProtocolHdata protocolHdata) {
        this.protocolHdata = protocolHdata;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
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

    public String getProtocolNr() {
        return protocolNr;
    }

    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public List<ProtocolStudent> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<ProtocolStudent> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

    public ProtocolVdata getProtocolVdata() {
        return protocolVdata;
    }

    public void setProtocolVdata(ProtocolVdata protocolVdata) {
        this.protocolVdata = protocolVdata;
    }
}
