package ee.hitsa.ois.web.dto.student;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.GradeDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumResult;

public class StudentVocationalResultModuleThemeDto {

    private Long curriculumVersionModuleId;
    private CurriculumModuleResult module;
    private AutocompleteResult theme;
    private AutocompleteResult outcome;
    private CurriculumResult curriculum;
    private BigDecimal credits;
    private GradeDto grade;
    private LocalDate date;
    private List<AutocompleteResult> teachers = new ArrayList<>();
    private String teachersAsString;
    private String studyYear;
    private LocalDate studyYearStartDate;
    private Boolean isApelTransfer;
    private Boolean isFormalLearning;
    private Boolean isCurrentCurriculumVersionResult; 
    private List<Long> replacedModules;

    public Long getCurriculumVersionModuleId() {
        return curriculumVersionModuleId;
    }

    public void setCurriculumVersionModuleId(Long curriculumVersionModuleId) {
        this.curriculumVersionModuleId = curriculumVersionModuleId;
    }

    public CurriculumModuleResult getModule() {
        return module;
    }

    public void setModule(CurriculumModuleResult module) {
        this.module = module;
    }

    public AutocompleteResult getTheme() {
        return theme;
    }

    public void setTheme(AutocompleteResult theme) {
        this.theme = theme;
    }

    public AutocompleteResult getOutcome() {
        return outcome;
    }

    public void setOutcome(AutocompleteResult outcome) {
        this.outcome = outcome;
    }

    public CurriculumResult getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(CurriculumResult curriculum) {
        this.curriculum = curriculum;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public GradeDto getGrade() {
        return grade;
    }

    public void setGrade(GradeDto grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<AutocompleteResult> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<AutocompleteResult> teachers) {
        this.teachers = teachers;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }

    public LocalDate getStudyYearStartDate() {
        return studyYearStartDate;
    }

    public void setStudyYearStartDate(LocalDate studyYearStartDate) {
        this.studyYearStartDate = studyYearStartDate;
    }

    public Boolean getIsApelTransfer() {
        return isApelTransfer;
    }

    public void setIsApelTransfer(Boolean isApelTransfer) {
        this.isApelTransfer = isApelTransfer;
    }

    public Boolean getIsFormalLearning() {
        return isFormalLearning;
    }

    public void setIsFormalLearning(Boolean isFormalLearning) {
        this.isFormalLearning = isFormalLearning;
    }

    public Boolean getIsCurrentCurriculumVersionResult() {
        return isCurrentCurriculumVersionResult;
    }

    public void setIsCurrentCurriculumVersionResult(Boolean isCurrentCurriculumVersionResult) {
        this.isCurrentCurriculumVersionResult = isCurrentCurriculumVersionResult;
    }

    public List<Long> getReplacedModules() {
        return replacedModules;
    }

    public void setReplacedModules(List<Long> replacedModules) {
        this.replacedModules = replacedModules;
    }

    public String getTeachersAsString() {
        return teachersAsString;
    }

    public void setTeachersAsString(String teachersAsString) {
        this.teachersAsString = teachersAsString;
    }
    
    public static class CurriculumModuleResult extends AutocompleteResult {

        private String moduleCode;
        private String versionCode;
        private Short orderNr;
        private BigDecimal credits;
        
        public CurriculumModuleResult(AutocompleteResult result) {
            super(result.getId(), result.getNameEt(), result.getNameEn());
        }
        
        public CurriculumModuleResult(Long id, String nameEt, String nameEn, String moduleCode, String versionCode, Short orderNr, BigDecimal credits) {
            super(id, nameEt, nameEn);
            this.moduleCode = moduleCode;
            this.versionCode = versionCode;
            this.orderNr = orderNr;
            this.credits = credits;
        }

        public String getModuleCode() {
            return moduleCode;
        }

        public void setModuleCode(String moduleCode) {
            this.moduleCode = moduleCode;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public Short getOrderNr() {
            return orderNr;
        }

        public void setOrderNr(Short orderNr) {
            this.orderNr = orderNr;
        }

        public BigDecimal getCredits() {
            return credits;
        }

        public void setCredits(BigDecimal credits) {
            this.credits = credits;
        }
    }
}
