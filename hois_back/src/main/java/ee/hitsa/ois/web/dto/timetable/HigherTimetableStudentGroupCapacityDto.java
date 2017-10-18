package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

public class HigherTimetableStudentGroupCapacityDto extends TimetableStudentGroupCapacityDto {
    private Long subjectStudyPeriod;
    private String subjectCode;
    private String subjectName;
    private List<String> teacherNames = new ArrayList<>();

    public HigherTimetableStudentGroupCapacityDto(Long studentGroup, Long totalPlannedLessons, String capacityType,
            String subjectCode, String subjectName, Long subjectStudyPeriod) {
        super(studentGroup, totalPlannedLessons, capacityType);
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectStudyPeriod = subjectStudyPeriod;
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

    public List<String> getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(List<String> teacherNames) {
        this.teacherNames = teacherNames;
    }

}
