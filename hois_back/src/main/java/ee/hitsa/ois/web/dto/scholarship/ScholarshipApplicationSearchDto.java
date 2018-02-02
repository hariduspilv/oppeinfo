package ee.hitsa.ois.web.dto.scholarship;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;

import java.math.BigDecimal;

import ee.hitsa.ois.util.PersonUtil;

public class ScholarshipApplicationSearchDto {
    private Long id;
    private Long place;
    private String type;
    private Long term;
    private String termNameEt;
    private String curriculumCode;
    private Long studentId;
    private String studentName;
    private String firstName;
    private String lastName;
    private String idcode;
    private BigDecimal averageMark;
    private BigDecimal lastPeriodMark;
    private BigDecimal curriculumCompletion;
    private BigDecimal credits;
    private Boolean isTeacherConfirm;
    private String status;
    private String rejectComment;

    private String compensationReason;
    private String compensationFrequency;

    public ScholarshipApplicationSearchDto(Object r) {
        this.id = resultAsLong(r, 0);
        this.type = resultAsString(r, 1);
        this.term = resultAsLong(r, 2);
        this.termNameEt = resultAsString(r, 3);
        this.curriculumCode = resultAsString(r, 4);
        this.studentId = resultAsLong(r, 5);
        this.studentName = PersonUtil.fullname(resultAsString(r, 6), resultAsString(r, 7));
        this.firstName = resultAsString(r, 6);
        this.lastName = resultAsString(r, 7);
        this.idcode = resultAsString(r, 8);
        this.averageMark = resultAsDecimal(r, 9);
        this.lastPeriodMark = resultAsDecimal(r, 10);
        this.curriculumCompletion = resultAsDecimal(r, 11);
        this.isTeacherConfirm = resultAsBoolean(r, 12);
        this.status = resultAsString(r, 13);
        this.compensationReason = resultAsString(r, 14);
        this.compensationFrequency = resultAsString(r, 15);
        this.credits = resultAsDecimal(r, 16);
        this.rejectComment = resultAsString(r, 17);
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

    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

}
