package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;

public class FinalExamProtocolStudentReport {
    
    private final String name;
    private final String grade;
    private final String gradeName;
    
    //TODO: curriculum occupations
    public FinalExamProtocolStudentReport(ProtocolStudent student, Language lang) {
        this.name = PersonUtil.fullname(student.getStudent().getPerson());
        this.grade = student.getGrade() != null ? student.getGrade().getValue() : null;
        this.gradeName = student.getGrade() != null ? name(student.getGrade(), lang) : null;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }
    
    public String getGradeName() {
        return gradeName;
    }
    
}
