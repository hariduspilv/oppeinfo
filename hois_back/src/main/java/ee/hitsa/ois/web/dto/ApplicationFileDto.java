package ee.hitsa.ois.web.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.application.ApplicationFile;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;

public class ApplicationFileDto extends InsertedChangedVersionDto {

    private Long id;

    @NotNull
    @Valid
    private OisFileViewDto oisFile;

    public static ApplicationFileDto of(ApplicationFile applicationFile) {
        ApplicationFileDto dto = EntityUtil.bindToDto(applicationFile, new ApplicationFileDto(), "oisFile");
        dto.setOisFile(EntityUtil.bindToDto(applicationFile.getOisFile(), new OisFileViewDto()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OisFileViewDto getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileViewDto oisFile) {
        this.oisFile = oisFile;
    }

}
