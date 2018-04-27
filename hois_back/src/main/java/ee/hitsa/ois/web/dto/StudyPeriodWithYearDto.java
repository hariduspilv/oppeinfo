package ee.hitsa.ois.web.dto;

public class StudyPeriodWithYearDto extends StudyPeriodDto {
    
    private StudyYearSearchDto studyYear;

    public StudyYearSearchDto getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(StudyYearSearchDto studyYear) {
        this.studyYear = studyYear;
    }
    
}
