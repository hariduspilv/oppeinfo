package ee.hitsa.ois.web.commandobject;

public class SubjectStudyPeriodCommand extends SearchCommand {
    
    private Long studyPeriod;

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

}
