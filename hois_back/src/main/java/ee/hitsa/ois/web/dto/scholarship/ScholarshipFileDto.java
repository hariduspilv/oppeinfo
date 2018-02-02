package ee.hitsa.ois.web.dto.scholarship;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplicationFile;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.OisFileDto;

public class ScholarshipFileDto {
    private OisFileDto oisFile;
    private Long id;
    
    public static ScholarshipFileDto of(ScholarshipApplicationFile file) {
        ScholarshipFileDto dto = new ScholarshipFileDto();
        dto.setOisFile(EntityUtil.bindToDto(file.getOisFile(), new OisFileDto(), "fdata"));
        dto.setId(EntityUtil.getId(file));
        return dto;
    }

    public OisFileDto getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileDto oisFile) {
        this.oisFile = oisFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
