package ee.hitsa.ois.web.dto.studymaterial;

import java.util.List;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectStudyPeriodDto {

    private Long id;
    private AutocompleteResult subject;
    private AutocompleteResult studyPeriod;
    private List<AutocompleteResult> teachers;
    private Boolean canConnectStudyMaterials;

    public static SubjectStudyPeriodDto of(SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodDto dto = EntityUtil.bindToDto(subjectStudyPeriod, new SubjectStudyPeriodDto(), "teachers",
                "studyPeriod");
        dto.setStudyPeriod(AutocompleteResult.ofWithYear(subjectStudyPeriod.getStudyPeriod()));
        dto.setTeachers(StreamUtil.toMappedList(sspt -> AutocompleteResult.of(sspt.getTeacher()),
                subjectStudyPeriod.getTeachers()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public List<AutocompleteResult> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<AutocompleteResult> teachers) {
        this.teachers = teachers;
    }

    public Boolean getCanConnectStudyMaterials() {
        return canConnectStudyMaterials;
    }

    public void setCanConnectStudyMaterials(Boolean canConnectStudyMaterials) {
        this.canConnectStudyMaterials = canConnectStudyMaterials;
    }

}
