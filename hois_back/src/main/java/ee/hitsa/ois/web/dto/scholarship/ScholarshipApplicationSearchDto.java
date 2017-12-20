package ee.hitsa.ois.web.dto.scholarship;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import ee.hitsa.ois.util.PersonUtil;

public class ScholarshipApplicationSearchDto {
    private Long id;
    private Long place;
    private String type;
    private String termNameEt;
    private String curriculumCode;
    private Long studentId;
    private String studentName;
    private String idcode;
    private Long averageMark;
    private Long lastPeriodMark;
    private Long curriculumCompletion;
    private Boolean isTeacherConfirm;
    private String status;

    private String compensationReason;
    private String compensationFrequency;

    private Long credits;

    public ScholarshipApplicationSearchDto(Object r) {
        this.id = resultAsLong(r, 0);
        this.type = resultAsString(r, 1);
        this.termNameEt = resultAsString(r, 2);
        this.curriculumCode = resultAsString(r, 3);
        this.studentId = resultAsLong(r, 4);
        this.studentName = PersonUtil.fullname(resultAsString(r, 5), resultAsString(r, 6));
        this.idcode = resultAsString(r, 7);
        this.averageMark = resultAsLong(r, 8);
        this.lastPeriodMark = resultAsLong(r, 9);
        this.curriculumCompletion = resultAsLong(r, 10);
        this.isTeacherConfirm = resultAsBoolean(r, 11);
        this.status = resultAsString(r, 12);
        this.compensationReason = resultAsString(r, 13);
        this.compensationFrequency = resultAsString(r, 14);
        this.credits = resultAsLong(r, 15);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlace() {
        return place;
    }

    public void setPlace(Long place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTermNameEt() {
        return termNameEt;
    }

    public void setTermNameEt(String termNameEt) {
        this.termNameEt = termNameEt;
    }

    public String getCurriculumCode() {
        return curriculumCode;
    }

    public void setCurriculumCode(String curriculumCode) {
        this.curriculumCode = curriculumCode;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
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

    public Boolean getIsTeacherConfirm() {
        return isTeacherConfirm;
    }

    public void setIsTeacherConfirm(Boolean isTeacherConfirm) {
        this.isTeacherConfirm = isTeacherConfirm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompensationReason() {
        return compensationReason;
    }

    public void setCompensationReason(String compensationReason) {
        this.compensationReason = compensationReason;
    }

    public String getCompensationFrequency() {
        return compensationFrequency;
    }

    public void setCompensationFrequency(String compensationFrequency) {
        this.compensationFrequency = compensationFrequency;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

}
