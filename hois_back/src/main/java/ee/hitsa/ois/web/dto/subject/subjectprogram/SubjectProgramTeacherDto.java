package ee.hitsa.ois.web.dto.subject.subjectprogram;

import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramTeacher;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

import java.time.LocalDateTime;

public class SubjectProgramTeacherDto extends VersionedCommand {

    public Long id;
    public AutocompleteResult teacher;
    public Boolean isReady;
    public LocalDateTime isReadyDt;
    public Long teacherId;

    public static SubjectProgramTeacherDto of(SubjectProgramTeacher programTeacher) {
        SubjectProgramTeacherDto dto = EntityUtil.bindToDto(programTeacher, new SubjectProgramTeacherDto(),
                "subjectStudyPeriodTeacher", "subjectProgram");
        AutocompleteResult teacherDto = AutocompleteResult.of(programTeacher.getSubjectStudyPeriodTeacher().getTeacher());
        teacherDto.setId(programTeacher.getSubjectStudyPeriodTeacher().getId());
        dto.setTeacher(teacherDto);
        dto.setTeacherId(programTeacher.getSubjectStudyPeriodTeacher().getTeacher().getId());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public Boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public LocalDateTime getIsReadyDt() {
        return isReadyDt;
    }

    public void setIsReadyDt(LocalDateTime isReadyDt) {
        this.isReadyDt = isReadyDt;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
