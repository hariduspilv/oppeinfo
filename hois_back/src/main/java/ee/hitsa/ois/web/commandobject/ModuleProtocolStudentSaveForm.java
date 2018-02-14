package ee.hitsa.ois.web.commandobject;

public class ModuleProtocolStudentSaveForm extends ModuleProtocolStudentCreateForm implements ProtocolStudentForm {

    private String grade;
    private String addInfo;

    @Override
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    

}
