package ee.hitsa.ois.web.commandobject.schoolcapacity;

public class SchoolCapacityTypeLoadForm {

    private Long studyYearId;
    private Integer loadPercentage;
    
    public Long getStudyYearId() {
        return studyYearId;
    }
    public void setStudyYearId(Long studyYearId) {
        this.studyYearId = studyYearId;
    }
    
    public Integer getLoadPercentage() {
        return loadPercentage;
    }
    public void setLoadPercentage(Integer loadPercentage) {
        this.loadPercentage = loadPercentage;
    }
    
}
