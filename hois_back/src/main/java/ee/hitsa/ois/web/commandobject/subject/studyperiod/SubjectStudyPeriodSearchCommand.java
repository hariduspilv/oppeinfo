package ee.hitsa.ois.web.commandobject.subject.studyperiod;

import java.util.List;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.SearchCommand;

public class SubjectStudyPeriodSearchCommand extends SearchCommand {
    
    private EntityConnectionCommand subjectObject;
    private EntityConnectionCommand teacherObject;
    private List<Long> studyPeriods;
    private Long studentGroup;
    private Long student;
    private Long studyPeriod;
    private EntityConnectionCommand curriculum;
    private Long department;
    private Long teacher;
    private Long subject;
    private String programStatus;

    public Long getStudent() {
        return student;
    }
    public void setStudent(Long student) {
        this.student = student;
    }
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
    public EntityConnectionCommand getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(EntityConnectionCommand curriculum) {
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
    public EntityConnectionCommand getSubjectObject() {
        return subjectObject;
    }
    public void setSubjectObject(EntityConnectionCommand subjectObject) {
        this.subjectObject = subjectObject;
    }
    public EntityConnectionCommand getTeacherObject() {
        return teacherObject;
    }
    public void setTeacherObject(EntityConnectionCommand teacherObject) {
        this.teacherObject = teacherObject;
    }
    public List<Long> getStudyPeriods() {
        return studyPeriods;
    }
    public void setStudyPeriods(List<Long> studyPeriods) {
        this.studyPeriods = studyPeriods;
    }
    public String getProgramStatus() {
        return programStatus;
    }
    public void setProgramStatus(String programStatus) {
        this.programStatus = programStatus;
    }
}
