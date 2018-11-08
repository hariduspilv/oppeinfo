package ee.hitsa.ois.web.dto.timetable;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.timetable.TimetableEventTeacher;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TimetableSingleEventTeacherForm {

    private Long id;
    @NotNull
    private EntityConnectionCommand teacher;
    private Boolean isSubstitute;

    public TimetableSingleEventTeacherForm() {
        
    }

    public TimetableSingleEventTeacherForm(Long id, Long teacherId, String nameEt, String nameEn,
            Boolean isSubstitute) {
        this.id = id;
        this.teacher = new AutocompleteResult(teacherId, nameEt, nameEn);
        this.isSubstitute = isSubstitute;
    }

    public static TimetableSingleEventTeacherForm of(TimetableEventTeacher eventTeacher) {
        TimetableSingleEventTeacherForm dto = new TimetableSingleEventTeacherForm();
        dto.setId(eventTeacher.getId());
        dto.setTeacher(AutocompleteResult.of(eventTeacher.getTeacher()));
        dto.setIsSubstitute(eventTeacher.getIsSubstitute());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityConnectionCommand getTeacher() {
        return teacher;
    }

    public void setTeacher(EntityConnectionCommand teacher) {
        this.teacher = teacher;
    }

    public Boolean getIsSubstitute() {
        return isSubstitute;
    }

    public void setIsSubstitute(Boolean isSubstitute) {
        this.isSubstitute = isSubstitute;
    }

}
