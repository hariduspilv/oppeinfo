package ee.hitsa.ois.web.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import ee.hitsa.ois.validation.Required;

public class SubjectStudyPeriodSubgroupForm {

    private Long id;
    
    @Required
    private String code;
    
    private AutocompleteResult teacher;
    
    @Required
    @Max(999)
    @Min(0)
    private Short places;

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

    public Short getPlaces() {
        return places;
    }

    public void setPlaces(Short places) {
        this.places = places;
    }
}
