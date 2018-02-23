package ee.hitsa.ois.web.dto;

import java.util.Comparator;
import java.util.List;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.teacher.TeacherContinuingEducationForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherMobilityForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherQualificationForm;

public class TeacherDto extends TeacherForm {

    private Long id;
    private String fullname;
    private List<TeacherContinuingEducationForm> teacherContinuingEducations;
    private List<TeacherQualificationForm> teacherQualifications;
    private List<TeacherMobilityForm> teacherMobility;
    private Boolean canEdit;

    public static TeacherDto of(Teacher teacher) {
        TeacherDto dto = EntityUtil.bindToDto(teacher, new TeacherDto(), "person", "teacherPositionEhis", "teacherMobility");
        dto.setPerson(EntityUtil.bindToDto(teacher.getPerson(), new TeacherPersonForm()));
        dto.setTeacherPositionEhis(StreamUtil.toMappedSet(it -> EntityUtil.bindToDto(it, new TeacherDto.TeacherPositionEhisForm()), teacher.getTeacherPositionEhis()));
        dto.setTeacherContinuingEducations(StreamUtil.toMappedList(it -> EntityUtil.bindToDto(it, new TeacherContinuingEducationForm()), teacher.getTeacherContinuingEducation()));
        dto.teacherContinuingEducations.sort(Comparator.comparing(TeacherContinuingEducationForm::getDiplomaDate));
        dto.setTeacherQualifications(StreamUtil.toMappedList(it -> EntityUtil.bindToDto(it, new TeacherQualificationForm()), teacher.getTeacherQualification()));
        dto.setTeacherMobility(StreamUtil.toMappedList(it -> EntityUtil.bindToDto(it, new TeacherMobilityForm()), teacher.getTeacherMobility()));
        dto.fullname = teacher.getPerson().getFullname();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public List<TeacherContinuingEducationForm> getTeacherContinuingEducations() {
        return teacherContinuingEducations;
    }

    public void setTeacherContinuingEducations(List<TeacherContinuingEducationForm> teacherContinuingEducations) {
        this.teacherContinuingEducations = teacherContinuingEducations;
    }

    public List<TeacherQualificationForm> getTeacherQualifications() {
        return teacherQualifications;
    }

    public void setTeacherQualifications(List<TeacherQualificationForm> teacherQualifications) {
        this.teacherQualifications = teacherQualifications;
    }

    public List<TeacherMobilityForm> getTeacherMobility() {
        return teacherMobility;
    }

    public void setTeacherMobility(List<TeacherMobilityForm> teacherMobility) {
        this.teacherMobility = teacherMobility;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
}
