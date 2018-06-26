package ee.hitsa.ois.web.commandobject;

public class StudentGroupAutocompleteCommand extends SearchCommand {

    private Boolean valid;
    private Boolean higher;
    private Long studentGroupTeacherId;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

    public Long getStudentGroupTeacherId() {
        return studentGroupTeacherId;
    }

    public void setStudentGroupTeacherId(Long studentGroupTeacherId) {
        this.studentGroupTeacherId = studentGroupTeacherId;
    }
    
}
