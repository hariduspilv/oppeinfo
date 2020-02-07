package ee.hitsa.ois.web.dto.poll;

public class SubjectAndSubjectStudyPeriod {
    
    private Long subjectId;
    private Long subjectStudyPeriodId;
    
    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    public Long getSubjectStudyPeriodId() {
        return subjectStudyPeriodId;
    }
    public void setSubjectStudyPeriodId(Long subjectStudyPeriodId) {
        this.subjectStudyPeriodId = subjectStudyPeriodId;
    }

}
