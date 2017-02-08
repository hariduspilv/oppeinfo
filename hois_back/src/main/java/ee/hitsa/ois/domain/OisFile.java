package ee.hitsa.ois.domain;

import javax.persistence.Entity;

@Entity
public class OisFile extends BaseEntityWithId {

    private byte[] fdata;
    private String fname;
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

    @Override
    public String toString() {
        return "OisFile [id=" + getId() + ", fname=" + fname + ", ftype=" + ftype;
    }
}
