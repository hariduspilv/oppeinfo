package ee.hitsa.ois.web.commandobject.student;

import javax.validation.constraints.Size;

import ee.hitsa.ois.validation.EstonianIdCode;

public class StudentRepresentativeApplicationSearchCommand {

    @Size(max = 255)
    private String name;
    @EstonianIdCode
    private String idcode;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
