package ee.hitsa.ois.domain.schoolcapacity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.StudyYear;

@Entity
public class SchoolCapacityTypeLoad extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private SchoolCapacityType schoolCapacityType;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyYear studyYear;
    @Column(nullable = false)
    private Integer loadPercentage;
    
    public SchoolCapacityType getSchoolCapacityType() {
        return schoolCapacityType;
    }
    public void setSchoolCapacityType(SchoolCapacityType schoolCapacityType) {
        this.schoolCapacityType = schoolCapacityType;
    }
    
    public StudyYear getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }
    
    public Integer getLoadPercentage() {
        return loadPercentage;
    }
    public void setLoadPercentage(Integer loadPercentage) {
        this.loadPercentage = loadPercentage;
    }
    
}
