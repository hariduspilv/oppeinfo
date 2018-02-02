package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import java.util.List;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

public class FinalExamHigherProtocolCreateForm {

    @NotNull
    private Long subjectStudyPeriod;
    @NotNull
    private Long subject;
    @NotNull
    @ClassifierRestriction(MainClassCode.PROTOKOLLI_LIIK)
    private String protocolType;
    
    private List<FinalExamProtocolStudentCreateForm> protocolStudents;

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
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
}
