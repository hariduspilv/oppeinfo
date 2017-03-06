package ee.hitsa.ois.web.commandobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityConnectionCommand {
    public Long id;

    public EntityConnectionCommand() {
    }

    public EntityConnectionCommand(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
