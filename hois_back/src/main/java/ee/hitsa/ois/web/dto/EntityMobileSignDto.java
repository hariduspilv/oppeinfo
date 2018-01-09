package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.BaseEntityWithId;

public class EntityMobileSignDto {
    private Long id;
    private Long version;
    private String challengeID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getChallengeID() {
        return challengeID;
    }

    public void setChallengeID(String challengeID) {
        this.challengeID = challengeID;
    }

    public static EntityMobileSignDto of(BaseEntityWithId entity, String challengeID) {
        EntityMobileSignDto dto = new EntityMobileSignDto();
        dto.setId(entity.getId());
        dto.setVersion(entity.getVersion());
        dto.setChallengeID(challengeID);
        return dto;
    }

}
