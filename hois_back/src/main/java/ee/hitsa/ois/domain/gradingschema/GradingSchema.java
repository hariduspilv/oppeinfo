package ee.hitsa.ois.domain.gradingschema;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.school.School;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GradingSchema extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    @Column(updatable = false)
    private Boolean isVocational = Boolean.FALSE;
    @Column(updatable = false)
    private Boolean isHigher = Boolean.FALSE;
    @Column(updatable = false)
    private Boolean isBasic = Boolean.FALSE;
    @Column(updatable = false)
    private Boolean isSecondary = Boolean.FALSE;
    private Boolean isVerbal = Boolean.FALSE;
    private Boolean isGrade = Boolean.FALSE;

    @OneToMany(mappedBy="gradingSchema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradingSchemaRow> gradingSchemaRows = new ArrayList<>();

    @OneToMany(mappedBy="gradingSchema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradingSchemaStudyYear> gradingSchemaStudyYears = new ArrayList<>();

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public Boolean getIsBasic() {
        return isBasic;
    }

    public void setIsBasic(Boolean isBasic) {
        this.isBasic = isBasic;
    }

    public Boolean getIsSecondary() {
        return isSecondary;
    }

    public void setIsSecondary(Boolean isSecondary) {
        this.isSecondary = isSecondary;
    }

    public Boolean getIsVerbal() {
        return isVerbal;
    }

    public void setIsVerbal(Boolean isVerbal) {
        this.isVerbal = isVerbal;
    }

    public Boolean getIsGrade() {
        return isGrade;
    }

    public void setIsGrade(Boolean isGrade) {
        this.isGrade = isGrade;
    }

    public List<GradingSchemaRow> getGradingSchemaRows() {
        return gradingSchemaRows;
    }

    public void setGradingSchemaRows(List<GradingSchemaRow> gradingSchemaRows) {
        this.gradingSchemaRows = gradingSchemaRows;
    }

    public List<GradingSchemaStudyYear> getGradingSchemaStudyYears() {
        return gradingSchemaStudyYears;
    }

    public void setGradingSchemaStudyYears(List<GradingSchemaStudyYear> gradingSchemaStudyYears) {
        this.gradingSchemaStudyYears = gradingSchemaStudyYears;
    }
}
