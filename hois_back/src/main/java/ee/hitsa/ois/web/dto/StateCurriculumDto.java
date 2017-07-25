package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import java.util.Set;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.dto.curriculum.CurriculumSearchDto;

public class StateCurriculumDto extends StateCurriculumForm {

    private Long id; 
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;
    private Set<CurriculumSearchDto> curricula;
    private String status;

    public static StateCurriculumDto of(StateCurriculum stateCurriculum) {
        StateCurriculumDto dto = EntityUtil.bindToDto(stateCurriculum, new StateCurriculumDto(), "modules", "occupations");
        dto.getModules().addAll(StreamUtil.toMappedList(StateCurriculumModuleDto::of, stateCurriculum.getModules()));
        dto.setOccupations(StreamUtil.toMappedSet(occupation -> EntityUtil.getNullableCode(occupation.getOccupation()), stateCurriculum.getOccupations()));
        dto.setCurricula(StreamUtil.toMappedSet(c -> {
            CurriculumSearchDto curriculumDto = new CurriculumSearchDto();
            curriculumDto.setId(EntityUtil.getId(c));
            curriculumDto.setNameEt(c.getNameEt());
            curriculumDto.setNameEn(c.getNameEn());
            curriculumDto.setCredits(c.getCredits());
            curriculumDto.setStatus(EntityUtil.getCode(c.getStatus()));
            curriculumDto.setSchool(AutocompleteResult.of(c.getSchool()));
            curriculumDto.setValidFrom(c.getValidFrom());
            curriculumDto.setValidThru(c.getValidThru());
            curriculumDto.setOrigStudyLevel(EntityUtil.getCode(c.getOrigStudyLevel()));
            return curriculumDto;
        }, stateCurriculum.getCurricula()));
        return dto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<CurriculumSearchDto> getCurricula() {
        return curricula;
    }

    public void setCurricula(Set<CurriculumSearchDto> curricula) {
        this.curricula = curricula;
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
