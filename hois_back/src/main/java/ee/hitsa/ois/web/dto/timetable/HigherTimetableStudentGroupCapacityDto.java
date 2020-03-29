package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.web.dto.AutocompleteResult;

import java.util.ArrayList;
import java.util.List;

public class HigherTimetableStudentGroupCapacityDto extends TimetableHigherCapacityDto {
    private AutocompleteResult studentGroup;
    private Long subjectStudyPeriod;
    private String subjectCode;
    private AutocompleteResult subject;
    private List<TimetableSubjectTeacherDto> teachers = new ArrayList<>();

    public HigherTimetableStudentGroupCapacityDto(Long studentGroupId, String studentGroupCode,
            String capacityType, Long totalPlannedLessons, String subjectCode, String subjectNameEt,
            String subjectNameEn, Long subjectStudyPeriod) {
        this.setTotalPlannedLessons(totalPlannedLessons);
        this.setCapacityType(capacityType);
        if (studentGroupId != null) {
            this.studentGroup = new AutocompleteResult(studentGroupId, studentGroupCode, studentGroupCode);
        }
        this.subjectCode = subjectCode;
        this.subject = new AutocompleteResult(null, subjectNameEt, subjectNameEn);
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(AutocompleteResult studentGroup) {
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

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public List<TimetableSubjectTeacherDto> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TimetableSubjectTeacherDto> teachers) {
        this.teachers = teachers;
    }

}
