package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class VocationalTimetablePlanDto extends TimetablePlanDto {
    private List<TimetableJournalDto> journals;
    private List<TimetableStudentGroupDto> studentGroups;

    public List<TimetableJournalDto> getJournals() {
        return journals;
    }

    public void setJournals(List<TimetableJournalDto> journals) {
        this.journals = journals;
    }

    public List<TimetableStudentGroupDto> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<TimetableStudentGroupDto> studentGroups) {
        this.studentGroups = studentGroups;
    }
}
