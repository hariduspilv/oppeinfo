package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.Teacher;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.TeacherForm;

import java.util.stream.Collectors;

public class TeacherDto extends TeacherForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // todo start using persondto
    private String fullname;

    public static TeacherDto of(Teacher teacher) {
        TeacherDto dto = EntityUtil.bindToDto(teacher, new TeacherDto());
        dto.setPerson(EntityUtil.bindToDto(teacher.getPerson(), new TeacherPersonForm()));
        dto.setTeacherPositionEhis(teacher.getTeacherPositionEhis().stream()
                .map(it -> EntityUtil.bindToDto(it, new TeacherDto.TeacherPositionEhisForm())).collect(Collectors.toSet()));
        dto.setTeacherQualifications(teacher.getTeacherQualification().stream()
                .map(it -> EntityUtil.bindToDto(it, new TeacherQualificationFrom())).collect(Collectors.toSet()));
        dto.setTeacherMobility(teacher.getTeacherMobility().stream()
                .map(it -> EntityUtil.bindToDto(it, new TeacherMobilityForm())).collect(Collectors.toSet()));
        dto.fullname = teacher.getPerson().getFullname();
        return dto;
    }

    public String getFullname() {
        return fullname;
    }
}
