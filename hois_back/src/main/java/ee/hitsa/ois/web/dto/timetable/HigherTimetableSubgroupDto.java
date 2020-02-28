package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class HigherTimetableSubgroupDto {

    private Long id;
    private String code;
    private AutocompleteResult teacher;

    public HigherTimetableSubgroupDto(Long id, String code, AutocompleteResult teacher) {
        this.id = id;
        this.code = code;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
}
