package ee.hitsa.ois.web.commandobject;

import org.springframework.beans.factory.annotation.Value;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.service.OisFileService;
import ee.hitsa.ois.util.EntityUtil;

/**
 * OisFile without fdata, which is supposed to be acquired via OisFileController by id
 */
public class OisFileViewDto {
    private String id;
    private String fname;
    private String ftype;

    @Value("${file.cypher.key}")
    private String key;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public static OisFileViewDto of(OisFile oisFile) {
        OisFileViewDto dto = EntityUtil.bindToDto(oisFile, new OisFileViewDto(), "id");
        dto.setId(OisFileService.encryptAndDecodeId(oisFile.getId()));
        return dto;
    }
}
