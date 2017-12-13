package ee.hitsa.ois.web.dto;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.teacher.TeacherContinuingEducationForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherMobilityForm;
import ee.hitsa.ois.web.commandobject.teacher.TeacherQualificationForm;

public class TeacherDto extends TeacherForm {

    private Long id;
    // TODO start using persondto
    private String fullname;
    
    private List<TeacherContinuingEducationForm> teacherContinuingEducations;

    private Set<TeacherQualificationForm> teacherQualifications;

    private Set<TeacherMobilityForm> teacherMobility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public static TeacherDto of(Teacher teacher) {
        TeacherDto dto = EntityUtil.bindToDto(teacher, new TeacherDto());
        dto.setPerson(EntityUtil.bindToDto(teacher.getPerson(), new TeacherPersonForm()));
        dto.setTeacherPositionEhis(StreamUtil.toMappedSet(it -> EntityUtil.bindToDto(it, new TeacherDto.TeacherPositionEhisForm()), teacher.getTeacherPositionEhis()));
        dto.setTeacherContinuingEducations(StreamUtil.toMappedList(it -> EntityUtil.bindToDto(it, new TeacherContinuingEducationForm()), teacher.getTeacherContinuingEducation()));
        dto.teacherContinuingEducations.sort(Comparator.comparing(TeacherContinuingEducationForm::getDiplomaDate));
        dto.setTeacherQualifications(StreamUtil.toMappedSet(it -> EntityUtil.bindToDto(it, new TeacherQualificationForm()), teacher.getTeacherQualification()));
        dto.setTeacherMobility(StreamUtil.toMappedSet(it -> EntityUtil.bindToDto(it, new TeacherMobilityForm()), teacher.getTeacherMobility()));
        dto.fullname = teacher.getPerson().getFullname();
        return dto;
    }
    
    public List<TeacherContinuingEducationForm> getTeacherContinuingEducations() {
        return teacherContinuingEducations;
    }

    public void setTeacherContinuingEducations(List<TeacherContinuingEducationForm> teacherContinuingEducations) {
        this.teacherContinuingEducations = teacherContinuingEducations;
    }

    public Set<TeacherQualificationForm> getTeacherQualifications() {
        return teacherQualifications;
    }

    public void setTeacherQualifications(Set<TeacherQualificationForm> teacherQualifications) {
        this.teacherQualifications = teacherQualifications;
    }

    public Set<TeacherMobilityForm> getTeacherMobility() {
        return teacherMobility;
    }

    public void setTeacherMobility(Set<TeacherMobilityForm> teacherMobility) {
        this.teacherMobility = teacherMobility;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
}
