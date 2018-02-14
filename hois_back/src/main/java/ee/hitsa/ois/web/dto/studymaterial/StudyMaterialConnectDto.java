package ee.hitsa.ois.web.dto.studymaterial;

import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StudyMaterialConnectDto extends VersionedCommand {

    private Long id;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
}
