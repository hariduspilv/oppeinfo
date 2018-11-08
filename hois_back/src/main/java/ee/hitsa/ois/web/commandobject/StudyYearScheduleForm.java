package ee.hitsa.ois.web.commandobject;

import java.util.Set;

public class StudyYearScheduleForm {

    private Long studyYearId;
    private Set<Long> schoolDepartments;
    private Boolean showMine;

    public Long getStudyYearId() {
        return studyYearId;
    }

    public void setStudyYearId(Long studyYearId) {
        this.studyYearId = studyYearId;
    }

    public Set<Long> getSchoolDepartments() {
        return schoolDepartments;
    }

    public void setSchoolDepartments(Set<Long> schoolDepartments) {
        this.schoolDepartments = schoolDepartments;
    }

    public Boolean getShowMine() {
        return showMine;
    }

    public void setShowMine(Boolean showMine) {
        this.showMine = showMine;
    }

}
