package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class FinalExamHigherProtocolCreateForm {

    @NotNull
    private Long subjectStudyPeriod;
    @NotNull
    @ClassifierRestriction(MainClassCode.PROTOKOLLI_LIIK)
    private String protocolType;
    @NotNull
    private Long curriculum;
    
    private List<FinalExamProtocolStudentCreateForm> protocolStudents;

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

    public List<FinalExamProtocolStudentCreateForm> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<FinalExamProtocolStudentCreateForm> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }
    
}
