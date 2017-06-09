package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.web.commandobject.OisFileCommand;

public class OisFileDto extends OisFileCommand {
    
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
