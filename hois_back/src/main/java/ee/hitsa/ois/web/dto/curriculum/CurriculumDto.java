package ee.hitsa.ois.web.dto.curriculum;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.CurriculumForm;

public class CurriculumDto extends CurriculumForm {
    
    private Long id; 
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;
    
    /**
     * TODO: finish it
     */
    public static CurriculumDto of(Curriculum curriculum) {
        CurriculumDto dto = EntityUtil.bindToDto
                (curriculum, new CurriculumDto(), 
                 "versions", "studyLanguages", "studyForms", "schoolDepartments", "files", 
                 "jointPartners", "specialities", "modules", "occupations", "grades");
                
        if(curriculum.getStudyLanguages() != null) {
            Set<String> langs = curriculum.getStudyLanguages().stream().
                    map(lang -> EntityUtil.getNullableCode(lang.getStudyLang())).collect(Collectors.toSet());
            dto.setStudyLanguages(langs);
        }
        if(curriculum.getStudyForms() != null) {
            Set<String> studyForms = curriculum.getStudyForms().stream().map
                    (f -> EntityUtil.getNullableCode(f.getStudyForm())).collect(Collectors.toSet());
            dto.setStudyForms(studyForms);
        }
        if(curriculum.getDepartments() != null) {
            Set<Long> departments = curriculum.getDepartments().stream().
                    map(d -> EntityUtil.getNullableId(d.getSchoolDepartment())).collect(Collectors.toSet());
            dto.setSchoolDepartments(departments);
        }
        if(curriculum.getFiles() != null) {
            Set<CurriculumFileDto> files = curriculum.getFiles().stream().map
                    (f -> CurriculumFileDto.of(f)).collect(Collectors.toSet());
            dto.setFiles(files);
        }
        if(curriculum.getVersions() != null) {
            Set<CurriculumVersionDto> versions = curriculum.getVersions().stream().map
                    (v -> CurriculumVersionDto.of(v)).collect(Collectors.toSet()); //TODO
            dto.setVersions(versions);
        }
        if(curriculum.getJointPartners() != null) {
            Set<CurriculumJointPartnerDto> jointPartners = curriculum.getJointPartners().stream().
                    map(p -> CurriculumJointPartnerDto.of(p)).collect(Collectors.toSet());
            dto.setJointPartners(jointPartners);
        }
        if(curriculum.getSpecialities() != null) {
            Set<CurriculumSpecialityDto> specialities = curriculum.getSpecialities().stream().
                    map(s -> CurriculumSpecialityDto.of(s)).collect(Collectors.toSet());
            dto.setSpecialities(specialities);
        }
        if(curriculum.getModules() != null) {
            Set<CurriculumModuleDto> modules = curriculum.getModules().stream().
                    map(m -> CurriculumModuleDto.of(m)).collect(Collectors.toSet());
            dto.setModules(modules);
        }
        if(curriculum.getOccupations() != null) {
            Set<CurriculumOccupationDto> occuptations = curriculum.getOccupations().stream().
                    map(o -> CurriculumOccupationDto.of(o)).collect(Collectors.toSet());
            dto.setOccupations(occuptations);
        }
        if(curriculum.getGrades() != null) {
            Set<CurriculumGradeDto> grades = curriculum.getGrades().stream()
                    .map(g -> CurriculumGradeDto.of(g)).collect(Collectors.toSet());
            dto.setGrades(grades);
        }
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
