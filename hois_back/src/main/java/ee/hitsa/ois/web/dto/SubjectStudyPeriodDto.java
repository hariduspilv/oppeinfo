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
    private Long studyPeriod;
    private Long subject;
    private Long teacher;

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
    public static SubjectStudyPeriodDto of(SubjectStudyPeriod subjectStudyPeriod) {
        SubjectStudyPeriodDto dto = EntityUtil.bindToDto(subjectStudyPeriod, new SubjectStudyPeriodDto(), 
                "studyPeriod", "teacher", "subjectStudyPeriodTeachers", "subject");
        dto.setTeachers(StreamUtil.toMappedList(SubjectStudyPeriodTeacherDto::of, subjectStudyPeriod.getTeachers()));
        dto.setVersion(subjectStudyPeriod.getVersion());
        dto.setSubject(subjectStudyPeriod.getSubject().getId());
        dto.setStudyPeriod(subjectStudyPeriod.getStudyPeriod().getId());
        return dto;
    }
}
