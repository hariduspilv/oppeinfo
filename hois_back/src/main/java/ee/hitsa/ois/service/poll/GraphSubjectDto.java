package ee.hitsa.ois.service.poll;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class GraphSubjectDto {
    
    private AutocompleteResult subjectOrJournal;
    private List<GraphTeacherDto> teachers = new ArrayList<>();
    
    public AutocompleteResult getSubjectOrJournal() {
        return subjectOrJournal;
    }
    public void setSubjectOrJournal(AutocompleteResult subjectOrJournal) {
        this.subjectOrJournal = subjectOrJournal;
    }
    public List<GraphTeacherDto> getTeachers() {
        return teachers;
    }
    public void setTeachers(List<GraphTeacherDto> teachers) {
        this.teachers = teachers;
    }
}
