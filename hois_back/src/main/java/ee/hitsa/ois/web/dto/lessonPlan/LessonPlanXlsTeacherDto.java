package ee.hitsa.ois.web.dto.lessonPlan;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonPlanXlsTeacherDto extends TeacherLoadSummaryDto {
    
    private Long lessonPlanId;
    private AutocompleteResult journal;
    private AutocompleteResult department;
    private String merCode;
    private String proportion;
    private AutocompleteResult teacher;
    private AutocompleteResult module;
    private AutocompleteResult studentGroup;
    private AutocompleteResult curriculum;
    
    public Long getLessonPlanId() {
        return lessonPlanId;
    }
    public void setLessonPlanId(Long lessonPlanId) {
        this.lessonPlanId = lessonPlanId;
    }
    public AutocompleteResult getJournal() {
        return journal;
    }
    public void setJournal(AutocompleteResult journal) {
        this.journal = journal;
    }
    public AutocompleteResult getDepartment() {
        return department;
    }
    public void setDepartment(AutocompleteResult department) {
        this.department = department;
    }
    public String getMerCode() {
        return merCode;
    }
    public void setMerCode(String merCode) {
        this.merCode = merCode;
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
    public AutocompleteResult getModule() {
        return module;
    }
    public void setModule(AutocompleteResult module) {
        this.module = module;
    }
    public AutocompleteResult getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(AutocompleteResult studentGroup) {
        this.studentGroup = studentGroup;
    }
    public AutocompleteResult getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }
}
