package ee.hitsa.ois.web.dto.scholarship;

import java.math.BigDecimal;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentCurriculumCompletion;
import ee.hitsa.ois.util.EntityUtil;

public class ScholarshipStudentDto {
    private BigDecimal averageMark;
    private BigDecimal lastPeriodMark;
    private BigDecimal curriculumCompletion;
    private Long absences;
    private String studentGroupCode;
    private String phone;
    private String address;
    private String email;

    public static ScholarshipStudentDto of(Student student) {
        ScholarshipStudentDto dto = new ScholarshipStudentDto();
        dto.setPhone(student.getPerson().getPhone());
        dto.setEmail(student.getEmail());
        dto.setAddress(student.getPerson().getAddress());
        dto.setStudentGroupCode(student.getStudentGroup().getCode());

        StudentCurriculumCompletion completion = student.getStudentCurriculumCompletion();
        if (completion != null) {
            dto.setAverageMark(completion.getAverageMark());
            dto.setLastPeriodMark(completion.getAverageMarkLastStudyPeriod());
            dto.setCurriculumCompletion(completion.getStudyBacklog());
        }
        // TODO: Missing absences
        return dto;
    }

    public static ScholarshipStudentDto of(ScholarshipApplication application) {
        ScholarshipStudentDto dto = new ScholarshipStudentDto();
        return EntityUtil.bindToDto(application, dto);
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }

    public BigDecimal getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(BigDecimal lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public BigDecimal getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(BigDecimal curriculumCompletion) {
        this.curriculumCompletion = curriculumCompletion;
    }

    public Long getAbsences() {
        return absences;
    }

    public void setAbsences(Long absences) {
        this.absences = absences;
    }

    public String getStudentGroupCode() {
        return studentGroupCode;
    }

    public void setStudentGroupCode(String studentGroupCode) {
        this.studentGroupCode = studentGroupCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
