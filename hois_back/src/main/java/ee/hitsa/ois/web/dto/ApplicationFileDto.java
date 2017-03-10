package ee.hitsa.ois.web.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.application.ApplicationFile;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;

public class ApplicationFileDto extends InsertedChangedVersionDto {

    private Long id;

    @NotNull
    @Valid
    private OisFileCommand oisFile;

    public static ApplicationFileDto of(ApplicationFile applicationFile) {
        ApplicationFileDto dto = EntityUtil.bindToDto(applicationFile, new ApplicationFileDto(), "oisFile");
        dto.setOisFile(EntityUtil.bindToDto(applicationFile.getOisFile(), new OisFileCommand()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OisFileCommand getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileCommand oisFile) {
        this.oisFile = oisFile;
    }

}
