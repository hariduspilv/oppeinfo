package ee.hitsa.ois.web.commandobject.finalprotocol;

import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class FinalHigherProtocolCreateForm {

    @NotNull
    private Boolean isFinalThesis;
    private Long subject;
    private Long subjectStudyPeriod;
    @NotNull
    @ClassifierRestriction(MainClassCode.PROTOKOLLI_LIIK)
    private String protocolType;
    @NotNull
    private Long curriculumVersion;
    
    private List<FinalProtocolStudentCreateForm> protocolStudents;
    
    public Boolean getIsFinalThesis() {
        return isFinalThesis;
    }

    public void setIsFinalThesis(Boolean isFinalThesis) {
        this.isFinalThesis = isFinalThesis;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public List<FinalProtocolStudentCreateForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<FinalProtocolStudentCreateForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

}
