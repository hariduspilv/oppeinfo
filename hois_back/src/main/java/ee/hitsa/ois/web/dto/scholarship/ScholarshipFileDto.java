package ee.hitsa.ois.web.dto.scholarship;

import ee.hitsa.ois.domain.scholarship.ScholarshipApplicationFile;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;

public class ScholarshipFileDto {
    private OisFileViewDto oisFile;
    private Long id;
    
    public static ScholarshipFileDto of(ScholarshipApplicationFile file) {
        ScholarshipFileDto dto = new ScholarshipFileDto();
        dto.setOisFile(OisFileViewDto.of(file.getOisFile()));
        dto.setId(EntityUtil.getId(file));
        return dto;
    }

    public OisFileViewDto getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileViewDto oisFile) {
        this.oisFile = oisFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
