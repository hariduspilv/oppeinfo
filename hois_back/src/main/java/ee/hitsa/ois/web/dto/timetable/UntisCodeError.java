package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class UntisCodeError {
    
    private List<String> teachers;
    
    private List<String> journals;

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public List<String> getJournals() {
        return journals;
    }

    public void setJournals(List<String> journals) {
        this.journals = journals;
    }

}
