package ee.hitsa.ois.web.commandobject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.dto.OisFileDto;

public class CurriculumFileForm extends VersionedCommand {
    @NotNull
    private Boolean ehis;
    @NotNull
    private Boolean sendEhis;
    @NotNull
    @Valid
    private OisFileDto oisFile;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.EHIS_FAIL)
    private String ehisFile;

    public OisFileDto getOisFile() {
        return oisFile;
    }
    public void setOisFile(OisFileDto oisFile) {
        this.oisFile = oisFile;
    }
    public Boolean getEhis() {
        return ehis;
    }
    public void setEhis(Boolean ehis) {
        this.ehis = ehis;
    }
    public Boolean getSendEhis() {
        return sendEhis;
    }
    public void setSendEhis(Boolean sendEhis) {
        this.sendEhis = sendEhis;
    }
    public String getEhisFile() {
        return ehisFile;
    }
    public void setEhisFile(String ehisFile) {
        this.ehisFile = ehisFile;
    }
}
