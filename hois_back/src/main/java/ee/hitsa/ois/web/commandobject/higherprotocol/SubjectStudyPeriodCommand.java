package ee.hitsa.ois.web.commandobject.higherprotocol;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class SubjectStudyPeriodCommand extends SearchCommand {

    private Long studyPeriodId;
    private Long studentGroupId;

    public Long getStudyPeriodId() {
        return studyPeriodId;
    }

    public void setStudyPeriodId(Long studyPeriodId) {
        this.studyPeriodId = studyPeriodId;
    }

    public Long getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }
}
