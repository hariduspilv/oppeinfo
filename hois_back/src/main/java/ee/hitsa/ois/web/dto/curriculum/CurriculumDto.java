package ee.hitsa.ois.web.dto.curriculum;

import java.time.LocalDateTime;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.CurriculumFileUpdateDto;
import ee.hitsa.ois.web.commandobject.CurriculumForm;

public class CurriculumDto extends CurriculumForm {
    private Long id;
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;
    private String status;
    private String stateCurrClass;

    public static CurriculumDto of(Curriculum curriculum) {
        CurriculumDto dto = EntityUtil.bindToDto
                (curriculum, new CurriculumDto(), 
                 "versions", "studyLanguages", "studyForms", "schoolDepartments", "files", 
                 "jointPartners", "specialities", "modules", "occupations", "grades");
                
        dto.setStudyLanguages(StreamUtil.toMappedSet(lang -> EntityUtil.getNullableCode(lang.getStudyLang()), curriculum.getStudyLanguages()));
        dto.setStudyForms(StreamUtil.toMappedSet(f -> EntityUtil.getNullableCode(f.getStudyForm()), curriculum.getStudyForms()));
        dto.setSchoolDepartments(StreamUtil.toMappedSet(d -> EntityUtil.getNullableId(d.getSchoolDepartment()), curriculum.getDepartments()));
        dto.setVersions(StreamUtil.toMappedSet(CurriculumVersionDto::of, curriculum.getVersions()));
        dto.setJointPartners(StreamUtil.toMappedSet(CurriculumJointPartnerDto::of, curriculum.getJointPartners()));
        dto.setSpecialities(StreamUtil.toMappedSet(CurriculumSpecialityDto::of, curriculum.getSpecialities()));
        dto.setModules(StreamUtil.toMappedSet(CurriculumModuleDto::of, curriculum.getModules()));
        dto.setOccupations(StreamUtil.toMappedSet(CurriculumOccupationDto::of, curriculum.getOccupations()));
        dto.setGrades(StreamUtil.toMappedSet(CurriculumGradeDto::of, curriculum.getGrades()));
        dto.setFiles(StreamUtil.toMappedSet(CurriculumFileUpdateDto::of, curriculum.getFiles()));
        dto.setStateCurrClass(getStateCurrClass(curriculum));
        return dto;
    }
    
    private static String getStateCurrClass(Curriculum curriculum) {
        if(curriculum.getStateCurriculum() == null) {
            return null;
        }
        return EntityUtil.getCode(curriculum.getStateCurriculum().getStateCurrClass());
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getStateCurrClass() {
        return stateCurrClass;
    }

    public void setStateCurrClass(String stateCurrClass) {
        this.stateCurrClass = stateCurrClass;
    }
}
