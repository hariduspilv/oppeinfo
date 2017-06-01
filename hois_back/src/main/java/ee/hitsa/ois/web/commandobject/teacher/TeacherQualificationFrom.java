package ee.hitsa.ois.web.commandobject.teacher;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

import javax.validation.constraints.NotNull;

public class TeacherQualificationFrom extends VersionedCommand {
    private Long id;

    @NotEmpty
    @ClassifierRestriction(MainClassCode.EHIS_KVALIFIKATSIOON)
    private String qualification;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.EHIS_KVALIFIKATSIOON_NIMI)
    private String qualificationName;
    @NotEmpty
    @ClassifierRestriction(MainClassCode.RIIK)
    private String state;
    @ClassifierRestriction(MainClassCode.EHIS_EESTI_OPPEASUTUS)
    private String school;
    @ClassifierRestriction(MainClassCode.EHIS_EESTI_OPPEASUTUS_ENDINE)
    private String exSchool;
    private String qualificationOther;
    @NotNull
    private Short year;
    private String schoolOther;

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
}
