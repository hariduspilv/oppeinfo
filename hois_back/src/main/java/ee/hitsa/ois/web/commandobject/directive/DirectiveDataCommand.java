package ee.hitsa.ois.web.commandobject.directive;

import java.util.List;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.NotEmpty;

public class DirectiveDataCommand {

    @NotEmpty
    @ClassifierRestriction(MainClassCode.KASKKIRI)
    private String type;
    private List<Long> curriculumVersion;
    private List<String> studyLevel;
    private Long canceledDirective;
    private List<Long> students;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Long> getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(List<Long> curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public List<String> getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(List<String> studyLevel) {
        this.studyLevel = studyLevel;
    }

    public Long getCanceledDirective() {
        return canceledDirective;
    }

    public void setCanceledDirective(Long canceledDirective) {
        this.canceledDirective = canceledDirective;
    }

    public List<Long> getStudents() {
        return students;
    }

    public void setStudents(List<Long> students) {
        this.students = students;
    }
}