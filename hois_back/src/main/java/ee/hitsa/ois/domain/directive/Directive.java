package ee.hitsa.ois.domain.directive;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.school.School;

@Entity
public class Directive extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @Column(nullable = false)
    private String headline;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Classifier type;
    private String directiveNr;
    private LocalDate confirmDate;
    private String addInfo;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier status;
    @ManyToOne(fetch = FetchType.LAZY)
    private DirectiveCoordinator directiveCoordinator;
    private LocalDate ekisDate;
    private String preamble;
    private Long wdId;
    private String confirmer;
    private Boolean isHigher;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Directive canceledDirective;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier cancelType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private Classifier scholarshipType;
    @OneToMany(mappedBy = "directive", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DirectiveStudent> students;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Classifier getType() {
        return type;
    }

    public void setType(Classifier type) {
        this.type = type;
    }

    public String getDirectiveNr() {
        return directiveNr;
    }

    public void setDirectiveNr(String directiveNr) {
        this.directiveNr = directiveNr;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public DirectiveCoordinator getDirectiveCoordinator() {
        return directiveCoordinator;
    }

    public void setDirectiveCoordinator(DirectiveCoordinator directiveCoordinator) {
        this.directiveCoordinator = directiveCoordinator;
    }

    public LocalDate getEkisDate() {
        return ekisDate;
    }

    public void setEkisDate(LocalDate ekisDate) {
        this.ekisDate = ekisDate;
    }

    public String getPreamble() {
        return preamble;
    }

    public void setPreamble(String preamble) {
        this.preamble = preamble;
    }

    public Long getWdId() {
        return wdId;
    }

    public void setWdId(Long wdId) {
        this.wdId = wdId;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public Directive getCanceledDirective() {
        return canceledDirective;
    }

    public void setCanceledDirective(Directive canceledDirective) {
        this.canceledDirective = canceledDirective;
    }

    public Classifier getCancelType() {
        return cancelType;
    }

    public void setCancelType(Classifier cancelType) {
        this.cancelType = cancelType;
    }

    public Classifier getScholarshipType() {
        return scholarshipType;
    }

    public void setScholarshipType(Classifier scholarshipType) {
        this.scholarshipType = scholarshipType;
    }

    public List<DirectiveStudent> getStudents() {
        return students;
    }

    public void setStudents(List<DirectiveStudent> students) {
        this.students = students;
    }
}
