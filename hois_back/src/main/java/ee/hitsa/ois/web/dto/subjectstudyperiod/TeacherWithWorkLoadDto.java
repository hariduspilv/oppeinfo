package ee.hitsa.ois.web.dto.subjectstudyperiod;

import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.lessonPlan.TeacherLoadSummaryDto;

public class TeacherWithWorkLoadDto extends TeacherLoadSummaryDto {
    
    private AutocompleteResult studyPeriod;
    private String subjectCode;
    private AutocompleteResult subject;
    private String proportion;
    private AutocompleteResult teacher;
    private String groups;
    
    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    public AutocompleteResult getSubject() {
        return subject;
    }
    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }
    public String getProportion() {
        return proportion;
    }
    public void setProportion(String proportion) {
        this.proportion = proportion;
    }
    public AutocompleteResult getTeacher() {
        return teacher;
    }
    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public String getGroups() {
        return groups;
    }
    public void setGroups(String groups) {
        this.groups = groups;
    }
}
