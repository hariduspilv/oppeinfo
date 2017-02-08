package ee.hitsa.ois.web.commandobject;

import javax.validation.constraints.NotNull;

public class OisFileCommand extends VersionedCommand {

    @NotNull
    private byte[] fdata;
    @NotNull
    private String fname;
    @NotNull
    private String ftype;

    public byte[] getFdata() {
        return fdata;
    }

    public void setFdata(byte[] fdata) {
        this.fdata = fdata;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }
}
