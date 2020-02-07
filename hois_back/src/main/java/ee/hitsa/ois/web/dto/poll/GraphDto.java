package ee.hitsa.ois.web.dto.poll;

import java.util.ArrayList;
import java.util.List;

public class GraphDto {
    
    private List<SubjectCommentDto> comments;
    private List<GraphThemeDto> theme = new ArrayList<>();
    private Boolean commentDisabled;
    private Boolean canComment;
    private Boolean canStudentView;

    public List<SubjectCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<SubjectCommentDto> comments) {
        this.comments = comments;
    }

    public Boolean getCommentDisabled() {
        return commentDisabled;
    }

    public void setCommentDisabled(Boolean commentDisabled) {
        this.commentDisabled = commentDisabled;
    }

    public List<GraphThemeDto> getTheme() {
        return theme;
    }

    public void setTheme(List<GraphThemeDto> theme) {
        this.theme = theme;
    }

    public Boolean getCanComment() {
        return canComment;
    }

    public void setCanComment(Boolean canComment) {
        this.canComment = canComment;
    }

    public Boolean getCanStudentView() {
        return canStudentView;
    }

    public void setCanStudentView(Boolean canStudentView) {
        this.canStudentView = canStudentView;
    }

}
