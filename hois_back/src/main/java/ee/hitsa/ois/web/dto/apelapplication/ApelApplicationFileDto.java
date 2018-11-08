package ee.hitsa.ois.web.dto.apelapplication;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationFile;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;
import ee.hitsa.ois.web.dto.InsertedChangedVersionDto;

public class ApelApplicationFileDto extends InsertedChangedVersionDto {
    private Long id;

    @NotNull
    @Valid
    private OisFileViewDto oisFile;

    public static ApelApplicationFileDto of(ApelApplicationFile applicationFile) {
        ApelApplicationFileDto dto = EntityUtil.bindToDto(applicationFile, new ApelApplicationFileDto(), "oisFile");
        dto.setOisFile(OisFileViewDto.of(applicationFile.getOisFile()));
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
