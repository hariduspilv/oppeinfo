package ee.hitsa.ois.web.commandobject.student;

import java.util.List;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class StudentCardSearchCommand {

    private EntityConnectionCommand studentGroup;
    private String name;
    private String idcode;
    private String cardNr;
    private List<String> status;
    private Boolean notActiveStudents = Boolean.FALSE;

    public EntityConnectionCommand getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(EntityConnectionCommand studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getCardNr() {
        return cardNr;
    }

    public void setCardNr(String cardNr) {
        this.cardNr = cardNr;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public Boolean getNotActiveStudents() {
        return notActiveStudents;
    }

    public void setNotActiveStudents(Boolean notActiveStudents) {
        this.notActiveStudents = notActiveStudents;
    }

}
