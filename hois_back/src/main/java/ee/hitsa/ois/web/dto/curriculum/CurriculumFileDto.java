package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.CurriculumFileForm;
import ee.hitsa.ois.web.commandobject.CurriculumFileUpdateDto;
import ee.hitsa.ois.web.dto.OisFileDto;

public class CurriculumFileDto extends CurriculumFileForm {

    private Long id;

    public static CurriculumFileDto of(CurriculumFile curriculumFile) {
        CurriculumFileDto dto = EntityUtil.bindToDto(curriculumFile, new CurriculumFileDto());
        dto.setOisFile(EntityUtil.bindToDto(curriculumFile.getOisFile(), new OisFileDto()));
        return dto;
    }
    
    public static CurriculumFileDto of(CurriculumFileUpdateDto updateDto) {
        return EntityUtil.bindToDto(updateDto, new CurriculumFileDto());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
