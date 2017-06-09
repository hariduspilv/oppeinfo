package ee.hitsa.ois.web.commandobject;

/**
 * OisFile without fdata, which is supposed to be acquired via OisFileController by id
 */
public class OisFileViewDto {

    private Long id;
    private String fname;
    private String ftype;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
