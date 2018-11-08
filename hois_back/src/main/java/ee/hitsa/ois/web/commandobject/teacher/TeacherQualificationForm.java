package ee.hitsa.ois.web.commandobject.teacher;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class TeacherQualificationForm extends VersionedCommand {

    private Long id;
    @Required
    @ClassifierRestriction(MainClassCode.EHIS_KVALIFIKATSIOON)
    private String qualification;
    @Required
    @ClassifierRestriction(MainClassCode.EHIS_KVALIFIKATSIOON_NIMI)
    private String qualificationName;
    @Required
    @ClassifierRestriction(MainClassCode.RIIK)
    private String state;
    @ClassifierRestriction(MainClassCode.EHIS_EESTI_OPPEASUTUS)
    private String school;
    @ClassifierRestriction(MainClassCode.EHIS_EESTI_OPPEASUTUS_ENDINE)
    private String exSchool;
    @ClassifierRestriction(MainClassCode.EHIS_EDUCATION_LEVEL)
    private String studyLevel;
    @ClassifierRestriction(MainClassCode.EHIS_OPETAJA_KEEL)
    private String language;
    @Size(max = 255)
    private String qualificationOther;
    @Min(1000)
    @Max(3000)
    private Short year;
    @Size(max = 255)
    private String schoolOther;
    @Size(max = 100)
    private String diplomaNr;
    @NotNull
    private LocalDate endDate;
    @Size(max = 255)
    private String specialty;
    @Size(max = 4000)
    private String addInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getExSchool() {
        return exSchool;
    }

    public void setExSchool(String exSchool) {
        this.exSchool = exSchool;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getQualificationOther() {
        return qualificationOther;
    }

    public void setQualificationOther(String qualificationOther) {
        this.qualificationOther = qualificationOther;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getSchoolOther() {
        return schoolOther;
    }

    public void setSchoolOther(String schoolOther) {
        this.schoolOther = schoolOther;
    }

    public String getDiplomaNr() {
        return diplomaNr;
    }

    public void setDiplomaNr(String diplomaNr) {
        this.diplomaNr = diplomaNr;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
}
