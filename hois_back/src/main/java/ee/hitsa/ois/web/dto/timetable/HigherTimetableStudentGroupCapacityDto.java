package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class HigherTimetableStudentGroupCapacityDto extends TimetableCapacityDto {
    private Long studentGroup;
    private Long subjectStudyPeriod;
    private String subjectCode;
    private String subjectName;
    private List<AutocompleteResult> teachers = new ArrayList<>();

    public HigherTimetableStudentGroupCapacityDto(Long studentGroup, Long totalPlannedLessons, String capacityType,
            String subjectCode, String subjectName, Long subjectStudyPeriod) {
        this.setTotalPlannedLessons(totalPlannedLessons);
        this.setCapacityType(capacityType);
        this.studentGroup = studentGroup;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<AutocompleteResult> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<AutocompleteResult> teachers) {
        this.teachers = teachers;
    }

}
