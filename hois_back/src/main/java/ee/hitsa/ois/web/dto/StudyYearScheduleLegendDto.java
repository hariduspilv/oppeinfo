package ee.hitsa.ois.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.school.StudyYearScheduleLegend;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class StudyYearScheduleLegendDto extends VersionedCommand {

    private Long id;
    @NotNull
    @Size(max = 2)
    private String code;
    @NotNull
    @Size(max = 50)
    private String nameEt;
    @Size(max = 50)
    private String nameEn;
    @NotNull
    @Size(max = 7)
    private String color;
    private Boolean inUse;

    public static StudyYearScheduleLegendDto of(StudyYearScheduleLegend l) {
        StudyYearScheduleLegendDto dto = new StudyYearScheduleLegendDto();
        EntityUtil.bindToDto(l, dto);
        dto.setInUse(Boolean.valueOf(!l.getStudyYearSchedules().isEmpty()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

}
