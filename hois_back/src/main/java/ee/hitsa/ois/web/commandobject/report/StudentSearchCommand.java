package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.validation.DateRange;

@DateRange(from = "birthdateFrom", thru = "studyStartThru")
@DateRange(from = "studyStartFrom", thru = "studyStartThru")
public class StudentSearchCommand {

    private String name;
    private String idcode;
    private LocalDate birthdateFrom;
    private LocalDate birthdateThru;
    private LocalDate studyStartFrom;
    private LocalDate studyStartThru;
    private LocalDate nominalStudyEndFrom;
    private LocalDate nominalStudyEndThru;
    private String studyLevel;
    private List<Long> curriculumVersions;
    private List<Long> studentGroups;
    private String studyLoad;
    private String studyForm;
    private String status;
    private String fin;
    private String finSpecific;
    private String language;
    private String dormitory;
    private String studyCompany;
    private String previousStudyLevel;
    private String previousSchoolName;
    private Boolean isHigher;
    private Boolean displayRepresentative = Boolean.FALSE;

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

    public Boolean getDisplayRepresentative() {
        return displayRepresentative;
    }

    public void setDisplayRepresentative(Boolean displayRepresentative) {
        this.displayRepresentative = displayRepresentative;
    }

    public String getStudyCompany() {
        return studyCompany;
    }

    public void setStudyCompany(String studyCompany) {
        this.studyCompany = studyCompany;
    }

    public String getPreviousStudyLevel() {
        return previousStudyLevel;
    }

    public void setPreviousStudyLevel(String previousStudyLevel) {
        this.previousStudyLevel = previousStudyLevel;
    }

    public String getPreviousSchoolName() {
        return previousSchoolName;
    }

    public void setPreviousSchoolName(String previousSchoolName) {
        this.previousSchoolName = previousSchoolName;
    }

    public List<Long> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<Long> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<Long> getCurriculumVersions() {
        return curriculumVersions;
    }

    public void setCurriculumVersions(List<Long> curriculumVersions) {
        this.curriculumVersions = curriculumVersions;
    }

    public LocalDate getNominalStudyEndFrom() {
        return nominalStudyEndFrom;
    }

    public void setNominalStudyEndFrom(LocalDate nominalStudyEndFrom) {
        this.nominalStudyEndFrom = nominalStudyEndFrom;
    }

    public LocalDate getNominalStudyEndThru() {
        return nominalStudyEndThru;
    }

    public void setNominalStudyEndThru(LocalDate nominalStudyEndThru) {
        this.nominalStudyEndThru = nominalStudyEndThru;
    }
    
}
