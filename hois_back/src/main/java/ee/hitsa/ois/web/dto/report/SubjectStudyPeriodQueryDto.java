package ee.hitsa.ois.web.dto.report;

import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectStudyPeriodQueryDto extends SchoolQueryDto {
    
    private Boolean nrShow;
    
    private Boolean subjectCodeShow;
    private String subjectCode;
    
    private Boolean subjectEtShow;
    private AutocompleteResult subjectEt;
    
    private Boolean subjectEnShow;
    private AutocompleteResult subjectEn;
    
    private Boolean teacherShow;
    private AutocompleteResult teacher;
    
    private Boolean curriculumShow;
    private List<AutocompleteResult> curriculum;
    
    private Boolean studyYearShow;
    private String studyYearSign;
    private String studyYear;
    
    private Boolean studyPeriodShow;
    private List<String> studyPeriod;
    
    private Boolean courseShow;
    private List<String> course;
    
    private Boolean studentGroupShow;
    private AutocompleteResult studentGroup;
    
    private Boolean eapShow;
    private Long eap;
    private String eapSign;
    
    private Boolean optionalSubjectShow;
    private Boolean optionalSubject;
    
    private Boolean moodleIdShow;
    private Long moodleId;
    
    private Boolean protocolShow;
    private Boolean protocol;
    
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
    public Boolean getSubjectEtShow() {
        return subjectEtShow;
    }
    public void setSubjectEtShow(Boolean subjectEtShow) {
        this.subjectEtShow = subjectEtShow;
    }
    public AutocompleteResult getSubjectEt() {
        return subjectEt;
    }
    public void setSubjectEt(AutocompleteResult subjectEt) {
        this.subjectEt = subjectEt;
    }
    public Boolean getSubjectEnShow() {
        return subjectEnShow;
    }
    public void setSubjectEnShow(Boolean subjectEnShow) {
        this.subjectEnShow = subjectEnShow;
    }
    public AutocompleteResult getSubjectEn() {
        return subjectEn;
    }
    public void setSubjectEn(AutocompleteResult subjectEn) {
        this.subjectEn = subjectEn;
    }
    public Boolean getTeacherShow() {
        return teacherShow;
    }
    public void setTeacherShow(Boolean teacherShow) {
        this.teacherShow = teacherShow;
    }
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
    public Boolean getCurriculumShow() {
        return curriculumShow;
    }
    public void setCurriculumShow(Boolean curriculumShow) {
        this.curriculumShow = curriculumShow;
    }
    public List<AutocompleteResult> getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(List<AutocompleteResult> curriculum) {
        this.curriculum = curriculum;
    }
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
    public Boolean getStudentGroupShow() {
        return studentGroupShow;
    }
    public void setStudentGroupShow(Boolean studentGroupShow) {
        this.studentGroupShow = studentGroupShow;
    }
    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }
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
    public Boolean getMoodleIdShow() {
        return moodleIdShow;
    }
    public void setMoodleIdShow(Boolean moodleIdShow) {
        this.moodleIdShow = moodleIdShow;
    }
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
    public Long getMoodleId() {
        return moodleId;
    }
    public void setMoodleId(Long moodleId) {
        this.moodleId = moodleId;
    }
    public Boolean getNrShow() {
        return nrShow;
    }
    public void setNrShow(Boolean nrShow) {
        this.nrShow = nrShow;
    }
    
}
