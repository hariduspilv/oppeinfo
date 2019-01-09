package ee.hitsa.ois.web.commandobject.student;

public class StudentSpecialitySearchCommand {

    private Long curriculumVersion;
    /* Students without speciality */
    private Boolean woSpeciality;
    
    public Long getCurriculumVersion() {
        return curriculumVersion;
    }
    
    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    
    public Boolean getWoSpeciality() {
        return woSpeciality;
    }
    
    public void setWoSpeciality(Boolean woSpeciality) {
        this.woSpeciality = woSpeciality;
    }
}
