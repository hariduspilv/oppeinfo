package ee.hitsa.ois.web.commandobject.scholarship;

public class ScholarshiApplicationRejectionForm {
    private Long id;
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
