package ee.hitsa.ois.web.dto.report;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;

import java.math.BigDecimal;

import ee.hitsa.ois.util.SubjectUtil;

public class SubjectStudyPeriodDataDto {
    
    private Integer nr;
    private String subjectCode;
    private String subjectEt;
    private String subjectEn;
    private String teacher;
    private AutocompleteResult curriculum;
    private String studyYear;
    private AutocompleteResult studyPeriod;
    private String course;
    private String studentGroup;
    private BigDecimal eap;
    private AutocompleteResult optionalSubject;
    private String moodleId;
    private String protocol;
    
    public SubjectStudyPeriodDataDto(Object r, Integer order) {
        this.nr = order;
        this.subjectCode = resultAsString(r, 0);
        this.subjectEt = SubjectUtil.subjectNameWithoutCode(resultAsString(r, 2), null);
        this.subjectEn = SubjectUtil.subjectNameWithoutCode(resultAsString(r, 3), null);
        this.teacher = resultAsString(r, 5);
        this.curriculum = new AutocompleteResult(resultAsLong(r, 6), resultAsString(r, 7), resultAsString(r, 8));
        this.studyYear = resultAsString(r, 9);
        this.studyPeriod = new AutocompleteResult(null, resultAsString(r, 10), resultAsString(r, 11));
        this.course = resultAsString(r, 12);
        Boolean isOptional = resultAsBoolean(r, 13);
        this.optionalSubject = new AutocompleteResult(null, 
                (Boolean.TRUE.equals(isOptional) ? "jah" : "ei"), 
                (Boolean.TRUE.equals(isOptional) ? "yes" : "no"));
        this.studentGroup = resultAsString(r, 14);
        this.eap = resultAsDecimal(r, 4);
        this.moodleId = resultAsString(r, 15);
        this.protocol = resultAsString(r, 16);
    }
    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public AutocompleteResult getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }
    public String getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public String getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }
    public String getMoodleId() {
        return moodleId;
    }
    public void setMoodleId(String moodleId) {
        this.moodleId = moodleId;
    }
    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public AutocompleteResult getOptionalSubject() {
        return optionalSubject;
    }
    public void setOptionalSubject(AutocompleteResult optionalSubject) {
        this.optionalSubject = optionalSubject;
    }
    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public BigDecimal getEap() {
        return eap;
    }
    public void setEap(BigDecimal eap) {
        this.eap = eap;
    }
    public String getSubjectEn() {
        return subjectEn;
    }
    public void setSubjectEn(String subjectEn) {
        this.subjectEn = subjectEn;
    }
    public String getSubjectEt() {
        return subjectEt;
    }
    public void setSubjectEt(String subjectEt) {
        this.subjectEt = subjectEt;
    }
    public Integer getNr() {
        return nr;
    }
    public void setNr(Integer nr) {
        this.nr = nr;
    }

}
