package ee.hitsa.ois.web.dto.timetable;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class HigherTimetablePlanDto extends TimetablePlanDto {
    private List<SubjectTeacherPairDto> subjectTeacherPairs;
    private List<HigherTimetableStudentGroupCapacityDto> studentGroupCapacities;
    private List<HigherTimetableStudentGroupDto> studentGroups;
    private List<AutocompleteResult> buildings;
    private List<DateRangeDto> weeks;

    public List<SubjectTeacherPairDto> getSubjectTeacherPairs() {
        return subjectTeacherPairs;
    }

    public void setSubjectTeacherPairs(List<SubjectTeacherPairDto> subjectTeacherPairs) {
        this.subjectTeacherPairs = subjectTeacherPairs;
    }

    public List<HigherTimetableStudentGroupCapacityDto> getStudentGroupCapacities() {
        return studentGroupCapacities;
    }

    public void setStudentGroupCapacities(List<HigherTimetableStudentGroupCapacityDto> studentGroupCapacities) {
        this.studentGroupCapacities = studentGroupCapacities;
    }

    public List<HigherTimetableStudentGroupDto> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<HigherTimetableStudentGroupDto> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<AutocompleteResult> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<AutocompleteResult> buildings) {
        this.buildings = buildings;
    }

    public List<DateRangeDto> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<DateRangeDto> weeks) {
        this.weeks = weeks;
    }

}
