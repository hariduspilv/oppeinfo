package ee.hitsa.ois.web.dto.curriculum;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.curriculum.CurriculumFile;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumFileDto extends VersionedCommand {

    private Long id;
    @NotNull
    private Boolean ehis;
    @NotNull
    private Boolean sendEhis;
    @NotNull
    @Valid
    private OisFileCommand oisFile;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.EHIS_FAIL)
    private String ehisFile;

    public static CurriculumFileDto of(CurriculumFile curriculumFile) {
        CurriculumFileDto dto = EntityUtil.bindToDto(curriculumFile, new CurriculumFileDto());
        dto.setOisFile(EntityUtil.bindToDto(curriculumFile.getOisFile(), new OisFileCommand()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getEhisFile() {
        return ehisFile;
    }
    public void setEhisFile(String ehisFile) {
        this.ehisFile = ehisFile;
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

    public OisFileCommand getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileCommand oisFile) {
        this.oisFile = oisFile;
    }
}
