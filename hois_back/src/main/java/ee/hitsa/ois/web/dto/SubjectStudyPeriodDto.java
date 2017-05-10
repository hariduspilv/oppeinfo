package ee.hitsa.ois.web.dto;

import java.util.List;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class SubjectStudyPeriodDto extends VersionedCommand {
    /*
     * Class does not extend SubjectStudyPeriodForm as teachers' list there is of different type
     */
    private Long id;
    private List<SubjectStudyPeriodTeacherDto> teachers;
    private List<SubjectStudyPeriodCapacityDto> capacities;
    private Long studyPeriod;
    private Long subject;
    private Long teacher;
    private String addInfo;
    private String declarationType;
    private String groupProportion;
    private List<Long> studentGroups;

    public List<SubjectStudyPeriodCapacityDto> getCapacities() {
        return capacities;
    }
    public void setCapacities(List<SubjectStudyPeriodCapacityDto> capacities) {
        this.capacities = capacities;
    }
    public List<Long> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<SubjectStudyPeriodTeacherDto> getTeachers() {
        return teachers;
    }
    public void setTeachers(List<SubjectStudyPeriodTeacherDto> teachers) {
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
    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
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
    public static SubjectStudyPeriodDto of(SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodDto dto = EntityUtil.bindToDto(subjectStudyPeriod, new SubjectStudyPeriodDto(), 
                "studyPeriod", "teacher", "subjectStudyPeriodTeachers", "subject", "capacities");
        dto.setTeachers(StreamUtil.toMappedList(SubjectStudyPeriodTeacherDto::of, subjectStudyPeriod.getTeachers()));
        dto.setVersion(subjectStudyPeriod.getVersion());
        dto.setSubject(subjectStudyPeriod.getSubject().getId());
        dto.setStudyPeriod(subjectStudyPeriod.getStudyPeriod().getId());
        dto.setAddInfo(subjectStudyPeriod.getAddInfo());
        dto.setDeclarationType(EntityUtil.getNullableCode(subjectStudyPeriod.getDeclarationType()));
        dto.setGroupProportion(EntityUtil.getNullableCode(subjectStudyPeriod.getGroupProportion()));
        dto.setStudentGroups(StreamUtil.toMappedList(sg -> {
            return EntityUtil.getId(sg.getStudentGroup());
        }, subjectStudyPeriod.getStudentGroups()));
        return dto;
    }
}
