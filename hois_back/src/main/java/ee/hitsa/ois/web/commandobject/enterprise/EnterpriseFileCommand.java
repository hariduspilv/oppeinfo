package ee.hitsa.ois.web.commandobject.enterprise;

import ee.hitsa.ois.web.dto.OisFileDto;

public class EnterpriseFileCommand {

    private OisFileDto file;

    public OisFileDto getFile() {
        return file;
    }

    public void setFile(OisFileDto file) {
        this.file = file;
    }

}