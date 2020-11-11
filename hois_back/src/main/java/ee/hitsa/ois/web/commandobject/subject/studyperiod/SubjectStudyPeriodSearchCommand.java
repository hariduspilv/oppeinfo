package ee.hitsa.ois.web.commandobject.subject.studyperiod;

import java.util.List;

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;
import ee.hitsa.ois.web.commandobject.SearchCommand;

public class SubjectStudyPeriodSearchCommand extends SearchCommand {
    
    private EntityConnectionCommand subjectObject;
    private EntityConnectionCommand teacherObject;
    private EntityConnectionCommand studentGroupObject;
    private EntityConnectionCommand curriculumObject;
    private List<Long> studyPeriods;
    private List<Long> studentGroup;
    private Long student;
    private Long studyPeriod;
    private Long studyYear;
    private EntityConnectionCommand curriculum;
    private Long department;
    private List<Long> teacher;
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
    public EntityConnectionCommand getSubjectObject() {
        return subjectObject;
    }
    public void setSubjectObject(EntityConnectionCommand subjectObject) {
        this.subjectObject = subjectObject;
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
    public EntityConnectionCommand getStudentGroupObject() {
        return studentGroupObject;
    }
    public void setStudentGroupObject(EntityConnectionCommand studentGroupObject) {
        this.studentGroupObject = studentGroupObject;
    }
    public EntityConnectionCommand getCurriculumObject() {
        return curriculumObject;
    }
    public void setCurriculumObject(EntityConnectionCommand curriculumObject) {
        this.curriculumObject = curriculumObject;
    }
    public List<Long> getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(List<Long> studentGroup) {
        this.studentGroup = studentGroup;
    }
    public EntityConnectionCommand getTeacherObject() {
        return teacherObject;
    }
    public void setTeacherObject(EntityConnectionCommand teacherObject) {
        this.teacherObject = teacherObject;
    }
    public List<Long> getTeacher() {
        return teacher;
    }
    public void setTeacher(List<Long> teacher) {
        this.teacher = teacher;
    }
    public Long getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }
}
