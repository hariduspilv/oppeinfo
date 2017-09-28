package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

public class HigherTimetablePlanDto extends TimetablePlanDto {
    private List<SubjectTeacherPairDto> subjectTeacherPairs;
    private List<HigherTimetableStudentGroupDto> studentGroups;
    private List<DateRangeDto> weeks;

    public List<SubjectTeacherPairDto> getSubjectTeacherPairs() {
        return subjectTeacherPairs;
    }

    public void setSubjectTeacherPairs(List<SubjectTeacherPairDto> subjectTeacherPairs) {
        this.subjectTeacherPairs = subjectTeacherPairs;
    }

    public List<HigherTimetableStudentGroupDto> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<HigherTimetableStudentGroupDto> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<DateRangeDto> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<DateRangeDto> weeks) {
        this.weeks = weeks;
    }

}
