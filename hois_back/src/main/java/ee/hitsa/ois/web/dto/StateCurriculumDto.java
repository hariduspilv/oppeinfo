package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;

public class StateCurriculumDto extends StateCurriculumForm {

    private Long id; 
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;

    public static StateCurriculumDto of(StateCurriculum stateCurriculum) {
        StateCurriculumDto dto = EntityUtil.bindToDto(stateCurriculum, new StateCurriculumDto(), "modules", "occupations");
        
        dto.getModules().addAll(StreamUtil.toMappedList(StateCurriculumModuleDto::of, stateCurriculum.getModules()));
        dto.setOccupations(StreamUtil.toMappedSet(occupation -> EntityUtil.getNullableCode(occupation.getOccupation()), stateCurriculum.getOccupations()));
        
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
