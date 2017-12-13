package ee.hitsa.ois.web.dto.scholarship;

import ee.hitsa.ois.domain.student.Student;

public class ScholarshipStudentDto {
    private Long averageMark;
    private Long lastPeriodMark;
    private Long curriculumCompletion;
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
        return dto;
    }

    public Long getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(Long averageMark) {
        this.averageMark = averageMark;
    }

    public Long getLastPeriodMark() {
        return lastPeriodMark;
    }

    public void setLastPeriodMark(Long lastPeriodMark) {
        this.lastPeriodMark = lastPeriodMark;
    }

    public Long getCurriculumCompletion() {
        return curriculumCompletion;
    }

    public void setCurriculumCompletion(Long curriculumCompletion) {
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
