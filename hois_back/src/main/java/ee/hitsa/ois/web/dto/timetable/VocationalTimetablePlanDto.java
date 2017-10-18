package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class VocationalTimetablePlanDto extends TimetablePlanDto {
    private List<TimetableStudentGroupCapacityDto> studentGroupCapacities;
    private List<TimetableJournalDto> journals;
    private List<TimetableStudentGroupDto> studentGroups;

    public List<TimetableStudentGroupCapacityDto> getStudentGroupCapacities() {
        return studentGroupCapacities;
    }

    public void setStudentGroupCapacities(List<TimetableStudentGroupCapacityDto> studentGroupCapacities) {
        this.studentGroupCapacities = studentGroupCapacities;
    }

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
