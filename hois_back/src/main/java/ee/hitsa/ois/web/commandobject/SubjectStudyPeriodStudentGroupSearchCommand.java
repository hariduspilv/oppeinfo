package ee.hitsa.ois.web.commandobject;

public class SubjectStudyPeriodStudentGroupSearchCommand {

    private Long studyPeriod;
    private Long curriculum;
    private Long department;
    private Long studentGroupId;

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }

    public Long getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }
}
