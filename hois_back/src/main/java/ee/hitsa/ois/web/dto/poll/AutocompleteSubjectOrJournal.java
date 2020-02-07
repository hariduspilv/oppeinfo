package ee.hitsa.ois.web.dto.poll;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class AutocompleteSubjectOrJournal extends AutocompleteResult {
    
    private List<AutocompleteTeacher> teachers = new ArrayList<>();

    public AutocompleteSubjectOrJournal(Long id, String nameEt, String nameEn) {
        super(id, nameEt, nameEn);
    }

    public List<AutocompleteTeacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<AutocompleteTeacher> teachers) {
        this.teachers = teachers;
    }
            
}
