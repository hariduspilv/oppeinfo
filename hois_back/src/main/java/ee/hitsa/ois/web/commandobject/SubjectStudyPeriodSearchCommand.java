package ee.hitsa.ois.web.commandobject;

import java.util.List;

public class SubjectStudyPeriodSearchCommand extends SearchCommand {
    
    private String subjectNameAndCode;
    private String teachersFullname;
    private List<Long> studyPeriods;
    private Long studentGroup;
    private Long studyPeriod;
    private Long curriculum;
    private Long department;
    private Long teacher;
    private Long subject;

    public Long getSubject() {
        return subject;
    }
    public void setSubject(Long subject) {
        this.subject = subject;
    }
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
    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
    public Long getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
    public String getSubjectNameAndCode() {
        return subjectNameAndCode;
    }
    public void setSubjectNameAndCode(String subjectNameAndCode) {
        this.subjectNameAndCode = subjectNameAndCode;
    }
    public String getTeachersFullname() {
        return teachersFullname;
    }
    public void setTeachersFullname(String teachersFullname) {
        this.teachersFullname = teachersFullname;
    }
    public List<Long> getStudyPeriods() {
        return studyPeriods;
    }
    public void setStudyPeriods(List<Long> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }
}
