package ee.hitsa.ois.web.commandobject;

public class StudentAutocompleteCommand extends AutocompleteCommand {

    private Boolean active;
    private Boolean studying;
    private Boolean academicLeave;
    private Boolean nominalStudy;
    private Boolean higher;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getStudying() {
        return studying;
    }

    public void setStudying(Boolean studying) {
        this.studying = studying;
    }

    public Boolean getAcademicLeave() {
        return academicLeave;
    }

    public void setAcademicLeave(Boolean academicLeave) {
        this.academicLeave = academicLeave;
    }

    public Boolean getNominalStudy() {
        return nominalStudy;
    }

    public void setNominalStudy(Boolean nominalStudy) {
        this.nominalStudy = nominalStudy;
    }

    public Boolean getHigher() {
        return higher;
    }

    public void setHigher(Boolean higher) {
        this.higher = higher;
    }

}
