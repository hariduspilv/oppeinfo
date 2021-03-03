package ee.hitsa.ois.web.dto.subject.subjectprogram;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectProgramSearchDto {

    private Long id;
    private AutocompleteResult subject;
    private AutocompleteResult studyPeriod;
    private Long subjectStudyPeriod;
    private String teachers;
    private String status;
    private Boolean joint;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public AutocompleteResult getSubject() {
        return subject;
    }
    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }
    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }
    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getJoint() {
        return joint;
    }

    public void setJoint(Boolean joint) {
        this.joint = joint;
    }
}
