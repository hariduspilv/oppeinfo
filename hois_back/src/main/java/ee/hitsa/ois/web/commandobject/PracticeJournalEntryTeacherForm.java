package ee.hitsa.ois.web.commandobject;

public class PracticeJournalEntryTeacherForm {

    private Long id;
    private String teacherComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }

}
