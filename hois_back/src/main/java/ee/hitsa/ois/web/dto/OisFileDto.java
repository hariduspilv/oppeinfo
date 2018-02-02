package ee.hitsa.ois.web.dto;

import javax.xml.bind.annotation.XmlTransient;

import ee.hitsa.ois.web.commandobject.OisFileCommand;

public class OisFileDto extends OisFileCommand {

    public Long id;

    @XmlTransient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
