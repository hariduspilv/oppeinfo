package ee.hitsa.ois.web.dto.poll;

public class AnswersDto extends ResponseDto {
    
    private Boolean isTeacherComment;
    private Boolean isTeacherCommentVisible;
    private Boolean isStudentVisible;
    
    public Boolean getIsTeacherComment() {
        return isTeacherComment;
    }
    public void setIsTeacherComment(Boolean isTeacherComment) {
        this.isTeacherComment = isTeacherComment;
    }
    public Boolean getIsTeacherCommentVisible() {
        return isTeacherCommentVisible;
    }
    public void setIsTeacherCommentVisible(Boolean isTeacherCommentVisible) {
        this.isTeacherCommentVisible = isTeacherCommentVisible;
    }
    public Boolean getIsStudentVisible() {
        return isStudentVisible;
    }
    public void setIsStudentVisible(Boolean isStudentVisible) {
        this.isStudentVisible = isStudentVisible;
    }
}
