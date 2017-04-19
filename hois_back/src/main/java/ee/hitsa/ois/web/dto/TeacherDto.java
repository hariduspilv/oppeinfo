package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.TeacherForm;

public class TeacherDto extends TeacherForm {

    private Long id;
    // todo start using persondto
    private String fullname;

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
        dto.setTeacherQualifications(StreamUtil.toMappedSet(it -> EntityUtil.bindToDto(it, new TeacherQualificationFrom()), teacher.getTeacherQualification()));
        dto.setTeacherMobility(StreamUtil.toMappedSet(it -> EntityUtil.bindToDto(it, new TeacherMobilityForm()), teacher.getTeacherMobility()));
        dto.fullname = teacher.getPerson().getFullname();
        return dto;
    }
}
