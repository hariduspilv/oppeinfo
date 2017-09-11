package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;

public class StudentHigherSubjectResultGradeDto {

    private String grade;
    private String gradeValue;
    private LocalDate gradeDate;
    private List<String> teachers;
    private Long studyPeriod;
    private Short gradeMark;

    public static StudentHigherSubjectResultGradeDto of(ProtocolStudent protocolStudent) {
        StudentHigherSubjectResultGradeDto dto = new StudentHigherSubjectResultGradeDto();
        dto.setGrade(EntityUtil.getNullableCode(protocolStudent.getGrade()));
        if(dto.getGrade() != null) {
            dto.setGradeValue(protocolStudent.getGrade().getValue());
            dto.setGradeDate(protocolStudent.getGradeDate());
            dto.setGradeMark(protocolStudent.getGradeMark());
        }
        SubjectStudyPeriod ssp = protocolStudent.getProtocol().getProtocolHdata().getSubjectStudyPeriod();
        dto.setTeachers(PersonUtil.sorted(ssp.getTeachers().stream().map(t -> t.getTeacher().getPerson())));
        dto.setStudyPeriod(EntityUtil.getId(ssp.getStudyPeriod()));
        return dto;
    }

    public Short getGradeMark() {
        return gradeMark;
    }

    public void setGradeMark(Short gradeMark) {
        this.gradeMark = gradeMark;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }
    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }
}
