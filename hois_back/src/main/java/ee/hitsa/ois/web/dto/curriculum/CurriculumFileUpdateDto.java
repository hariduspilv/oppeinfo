package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;

/**
 * Goal of this dto is to prevent OisFile.fdata to load with curriculum entity. 
 * Files are saved/updated/deleted with their own queries
 */
public class CurriculumFileUpdateDto {
    
    private Long id;
    private Boolean ehis;
    private Boolean sendEhis;
    private OisFileViewDto oisFile;
    private String ehisFile;
    
    public static CurriculumFileUpdateDto of(CurriculumFile curriculumFile) {
        CurriculumFileUpdateDto dto = new CurriculumFileUpdateDto();
        EntityUtil.bindToDto(curriculumFile, dto, "oisFile");
        dto.setOisFile(EntityUtil.bindToDto(curriculumFile.getOisFile(), new OisFileViewDto(), "fdata"));
        return dto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getEhis() {
        return ehis;
    }
    public void setEhis(Boolean ehis) {
        this.ehis = ehis;
    }
    public Boolean getSendEhis() {
        return sendEhis;
    }
    public void setSendEhis(Boolean sendEhis) {
        this.sendEhis = sendEhis;
    }
    public OisFileViewDto getOisFile() {
        return oisFile;
    }
    public void setOisFile(OisFileViewDto oisFile) {
        this.oisFile = oisFile;
    }
    public String getEhisFile() {
        return ehisFile;
    }
    public void setEhisFile(String ehisFile) {
        this.ehisFile = ehisFile;
    }
}
