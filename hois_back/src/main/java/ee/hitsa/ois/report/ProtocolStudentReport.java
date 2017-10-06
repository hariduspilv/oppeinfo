package ee.hitsa.ois.report;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.PersonUtil;

public class ProtocolStudentReport {
    private final String fullname;
    private final String grade;
    
    ProtocolStudentReport(ProtocolStudent protocolStudent) {
        fullname = PersonUtil.fullname(protocolStudent.getStudent().getPerson());
        grade = ClassifierUtil.getNullableNameEt(protocolStudent.getGrade());
    }

    public String getFullname() {
        return fullname;
    }

    public String getGrade() {
        return grade;
    }
}
