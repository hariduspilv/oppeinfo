package ee.hitsa.ois.web.dto.poll;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class PollResultsSubjectDto {
    
    private AutocompleteResult name;
    private String teachers;
    
    public AutocompleteResult getName() {
        return name;
    }
    public void setName(AutocompleteResult name) {
        this.name = name;
    }
    public String getTeachers() {
        return teachers;
    }
    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

}
