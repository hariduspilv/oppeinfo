package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TeacherDetailLoadJournalSubjectDto extends PeriodDetailLoadDto {

    private AutocompleteResult journalSubject;
    private Set<String> studentGroups = new HashSet<>();

    public AutocompleteResult getJournalSubject() {
        return journalSubject;
    }

    public void setJournalSubject(AutocompleteResult journalSubject) {
        this.journalSubject = journalSubject;
    }

    public Set<String> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<String> studentGroups) {
        this.studentGroups = studentGroups;
    }

}
