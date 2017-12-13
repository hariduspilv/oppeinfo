package ee.hitsa.ois.web.commandobject.apelapplication;

import javax.validation.constraints.Size;

public class ApelApplicationCommentForm {

    private Long id;
    
    @Size(max = 4000)
    private String addInfo;

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

}
