package ee.hitsa.ois.domain.curriculum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;

@Entity
@Table(name="curriculum_version_omodule_year_capacity")
public class CurriculumVersionOccupationModuleYearCapacity extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false, updatable = false, insertable = false)
    private CurriculumVersionOccupationModule module;

    @Column(nullable = false)
    private Integer studyYearNumber;

    @Column(nullable = false)
    private Double credits;

    public Integer getStudyYearNumber() {
        return studyYearNumber;
    }

    public void setStudyYearNumber(Integer studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public CurriculumVersionOccupationModule getModule() {
        return module;
    }

    public void setModule(CurriculumVersionOccupationModule module) {
        this.module = module;
    }

}
