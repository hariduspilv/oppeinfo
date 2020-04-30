package ee.hitsa.ois.web.commandobject.report;
import java.util.List;

import ee.hitsa.ois.web.dto.report.SchoolQueryDto;

public class SubjectStudyPeriodDataCommand extends SchoolQueryDto {
    
    private Boolean nrShow;
    
    private Boolean subjectCodeShow;
    private String subjectCode;
    
    private Boolean subjectEtShow;
    private Long subjectEt;
    
    private Boolean subjectEnShow;
    private Long subjectEn;
    
    private Boolean teacherShow;
    private Long teacher;
    
    private Boolean curriculumShow;
    private List<Long> curriculum;
    
    private Boolean studyYearShow;
    private String studyYearSign;
    private String studyYear;
    
    private Boolean studyPeriodShow;
    private List<String> studyPeriod;
    
    private Boolean courseShow;
    private List<String> course;
    
    private Boolean studentGroupShow;
    private Long studentGroup;
    
    private Boolean eapShow;
    private Long eap;
    private String eapSign;
    
    private Boolean optionalSubjectShow;
    private Boolean optionalSubject;
    
    private Boolean moodleIdShow;
    private String moodleId;
    
    private Boolean protocolShow;
    private Boolean protocol;
    
    @Order(value=1)
    public Boolean getSubjectCodeShow() {
        return subjectCodeShow;
    }
    public void setSubjectCodeShow(Boolean subjectCodeShow) {
        this.subjectCodeShow = subjectCodeShow;
    }
    
    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    @Order(value=2)
    public Boolean getSubjectEtShow() {
        return subjectEtShow;
    }
    public void setSubjectEtShow(Boolean subjectEtShow) {
        this.subjectEtShow = subjectEtShow;
    }
    public Long getSubjectEt() {
        return subjectEt;
    }
    public void setSubjectEt(Long subjectEt) {
        this.subjectEt = subjectEt;
    }
    @Order(value=3)
    public Boolean getSubjectEnShow() {
        return subjectEnShow;
    }
    public void setSubjectEnShow(Boolean subjectEnShow) {
        this.subjectEnShow = subjectEnShow;
    }
    public Long getSubjectEn() {
        return subjectEn;
    }
    public void setSubjectEn(Long subjectEn) {
        this.subjectEn = subjectEn;
    }
    @Order(value=5)
    public Boolean getTeacherShow() {
        return teacherShow;
    }
    public void setTeacherShow(Boolean teacherShow) {
        this.teacherShow = teacherShow;
    }
    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    @Order(value=4)
    public Boolean getCurriculumShow() {
        return curriculumShow;
    }
    public void setCurriculumShow(Boolean curriculumShow) {
        this.curriculumShow = curriculumShow;
    }
    public List<Long> getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(List<Long> curriculum) {
        this.curriculum = curriculum;
    }
    @Order(value=6)
    public Boolean getStudyYearShow() {
        return studyYearShow;
    }
    public void setStudyYearShow(Boolean studyYearShow) {
        this.studyYearShow = studyYearShow;
    }
    public String getStudyYearSign() {
        return studyYearSign;
    }
    public void setStudyYearSign(String studyYearSign) {
        this.studyYearSign = studyYearSign;
    }
    public String getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }
    @Order(value=7)
    public Boolean getStudyPeriodShow() {
        return studyPeriodShow;
    }
    public void setStudyPeriodShow(Boolean studyPeriodShow) {
        this.studyPeriodShow = studyPeriodShow;
    }
    public List<String> getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(List<String> studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    @Order(value=8)
    public Boolean getCourseShow() {
        return courseShow;
    }
    public void setCourseShow(Boolean courseShow) {
        this.courseShow = courseShow;
    }
    public List<String> getCourse() {
        return course;
    }
    public void setCourse(List<String> course) {
        this.course = course;
    }
    @Order(value=9)
    public Boolean getStudentGroupShow() {
        return studentGroupShow;
    }
    public void setStudentGroupShow(Boolean studentGroupShow) {
        this.studentGroupShow = studentGroupShow;
    }
    public Long getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
    @Order(value=10)
    public Boolean getEapShow() {
        return eapShow;
    }
    public void setEapShow(Boolean eapShow) {
        this.eapShow = eapShow;
    }
    public Long getEap() {
        return eap;
    }
    public void setEap(Long eap) {
        this.eap = eap;
    }
    public String getEapSign() {
        return eapSign;
    }
    public void setEapSign(String eapSign) {
        this.eapSign = eapSign;
    }
    @Order(value=11)
    public Boolean getOptionalSubjectShow() {
        return optionalSubjectShow;
    }
    public void setOptionalSubjectShow(Boolean optionalSubjectShow) {
        this.optionalSubjectShow = optionalSubjectShow;
    }
    public Boolean getOptionalSubject() {
        return optionalSubject;
    }
    public void setOptionalSubject(Boolean optionalSubject) {
        this.optionalSubject = optionalSubject;
    }
    @Order(value=13)
    public Boolean getProtocolShow() {
        return protocolShow;
    }
    public void setProtocolShow(Boolean protocolShow) {
        this.protocolShow = protocolShow;
    }
    public Boolean getProtocol() {
        return protocol;
    }
    public void setProtocol(Boolean protocol) {
        this.protocol = protocol;
    }
    public String getMoodleId() {
        return moodleId;
    }
    public void setMoodleId(String moodleId) {
        this.moodleId = moodleId;
    }
    @Order(value=12)
    public Boolean getMoodleIdShow() {
        return moodleIdShow;
    }
    public void setMoodleIdShow(Boolean moodleIdShow) {
        this.moodleIdShow = moodleIdShow;
    }
    @Order(value=0)
    public Boolean getNrShow() {
        return nrShow;
    }
    public void setNrShow(Boolean nrShow) {
        this.nrShow = nrShow;
    }

}
