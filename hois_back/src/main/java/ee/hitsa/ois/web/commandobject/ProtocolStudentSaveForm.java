package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.web.dto.GradeDto;

public class ProtocolStudentSaveForm extends ProtocolStudentCreateForm implements ProtocolStudentForm {

    private GradeDto grade;
    private String addInfo;

    @Override
    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    

}
