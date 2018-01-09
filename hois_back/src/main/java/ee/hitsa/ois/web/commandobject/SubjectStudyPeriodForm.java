package ee.hitsa.ois.web.commandobject;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;

public class SubjectStudyPeriodForm  extends VersionedCommand {
    @Valid
    private List<SubjectStudyPeriodTeacherForm> teachers;
    @NotNull
    private Long studyPeriod;
    @NotNull
    private Long subject;
    
    private String addInfo;
    private List<Long> studentGroups;
    
    @ClassifierRestriction(MainClassCode.DEKLARATSIOON)
    private String declarationType;

    @Required
    @ClassifierRestriction(MainClassCode.PAEVIK_GRUPI_JAOTUS)
    private String groupProportion;

    public List<SubjectStudyPeriodTeacherForm> getTeachers() {
        return teachers != null ? teachers : (teachers = new ArrayList<>());
    }

    public List<Long> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public void setTeachers(List<SubjectStudyPeriodTeacherForm> teachers) {
        this.teachers = teachers;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getDeclarationType() {
        return declarationType;
    }

    public void setDeclarationType(String declarationType) {
        this.declarationType = declarationType;
    }

    public String getGroupProportion() {
        return groupProportion;
    }

    public void setGroupProportion(String groupProportion) {
        this.groupProportion = groupProportion;
    }
}
