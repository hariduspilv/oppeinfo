package ee.hitsa.ois.web.dto;

import javax.xml.bind.annotation.XmlTransient;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplicationFile;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;

public class OisFileDto extends OisFileCommand {
    
    public Long id;
    
    public static OisFileDto of(ScholarshipApplicationFile file) {
        OisFileDto dto = new OisFileDto();
        dto.setId(EntityUtil.getId(file));
        return EntityUtil.bindToDto(file.getOisFile(), dto, "fdata", "id");
    }
    
    @XmlTransient
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
