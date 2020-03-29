package ee.hitsa.ois.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.util.SubjectStudyPeriodUtil;
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
    private String addInfo;
    private String declarationType;
    private String groupProportion;
    private List<Long> studentGroups;
    private List<AutocompleteResult> studentGroupObjects;
    private Long moodleCourseId;
    private Boolean capacityDiff;
    private Set<SubjectStudyPeriodSubgroupDto> subgroups;
    private Long students;

    private Boolean canUpdate;
    private Boolean canEditSubgroups;
    private Boolean canDelete;

    public List<AutocompleteResult> getStudentGroupObjects() {
        return studentGroupObjects != null ? studentGroupObjects : (studentGroupObjects = new ArrayList<>());
    }

    public void setStudentGroupObjects(List<AutocompleteResult> studentGroupObjects) {
        this.studentGroupObjects = studentGroupObjects;
    }

    public List<SubjectStudyPeriodCapacityDto> getCapacities() {
        return capacities != null ? capacities : (capacities = new ArrayList<>());
    }

    public void setCapacities(List<SubjectStudyPeriodCapacityDto> capacities) {
        this.capacities = capacities;
    }

    public List<Long> getStudentGroups() {
        return studentGroups != null ? studentGroups : (studentGroups = new ArrayList<>());
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
        return teachers != null ? teachers : (teachers = new ArrayList<>());
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

    public Set<SubjectStudyPeriodSubgroupDto> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(Set<SubjectStudyPeriodSubgroupDto> subgroups) {
        this.subgroups = subgroups;
    }

    public Long getStudents() {
        return students;
    }

    public void setStudents(Long students) {
        this.students = students;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public Boolean getCanEditSubgroups() {
        return canEditSubgroups;
    }

    public void setCanEditSubgroups(Boolean canEditSubgroups) {
        this.canEditSubgroups = canEditSubgroups;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public static SubjectStudyPeriodDto of(SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodDto dto = EntityUtil.bindToDto(subjectStudyPeriod, new SubjectStudyPeriodDto(), 
                "studyPeriod", "teacher", "subjectStudyPeriodTeachers", "subject", "capacities", "subgroups");
        dto.setTeachers(StreamUtil.toMappedList(SubjectStudyPeriodTeacherDto::of, subjectStudyPeriod.getTeachers()));
        dto.setVersion(subjectStudyPeriod.getVersion());
        dto.setSubject(EntityUtil.getId(subjectStudyPeriod.getSubject()));
        dto.setStudyPeriod(EntityUtil.getId(subjectStudyPeriod.getStudyPeriod()));
        dto.setAddInfo(subjectStudyPeriod.getAddInfo());
        dto.setDeclarationType(EntityUtil.getNullableCode(subjectStudyPeriod.getDeclarationType()));
        dto.setGroupProportion(EntityUtil.getNullableCode(subjectStudyPeriod.getGroupProportion()));
        dto.setStudentGroups(StreamUtil.toMappedList(sg -> EntityUtil.getId(sg.getStudentGroup()), subjectStudyPeriod.getStudentGroups()));
        dto.setSubgroups(StreamUtil.toMappedSet(sg -> SubjectStudyPeriodSubgroupDto.of(sg), subjectStudyPeriod.getSubgroups()));
        dto.setCanEditSubgroups(Boolean.valueOf(SubjectStudyPeriodUtil.canEditSubgroups(subjectStudyPeriod)));
        dto.setStudents(Long.valueOf(subjectStudyPeriod.getStudentGroups().stream()
                .map(SubjectStudyPeriodStudentGroup::getStudentGroup)
                .flatMap(sg -> sg.getStudents().stream())
                .filter(StudentUtil::isActive)
                .count()));
        return dto;
    }
}
