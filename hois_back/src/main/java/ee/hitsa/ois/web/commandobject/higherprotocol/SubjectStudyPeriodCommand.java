package ee.hitsa.ois.web.commandobject.higherprotocol;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class SubjectStudyPeriodCommand extends SearchCommand {

    private Long studyPeriodId;

    public Long getStudyPeriodId() {
        return studyPeriodId;
    }

    public void setStudyPeriodId(Long studyPeriodId) {
        this.studyPeriodId = studyPeriodId;
    }
}
