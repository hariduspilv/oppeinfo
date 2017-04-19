package ee.hitsa.ois.web.dto.curriculum;

import java.util.Set;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class CurriculumOccupationDto extends VersionedCommand {

    private Long id;
    @NotEmpty
    @ClassifierRestriction({MainClassCode.KUTSE, MainClassCode.OSAKUTSE})
    private String occupation;
    @NotNull
    private Boolean occupationGrant;
    private Set<String> specialities;

    public static CurriculumOccupationDto of(CurriculumOccupation occupation) {
        CurriculumOccupationDto dto = EntityUtil.bindToDto(occupation, new CurriculumOccupationDto(), "specialities");
        dto.setSpecialities(StreamUtil.toMappedSet(o -> EntityUtil.getNullableCode(o.getSpeciality()), occupation.getSpecialities()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Boolean getOccupationGrant() {
        return occupationGrant;
    }

    public void setOccupationGrant(Boolean occupationGrant) {
        this.occupationGrant = occupationGrant;
    }

    public Set<String> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<String> specialities) {
        this.specialities = specialities;
    }
}
