package ee.hitsa.ois.web.commandobject;

public class ProtocolVdataForm {

    private Long curriculumVersion;
    private Long curriculumVersionOccupationModule;
    private Long studyYear;
    private Long teacher;

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Long getCurriculumVersionOccupationModule() {
        return curriculumVersionOccupationModule;
    }

    public void setCurriculumVersionOccupationModule(Long curriculumVersionOccupationModule) {
        this.curriculumVersionOccupationModule = curriculumVersionOccupationModule;
    }

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

}
