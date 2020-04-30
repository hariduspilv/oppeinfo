package ee.hitsa.ois.web.commandobject.juhan;

import ee.hitsa.ois.validation.Conditional;

@Conditional(selected = "idcode", values = {"null"}, required = {"uqcode"})
@Conditional(selected = "uqcode", values = {"null"}, required = {"idcode"})
public class JuhanEventTeacherForm {

    private String idcode;
    private String uqcode;
    private String teacherName;

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getUqcode() {
        return uqcode;
    }

    public void setUqcode(String uqcode) {
        this.uqcode = uqcode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
