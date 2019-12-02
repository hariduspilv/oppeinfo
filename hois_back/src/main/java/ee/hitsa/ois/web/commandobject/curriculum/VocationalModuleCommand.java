package ee.hitsa.ois.web.commandobject.curriculum;

import ee.hitsa.ois.web.commandobject.SearchCommand;

public class VocationalModuleCommand extends SearchCommand {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
