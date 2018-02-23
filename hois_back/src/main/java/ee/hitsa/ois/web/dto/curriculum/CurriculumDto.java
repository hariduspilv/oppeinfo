package ee.hitsa.ois.web.dto.curriculum;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.curriculum.CurriculumForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumDto extends CurriculumForm {
    private Long id;
    private LocalDateTime inserted;
    private String insertedBy;
    private LocalDateTime changed;
    private String changedBy;
    private String status;
    private String stateCurrClass;
    private String ehisSchool;
    private Set<CurriculumVersionDto> versions;
    private Boolean canChange;
    private Boolean canConfirm;
    private Boolean canClose;
    private Boolean canDelete;
    private AutocompleteResult stateCurriculum;
    private Boolean canHaveOccupations;
    
    /*
     * Two sets below actually bring the same info
     */
    private Set<CurriculumOccupationDto> occupations;
    private Set<CurriculumOccupationViewDto> curriculumOccupations;


    public static CurriculumDto afterDeletingOccupation(Curriculum curriculum) {
        CurriculumDto dto = new CurriculumDto();   
        dto.setModules(StreamUtil.toMappedSet(CurriculumModuleDto::of, curriculum.getModules()));
        return dto;
    }

    public static CurriculumDto forModuleForm(Curriculum curriculum) {
        CurriculumDto dto = new CurriculumDto(); 
        dto.setId(EntityUtil.getId(curriculum));
        dto.setOccupation(curriculum.getOccupation());
        dto.setOccupations(StreamUtil.toMappedSet(CurriculumOccupationDto::of, curriculum.getOccupations()));
        dto.setCanHaveOccupations(Boolean.valueOf(CurriculumUtil.canHaveOccupations(curriculum)));
        return dto;
    }

    public static CurriculumDto forVersionForm(Curriculum curriculum) {
        CurriculumDto dto = new CurriculumDto();
        dto.setId(EntityUtil.getId(curriculum));
        dto.setNameEt(curriculum.getNameEt());
        dto.setNameEn(curriculum.getNameEn());
        dto.setCode(curriculum.getCode());
        dto.setCredits(curriculum.getCredits());
        dto.setOccupation(curriculum.getOccupation());
        dto.setStudyPeriod(curriculum.getStudyPeriod());
        //TODO: autocomplete result here would be better
        dto.setSpecialities(StreamUtil.toMappedSet(CurriculumSpecialityDto::of, curriculum.getSpecialities()));

        dto.setModules(StreamUtil.toMappedSet(CurriculumModuleDto::of, curriculum.getModules()));
        dto.setStudyForms(StreamUtil.toMappedSet(f -> EntityUtil.getNullableCode(f.getStudyForm()), curriculum.getStudyForms()));

        //TODO: use partial dto 
        dto.setOccupations(StreamUtil.toMappedSet(CurriculumOccupationDto::of, curriculum.getOccupations()));
        dto.setCanHaveOccupations(Boolean.valueOf(CurriculumUtil.canHaveOccupations(curriculum)));

        return dto;
    }

    /**
     * Versions are set in service, as it depends on user rights
     */
    public static CurriculumDto of(Curriculum curriculum) {
        CurriculumDto dto = EntityUtil.bindToDto
                (curriculum, new CurriculumDto(), 
                 "versions", "studyLanguages", "studyForms", "schoolDepartments", "files", 
                 "jointPartners", "specialities", "modules", "occupations", "grades", "stateCurriculum");
                
        dto.setStudyLanguages(StreamUtil.toMappedSet(lang -> EntityUtil.getNullableCode(lang.getStudyLang()), curriculum.getStudyLanguages()));
        dto.setStudyForms(StreamUtil.toMappedSet(f -> EntityUtil.getNullableCode(f.getStudyForm()), curriculum.getStudyForms()));
        dto.setSchoolDepartments(StreamUtil.toMappedSet(d -> EntityUtil.getNullableId(d.getSchoolDepartment()), curriculum.getDepartments()));
        dto.setJointPartners(StreamUtil.toMappedSet(CurriculumJointPartnerDto::of, curriculum.getJointPartners()));
        dto.setSpecialities(StreamUtil.toMappedSet(CurriculumSpecialityDto::of, curriculum.getSpecialities()));
        //TODO: use partial dto
        dto.setModules(StreamUtil.toMappedSet(CurriculumModuleDto::of, curriculum.getModules()));
        dto.setGrades(StreamUtil.toMappedSet(CurriculumGradeDto::of, curriculum.getGrades()));
        dto.setFiles(StreamUtil.toMappedSet(CurriculumFileUpdateDto::of, curriculum.getFiles()));
        dto.setStateCurrClass(getStateCurrClass(curriculum));        
        dto.setEhisSchool(EntityUtil.getCode(curriculum.getSchool().getEhisSchool()));
        
        dto.setOccupations(StreamUtil.toMappedSet(CurriculumOccupationDto::of, curriculum.getOccupations()));
        dto.setCurriculumOccupations(StreamUtil.toMappedSet(CurriculumOccupationViewDto::of, curriculum.getOccupations()));

        return dto;
    }
    
    /**
     * After creation of new curriculum redirection to edit page occurs, 
     * so only id is required
     */
    public static CurriculumDto onlyId(Curriculum curriculum) {
        CurriculumDto dto = new CurriculumDto();
        dto.setId(EntityUtil.getId(curriculum));
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

    public Set<CurriculumVersionDto> getVersions() {
        return versions != null ? versions : (versions = new HashSet<>());
    }

    public void setVersions(Set<CurriculumVersionDto> versions) {
        this.versions = versions;
    }

    public Boolean getCanChange() {
        return canChange;
    }

    public void setCanChange(Boolean canChange) {
        this.canChange = canChange;
    }

    public Boolean getCanConfirm() {
        return canConfirm;
    }

    public void setCanConfirm(Boolean canConfirm) {
        this.canConfirm = canConfirm;
    }

    public Boolean getCanClose() {
        return canClose;
    }

    public void setCanClose(Boolean canClose) {
        this.canClose = canClose;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(String ehisSchool) {
        this.ehisSchool = ehisSchool;
    }

    public Set<CurriculumOccupationDto> getOccupations() {
        return occupations != null ? occupations : (occupations = new HashSet<>());
    }

    public void setOccupations(Set<CurriculumOccupationDto> occupations) {
        this.occupations = occupations;
    }

    public Set<CurriculumOccupationViewDto> getCurriculumOccupations() {
        return curriculumOccupations;
    }

    public void setCurriculumOccupations(Set<CurriculumOccupationViewDto> curriculumOccupations) {
        this.curriculumOccupations = curriculumOccupations;
    }

    public AutocompleteResult getStateCurriculum() {
        return stateCurriculum;
    }

    public void setStateCurriculum(AutocompleteResult stateCurriculum) {
        this.stateCurriculum = stateCurriculum;
    }

    public Boolean getCanHaveOccupations() {
        return canHaveOccupations;
    }

    public void setCanHaveOccupations(Boolean canHaveOccupations) {
        this.canHaveOccupations = canHaveOccupations;
    }
}
