package ee.hitsa.ois.web.dto.poll;

import java.util.ArrayList;
import java.util.List;

public class GraphDto {
    
    private List<SubjectCommentDto> comments;
    private List<GraphInfoDto> graphByTheme = new ArrayList<>();
    private Boolean commentDisabled;

    public List<GraphInfoDto> getGraphByTheme() {
        return graphByTheme;
    }

    public void setGraphByTheme(List<GraphInfoDto> graphByTheme) {
        this.graphByTheme = graphByTheme;
    }

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

}
