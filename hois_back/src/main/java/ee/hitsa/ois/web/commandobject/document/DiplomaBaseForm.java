package ee.hitsa.ois.web.commandobject.document;

import java.util.List;

public abstract class DiplomaBaseForm {

    private Long directiveId;
    private String formType;
    private List<Long> studentIds;

    public Long getDirectiveId() {
        return directiveId;
    }
    public void setDirectiveId(Long directiveId) {
        this.directiveId = directiveId;
    }
    
    public String getFormType() {
        return formType;
    }
    public void setFormType(String formType) {
        this.formType = formType;
    }
    
    public List<Long> getStudentIds() {
        return studentIds;
    }
    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }
    
}
