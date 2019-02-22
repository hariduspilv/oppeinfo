package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TeacherDetailLoadDto extends PeriodDetailLoadDto {

    private AutocompleteResult teacher;

    private List<TeacherDetailLoadJournalDto> journals = new ArrayList<>();
    private Set<String> teacherCapacities = new HashSet<>();

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public List<TeacherDetailLoadJournalDto> getJournals() {
        return journals;
    }

    public void setJournals(List<TeacherDetailLoadJournalDto> journals) {
        this.journals = journals;
    }

    public Set<String> getTeacherCapacities() {
        return teacherCapacities;
    }

    public void setTeacherCapacities(Set<String> teacherCapacities) {
        this.teacherCapacities = teacherCapacities;
    }

}
