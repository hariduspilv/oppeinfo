package ee.hitsa.ois.web.dto.student;

import java.time.LocalDate;
import java.util.Set;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;

public class StudentHigherSubjectResultGradeDto {

    private String grade;
    private String gradeValue;
    private LocalDate gradeDate;
    private Set<String> teachers;
    private Long studyPeriod;
    private Integer gradeMark;
    
    public static StudentHigherSubjectResultGradeDto of(ProtocolStudent protocolStudent) {
        StudentHigherSubjectResultGradeDto dto = new StudentHigherSubjectResultGradeDto();
        dto.setGrade(EntityUtil.getNullableCode(protocolStudent.getGrade()));
        if(dto.getGrade() != null) {
            dto.setGradeValue(protocolStudent.getGrade().getValue());
            dto.setGradeDate(protocolStudent.getGradeDate());
            dto.setGradeMark(protocolStudent.getGradeMark());
        }
        SubjectStudyPeriod ssp = protocolStudent.getProtocol().getProtocolHdata().getSubjectStudyPeriod();
        dto.setTeachers(StreamUtil.toMappedSet(t -> PersonUtil.fullname(t.getTeacher().getPerson())
                , ssp.getTeachers()));
        dto.setStudyPeriod(EntityUtil.getId(ssp.getStudyPeriod()));
        return dto;
    }

    public Integer getGradeMark() {
        return gradeMark;
    }
    public void setGradeMark(Integer gradeMark) {
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
    public Set<String> getTeachers() {
        return teachers;
    }
    public void setTeachers(Set<String> teachers) {
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
