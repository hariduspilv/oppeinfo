package ee.hitsa.ois.web.commandobject.teacher;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class TeacherContinuingEducationForm extends VersionedCommand {

    private Long id;
    @Required
    @ClassifierRestriction(MainClassCode.EHIS_TAIEND_OPPEAS)
    private String school;
    @Required
    @ClassifierRestriction(MainClassCode.EHIS_TAIEND_VALDKOND)
    private String field;
    @Required
    @ClassifierRestriction(MainClassCode.EHIS_TAIEND_DOK)
    private String diploma;
    @NotNull
    private String nameEt;
    @NotNull
    private LocalDate  diplomaDate;
    private String diplomaNr;
    @NotNull
    private Short capacity;
    private String otherSchool;
    @NotNull
    private boolean isAbroad;
    private String abroadDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public LocalDate getDiplomaDate() {
        return diplomaDate;
    }

    public void setDiplomaDate(LocalDate diplomaDate) {
        this.diplomaDate = diplomaDate;
    }

    public String getDiplomaNr() {
        return diplomaNr;
    }

    public void setDiplomaNr(String diplomaNr) {
        this.diplomaNr = diplomaNr;
    }

    public Short getCapacity() {
        return capacity;
    }

    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }

    public String getOtherSchool() {
        return otherSchool;
    }

    public void setOtherSchool(String otherSchool) {
        this.otherSchool = otherSchool;
    }

    public boolean getIsAbroad() {
        return isAbroad;
    }

    public void setIsAbroad(boolean isAbroad) {
        this.isAbroad = isAbroad;
    }

    public String getAbroadDesc() {
        return abroadDesc;
    }

    public void setAbroadDesc(String abroadDesc) {
        this.abroadDesc = abroadDesc;
    }
}
