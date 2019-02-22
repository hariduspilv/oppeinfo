package ee.hitsa.ois.web.dto.report.teacherdetailload;

import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TeacherDetailLoadJournalDto extends PeriodDetailLoadDto {

    private AutocompleteResult journal;
    private Set<String> studentGroups = new HashSet<>();

    public AutocompleteResult getJournal() {
        return journal;
    }

    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
    }

    public Set<String> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<String> studentGroups) {
        this.studentGroups = studentGroups;
    }

}
