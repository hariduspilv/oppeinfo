package ee.hitsa.ois.web.commandobject.boardingschool;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class BoardingSchoolManagementSearchCommand {

    private EntityConnectionCommand studentGroup;
    private String name;
    private String idcode;
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

    public Boolean getNotActiveStudents() {
        return notActiveStudents;
    }

    public void setNotActiveStudents(Boolean notActiveStudents) {
        this.notActiveStudents = notActiveStudents;
    }
}
