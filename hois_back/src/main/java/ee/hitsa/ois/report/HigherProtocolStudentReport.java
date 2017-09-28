package ee.hitsa.ois.report;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.util.PersonUtil;
/**
 *  Probably should be renamed to ProtocolStudentReport
 */
public class HigherProtocolStudentReport {
    private final String fullname;
    private final String grade;
    
    HigherProtocolStudentReport(ProtocolStudent protocolStudent) {
        fullname = PersonUtil.fullname(protocolStudent.getStudent().getPerson());
        grade = protocolStudent.getGrade().getNameEt();
    }

    public String getFullname() {
        return fullname;
    }

    public String getGrade() {
        return grade;
    }
}
