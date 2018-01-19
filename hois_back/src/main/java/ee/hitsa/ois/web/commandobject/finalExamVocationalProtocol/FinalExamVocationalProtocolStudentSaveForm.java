package ee.hitsa.ois.web.commandobject.finalExamVocationalProtocol;

import ee.hitsa.ois.web.commandobject.ProtocolStudentForm;

public class FinalExamVocationalProtocolStudentSaveForm extends FinalExamVocationalProtocolStudentCreateForm implements ProtocolStudentForm {

    private String grade;

    @Override
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
