package ee.hitsa.ois.web.commandobject;

public class ModuleProtocolStudentSaveForm extends ModuleProtocolStudentCreateForm implements ProtocolStudentForm {

    private String grade;

    @Override
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
