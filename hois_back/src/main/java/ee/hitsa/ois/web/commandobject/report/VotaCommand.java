package ee.hitsa.ois.web.commandobject.report;

import ee.hitsa.ois.validation.Required;

public class VotaCommand {

    @Required
    private Long studyYear;
    private Long studyPeriod;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
}
