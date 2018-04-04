package ee.hitsa.ois.web.commandobject.exam;

import ee.hitsa.ois.validation.Required;

public class StudentSearchCriteria {

    @Required
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
