package ee.hitsa.ois.web.commandobject.apelapplication;

import javax.validation.constraints.Size;

public class ApelApplicationCommentForm {

    private Long id;
    
    @Size(max = 4000)
    private String addInfo;
    /** Is comment visible for student */
    private Boolean isStudent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

}
