package ee.hitsa.ois.web.dto.finalthesis;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class FinalThesisCercsForm extends VersionedCommand {

    private Long id;
    @ClassifierRestriction(MainClassCode.CERCS)
    private String cercs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCercs() {
        return cercs;
    }

    public void setCercs(String cercs) {
        this.cercs = cercs;
    }

}
