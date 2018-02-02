package ee.hitsa.ois.web.commandobject.finalexamprotocol;

import ee.hitsa.ois.web.commandobject.ProtocolStudentForm;

public class FinalExamVocationalProtocolStudentSaveForm extends FinalExamProtocolStudentCreateForm implements ProtocolStudentForm {

    private String grade;

    @Override
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
