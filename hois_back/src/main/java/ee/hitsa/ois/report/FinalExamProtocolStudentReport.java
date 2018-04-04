package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import java.util.List;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;

public class FinalExamProtocolStudentReport {
    
    private final String name;
    private final String grade;
    private final String gradeName;
    private final List<String> occupations;
    private final List<String> partOccupations;
    
    public FinalExamProtocolStudentReport(ProtocolStudent student, Language lang) {
        this.name = PersonUtil.fullname(student.getStudent().getPerson());
        this.grade = student.getGrade() != null ? student.getGrade().getValue() : null;
        this.gradeName = student.getGrade() != null ? name(student.getGrade(), lang) : null;
        this.occupations = StreamUtil.toMappedList(pso -> ClassifierUtil.getNullableNameEt(pso.getOccupation()), student.getProtocolStudentOccupations());
        this.partOccupations = StreamUtil.toMappedList(pso -> ClassifierUtil.getNullableNameEt(pso.getPartOccupation()), student.getProtocolStudentOccupations());
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

    public List<String> getOccupations() {
        return occupations;
    }

    public List<String> getPartOccupations() {
        return partOccupations;
    }
    
}
