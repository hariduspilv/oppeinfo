package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.service.OisFileService;
import ee.hitsa.ois.util.EntityUtil;

public class OisFileEditDto extends OisFileViewDto {

    private byte[] fdata;

    public byte[] getFdata() {
        return fdata;
    }

    public void setFdata(byte[] fdata) {
        this.fdata = fdata;
    }

    /**
     * Use this dto for editing files on frontend
     * fdata wont be sent, id will be encrypted
     * fdata can be recieved for saving
     * @param oisFile
     * @return
     */
    public static OisFileEditDto of(OisFile oisFile) {
        OisFileEditDto dto = EntityUtil.bindToDto(oisFile, new OisFileEditDto(), "id", "fdata");
        dto.setId(OisFileService.encryptAndDecodeId(oisFile.getId()));
        return dto;
    }
}
