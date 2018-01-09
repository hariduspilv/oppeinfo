package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class StudentAutocompleteCommand extends AutocompleteCommand {

    private Boolean active;
    private Boolean finished;
    private Boolean studying;
    private Boolean academicLeave;
    private Boolean nominalStudy;
    private Boolean higher;
    private List<Long> curriculumVersion;
    private List<Long> studentGroup;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
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

    public List<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(List<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public List<Long> getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(List<Long> studentGroup) {
        this.studentGroup = studentGroup;
    }
}
