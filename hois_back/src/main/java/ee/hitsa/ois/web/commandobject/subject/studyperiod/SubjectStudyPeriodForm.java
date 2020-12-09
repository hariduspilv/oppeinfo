package ee.hitsa.ois.web.commandobject.subject.studyperiod;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.SubjectStudyPeriodSubgroupForm;

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
    
    private Long moodleCourseId;
    private Boolean capacityDiff;
    private Short places;
    
    private List<SubjectStudyPeriodSubgroupForm> subgroups;

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

    public Long getMoodleCourseId() {
        return moodleCourseId;
    }

    public void setMoodleCourseId(Long moodleCourseId) {
        this.moodleCourseId = moodleCourseId;
    }

    public Boolean getCapacityDiff() {
        return capacityDiff;
    }

    public void setCapacityDiff(Boolean capacityDiff) {
        this.capacityDiff = capacityDiff;
    }

    public Short getPlaces() {
        return places;
    }

    public void setPlaces(Short places) {
        this.places = places;
    }

    public List<SubjectStudyPeriodSubgroupForm> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(List<SubjectStudyPeriodSubgroupForm> subgroups) {
        this.subgroups = subgroups;
    }
}
