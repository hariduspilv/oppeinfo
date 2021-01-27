package ee.hitsa.ois.web.commandobject.report;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

import java.util.ArrayList;
import java.util.List;

public class CurriculumCompletionCommand {

    private String name;
    private List<String> status;
    private EntityConnectionCommand curriculumVersion;
    private Long studentGroup;
    private String studyForm;
    private String studyLoad;
    private Boolean isHigher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStatus() {
        return status != null ? status : (status = new ArrayList<>());
    }

    public void setStatus(List<String> status) {
        this.status = status;
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

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(String studyLoad) {
        this.studyLoad = studyLoad;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }
    
}
