package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.EstonianIdCode;

public class IdcodeCommand {

    @NotNull
    @EstonianIdCode
    private String idcode;

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
}
