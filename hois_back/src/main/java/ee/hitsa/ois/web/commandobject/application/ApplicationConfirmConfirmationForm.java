package ee.hitsa.ois.web.commandobject.application;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.web.commandobject.OisFileCommand;

public class ApplicationConfirmConfirmationForm {

    @NotNull
    private Boolean confirm;
    @Size(max = 4000)
    private String representativeDecisionAddInfo;
    
    private OisFileCommand oisFile;

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public String getRepresentativeDecisionAddInfo() {
        return representativeDecisionAddInfo;
    }

    public void setRepresentativeDecisionAddInfo(String representativeDecisionAddInfo) {
        this.representativeDecisionAddInfo = representativeDecisionAddInfo;
    }

    public OisFileCommand getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileCommand oisFile) {
        this.oisFile = oisFile;
    }
}
