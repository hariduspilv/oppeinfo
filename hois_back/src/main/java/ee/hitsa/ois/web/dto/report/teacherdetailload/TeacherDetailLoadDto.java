package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TeacherDetailLoadDto extends PeriodDetailLoadDto {

    private AutocompleteResult teacher;

    private List<TeacherDetailLoadJournalSubjectDto> journalSubjects = new ArrayList<>();
    private Set<String> teacherCapacities = new HashSet<>();

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }

    public List<TeacherDetailLoadJournalSubjectDto> getJournalSubjects() {
        return journalSubjects;
    }

    public void setJournalSubjects(List<TeacherDetailLoadJournalSubjectDto> journalSubjects) {
        this.journalSubjects = journalSubjects;
    }

    public Set<String> getTeacherCapacities() {
        return teacherCapacities;
    }

    public void setTeacherCapacities(Set<String> teacherCapacities) {
        this.teacherCapacities = teacherCapacities;
    }

}
