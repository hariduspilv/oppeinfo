package ee.hitsa.ois.report;

import static ee.hitsa.ois.util.TranslateUtil.name;

import ee.hitsa.ois.domain.protocol.ProtocolStudent;
import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.PersonUtil;

public class ProtocolStudentReport {
    private final String fullname;
    private final String grade;
    private final String gradeName;
    private final String gradingSchemaGrade;
    private final String studentgroup;

    ProtocolStudentReport(ProtocolStudent protocolStudent, Boolean letterGrades, Language lang) {
        fullname = PersonUtil.fullname(protocolStudent.getStudent().getPerson());
        StudentGroup sg = protocolStudent.getStudent().getStudentGroup();
        if (sg != null) {
            studentgroup = sg.getCode();
        } else {
            studentgroup = null;
        }
        if (protocolStudent.getGradingSchemaRow() != null) {
            grade = null;
            gradeName = null;
            gradingSchemaGrade = Language.EN.equals(lang) ? protocolStudent.getGradingSchemaRow().getGradeEn()
                    : protocolStudent.getGradingSchemaRow().getGrade();
        } else if (protocolStudent.getGrade() != null) {
            grade = ReportUtil.gradeValue(protocolStudent.getGrade(), letterGrades, lang);
            gradeName = name(protocolStudent.getGrade(), lang);
            gradingSchemaGrade = null;
        } else {
            grade = null;
            gradeName = null;
            gradingSchemaGrade = null;
        }
    }

    public String getFullname() {
        return fullname;
    }

    public String getGrade() {
        return grade;
    }

    public String getGradingSchemaGrade() {
        return gradingSchemaGrade;
    }

    public String getGradeName() {
        return gradeName;
    }

    public String getStudentgroup() {
        return studentgroup;
    }
}
