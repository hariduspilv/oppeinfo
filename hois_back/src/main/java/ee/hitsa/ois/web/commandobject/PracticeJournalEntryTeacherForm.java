package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

public class PracticeJournalEntryTeacherForm {

    private Long id;
    @Size(max=10000)
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
