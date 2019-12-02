package ee.hitsa.ois.web.dto.poll;

import ee.hitsa.ois.domain.poll.PollTeacherComment;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectCommentDto {
    
    private AutocompleteResult teacher;
    private String addInfo;
    private Long commentRef;
    
    public static SubjectCommentDto of(PollTeacherComment comment) {
        SubjectCommentDto dto = new SubjectCommentDto();
        dto.setAddInfo(comment.getAddInfo());
        dto.setCommentRef(comment.getId());
        dto.setTeacher(AutocompleteResult.of(comment.getTeacher()));
        return dto;
    }
    
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    public Long getCommentRef() {
        return commentRef;
    }
    public void setCommentRef(Long commentRef) {
        this.commentRef = commentRef;
    }

}
