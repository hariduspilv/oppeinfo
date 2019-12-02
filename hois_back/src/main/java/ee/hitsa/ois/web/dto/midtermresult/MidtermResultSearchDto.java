package ee.hitsa.ois.web.dto.midtermresult;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class MidtermResultSearchDto {

    private Long id;
    private AutocompleteResult subject;
    private List<String> teachers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }
}
