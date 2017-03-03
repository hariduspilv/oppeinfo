package ee.hitsa.ois.web.commandobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityConnectionCommand<ID> {
    public ID id;

    public EntityConnectionCommand() {}

    public EntityConnectionCommand(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
