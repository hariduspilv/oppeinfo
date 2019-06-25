package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;

import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

@DateRange(from = "birthdateFrom", thru = "studyStartThru")
@DateRange(from = "studyStartFrom", thru = "studyStartThru")
public class StudentSearchCommand {

    private String name;
    private String idcode;
    private LocalDate birthdateFrom;
    private LocalDate birthdateThru;
    private LocalDate studyStartFrom;
    private LocalDate studyStartThru;
    private String studyLevel;
    private EntityConnectionCommand curriculumVersion;
    private Long studentGroup;
    private String studyLoad;
    private String studyForm;
    private String status;
    private String fin;
    private String finSpecific;
    private String language;
    private String dormitory;
    private Boolean isHigher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public LocalDate getBirthdateFrom() {
        return birthdateFrom;
    }

    public void setBirthdateFrom(LocalDate birthdateFrom) {
        this.birthdateFrom = birthdateFrom;
    }

    public LocalDate getBirthdateThru() {
        return birthdateThru;
    }

    public void setBirthdateThru(LocalDate birthdateThru) {
        this.birthdateThru = birthdateThru;
    }

    public LocalDate getStudyStartFrom() {
        return studyStartFrom;
    }

    public void setStudyStartFrom(LocalDate studyStartFrom) {
        this.studyStartFrom = studyStartFrom;
    }

    public LocalDate getStudyStartThru() {
        return studyStartThru;
    }

    public void setStudyStartThru(LocalDate studyStartThru) {
        this.studyStartThru = studyStartThru;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public EntityConnectionCommand getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(EntityConnectionCommand curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(String studyLoad) {
        this.studyLoad = studyLoad;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getFinSpecific() {
        return finSpecific;
    }

    public void setFinSpecific(String finSpecific) {
        this.finSpecific = finSpecific;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
    
}
