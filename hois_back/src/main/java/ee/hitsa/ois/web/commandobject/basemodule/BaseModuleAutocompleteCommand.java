package ee.hitsa.ois.web.commandobject.basemodule;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class BaseModuleAutocompleteCommand extends SearchCommand {

    private Boolean notExpired;

    public Boolean getNotExpired() {
        return notExpired;
    }

    public void setNotExpired(Boolean notExpired) {
        this.notExpired = notExpired;
    }
}
