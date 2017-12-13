package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.Size;

public class PracticeJournalEntrySupervisorForm {

    private Long id;
    @Size(max=10000)
    private String supervisorComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupervisorComment() {
        return supervisorComment;
    }

    public void setSupervisorComment(String supervisorComment) {
        this.supervisorComment = supervisorComment;
    }

}
