package ee.hitsa.ois.web.dto.curriculum;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.CurriculumUtil;
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
    private Set<String> partOccupations;
    @NotNull
    private Long curriculum;

    public static CurriculumOccupationDto of(CurriculumOccupation occupation) {
        CurriculumOccupationDto dto = EntityUtil.bindToDto(occupation, new CurriculumOccupationDto(), "specialities", "curriculum");
        dto.setSpecialities(StreamUtil.toMappedSet(o -> EntityUtil.getNullableCode(o.getSpeciality()), occupation.getSpecialities()));
        dto.setCurriculum(EntityUtil.getId(occupation.getCurriculum()));
        dto.setPartOccupations(CurriculumUtil.getPartOccupationsCodes(occupation));
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
        return specialities != null ? specialities : (specialities = new HashSet<>());
    }

    public void setSpecialities(Set<String> specialities) {
        this.specialities = specialities;
    }

    public Set<String> getPartOccupations() {
        return partOccupations;
    }

    public void setPartOccupations(Set<String> partOccupations) {
        this.partOccupations = partOccupations;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }
}
