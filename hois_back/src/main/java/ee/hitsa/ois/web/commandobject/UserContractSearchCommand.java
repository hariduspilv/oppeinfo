package ee.hitsa.ois.web.commandobject;

import java.time.LocalDate;

public class UserContractSearchCommand {

    private String studentName;
    private String groupName;
    private LocalDate from;
    private LocalDate thru;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

}
