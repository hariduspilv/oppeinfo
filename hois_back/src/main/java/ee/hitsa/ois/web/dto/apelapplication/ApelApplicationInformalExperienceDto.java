package ee.hitsa.ois.web.dto.apelapplication;

import ee.hitsa.ois.domain.apelapplication.ApelApplicationInformalExperience;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class ApelApplicationInformalExperienceDto extends VersionedCommand {

    private Long id;
    private String nameEt;
    private String placeTime;
    private Short hours;
    private String documents;
    private String type;

    public static ApelApplicationInformalExperienceDto of(
            ApelApplicationInformalExperience applicationInformalExperience) {
        ApelApplicationInformalExperienceDto dto = EntityUtil.bindToDto(applicationInformalExperience,
                new ApelApplicationInformalExperienceDto());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getPlaceTime() {
        return placeTime;
    }

    public void setPlaceTime(String placeTime) {
        this.placeTime = placeTime;
    }

    public Short getHours() {
        return hours;
    }

    public void setHours(Short hours) {
        this.hours = hours;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
