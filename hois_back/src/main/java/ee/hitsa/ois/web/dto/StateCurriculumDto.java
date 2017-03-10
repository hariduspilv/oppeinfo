package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;

public class StateCurriculumDto extends StateCurriculumForm {
    
    private Long id; 
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;
    
    public static StateCurriculumDto of(StateCurriculum stateCurriculum) {
        StateCurriculumDto dto = EntityUtil.bindToDto(stateCurriculum, new StateCurriculumDto(), "modules", "occupations");
        
        stateCurriculum.getModules().forEach(m ->{
            dto.getModules().add(StateCurriculumModuleDto.of(m));
        });
        
        Set<String> occupations = stateCurriculum.getOccupations().stream().
                map(occupation -> EntityUtil.getNullableCode(occupation.getOccupation())).collect(Collectors.toSet());
        dto.setOccupations(occupations);
        
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getInserted() {
        return inserted;
    }
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
    public String getInsertedBy() {
        return insertedBy;
    }
    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }
    public LocalDateTime getChanged() {
        return changed;
    }
    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }
    public String getChangedBy() {
        return changedBy;
    }
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}
