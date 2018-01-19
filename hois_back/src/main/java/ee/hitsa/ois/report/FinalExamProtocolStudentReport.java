package ee.hitsa.ois.report;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;

public class FinalExamProtocolStudentReport {
    
    private final String name;
    private final String grade;
    
    //TODO: curriculum occupations

    public FinalExamProtocolStudentReport(ProtocolStudent student) {
        this(student, Language.ET);
    }
    
    public FinalExamProtocolStudentReport(ProtocolStudent student, Language lang) {
        this.name = PersonUtil.fullname(student.getStudent().getPerson());
        this.grade = student.getGradeValue();
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }
    
}
