package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;

public class ProtocolStudentReport {
    private final String fullname;
    private final String grade;
    private final String gradeName;
    
    ProtocolStudentReport(ProtocolStudent protocolStudent, Language lang) {
        fullname = PersonUtil.fullname(protocolStudent.getStudent().getPerson());
        grade = protocolStudent.getGrade() != null ? protocolStudent.getGrade().getValue() : null;
        gradeName = protocolStudent.getGrade() != null ? name(protocolStudent.getGrade(), lang) : null;
    }

    public String getFullname() {
        return fullname;
    }

    public String getGrade() {
        return grade;
    }
    
    public String getGradeName() {
        return gradeName;
    }
}
