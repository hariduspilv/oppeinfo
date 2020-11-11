package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class JournalSearchDto {

    private Long id;
    private String studentGroups;
    private String nameEt;
    private String teachers;
    private AutocompleteResult modules;
    private List<AutocompleteResult> moduleObjects;
    private Integer plannedHours;
    private Integer usedHours;
    private String status;
    private String curriculums;
    private Boolean isReviewOk;
    private LocalDate reviewDate;
    private String studyYear;
    private Long studentCount;
    private Boolean canEdit;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStudentGroups() {
        return studentGroups;
    }
    
    public void setStudentGroups(String studentGroups) {
        this.studentGroups = studentGroups;
    }
    
    public String getNameEt() {
        return nameEt;
    }
    
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    
    public String getTeachers() {
        return teachers;
    }
    
    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public AutocompleteResult getModules() {
        return modules;
    }

    public void setModules(AutocompleteResult modules) {
        this.modules = modules;
    }

    public List<AutocompleteResult> getModuleObjects() {
        return moduleObjects;
    }

    public void setModuleObjects(List<AutocompleteResult> moduleObjects) {
        this.moduleObjects = moduleObjects;
    }

    public Integer getPlannedHours() {
        return plannedHours;
    }
    
    public void setPlannedHours(Integer plannedHours) {
        this.plannedHours = plannedHours;
    }
    
    public Integer getUsedHours() {
        return usedHours;
    }
    
    public void setUsedHours(Integer usedHours) {
        this.usedHours = usedHours;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getCurriculums() {
        return curriculums;
    }
    
    public void setCurriculums(String curriculums) {
        this.curriculums = curriculums;
    }
    
    public Boolean getIsReviewOk() {
        return isReviewOk;
    }

    public void setIsReviewOk(Boolean isReviewOk) {
        this.isReviewOk = isReviewOk;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }

    public Long getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Long studentCount) {
        this.studentCount = studentCount;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }
    
    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
    
}
