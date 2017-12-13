package ee.hitsa.ois.web.dto.curriculum;

import java.util.Set;

import ee.hitsa.ois.domain.curriculum.CurriculumOccupation;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.ClassifierSelection;

/**
 * This class actually brings the same info as CurriculumOccupationDto, 
 * it is only convenient to display the info. 
 * 
 * For now both classes are kept inside CurriculumDto.class, 
 * in order to save time.
 */
public class CurriculumOccupationViewDto extends VersionedCommand {
    
    private Long id;
    private ClassifierSelection occupation;
    private Boolean occupationGrant;
    private Set<ClassifierSelection> specialities;
    private Set<ClassifierSelection> partOccupations;
    private Long curriculum;
    
    public static CurriculumOccupationViewDto of(CurriculumOccupation occupation) {
        CurriculumOccupationViewDto dto = EntityUtil.bindToDto(occupation, new CurriculumOccupationViewDto(), "specialities", "curriculum", "occupation");
        dto.setOccupation(ClassifierSelection.of(occupation.getOccupation()));
        dto.setSpecialities(StreamUtil.toMappedSet(o -> ClassifierSelection.of(o.getSpeciality()), occupation.getSpecialities()));
        dto.setCurriculum(EntityUtil.getId(occupation.getCurriculum()));
        dto.setPartOccupations(StreamUtil.toMappedSet(c -> ClassifierSelection.of(c), 
                CurriculumUtil.getPartOccupationClassifiers(occupation)));
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ClassifierSelection getOccupation() {
        return occupation;
    }
    public void setOccupation(ClassifierSelection occupation) {
        this.occupation = occupation;
    }
    public Boolean getOccupationGrant() {
        return occupationGrant;
    }
    public void setOccupationGrant(Boolean occupationGrant) {
        this.occupationGrant = occupationGrant;
    }
    public Set<ClassifierSelection> getSpecialities() {
        return specialities;
    }
    public void setSpecialities(Set<ClassifierSelection> specialities) {
        this.specialities = specialities;
    }
    public Set<ClassifierSelection> getPartOccupations() {
        return partOccupations;
    }
    public void setPartOccupations(Set<ClassifierSelection> partOccupations) {
        this.partOccupations = partOccupations;
    }
    public Long getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }
}
