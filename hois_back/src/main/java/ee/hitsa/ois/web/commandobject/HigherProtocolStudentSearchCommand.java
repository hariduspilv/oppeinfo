package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class HigherProtocolStudentSearchCommand {
    @NotNull
    private Long subjectStudyPeriod;
    @NotNull
    @ClassifierRestriction(MainClassCode.PROTOKOLLI_LIIK)
    private String protocolType;
    private Long studentGroup;

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }
    public String getProtocolType() {
        return protocolType;
    }
    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
}
