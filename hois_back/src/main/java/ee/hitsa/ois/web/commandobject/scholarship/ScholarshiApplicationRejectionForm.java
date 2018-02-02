package ee.hitsa.ois.web.commandobject.scholarship;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.Required;

public class ScholarshiApplicationRejectionForm {
    @Required
    private Long id;
    @Size(max = 100)
    private String rejectComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public void setRejectComment(String rejectComment) {
        this.rejectComment = rejectComment;
    }

}
