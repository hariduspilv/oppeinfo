package ee.hitsa.ois.web.commandobject.subject.studyperiod;

import java.util.Set;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.web.dto.SubjectStudyPeriodPlanDto;

public class SubjectStudyPeriodPlanUniqueCommand {
    
    private Long id;
    @NotNull
    private Long subject;
    private Set<String> studyForms;
    private Set<Long> curriculums;

    public static SubjectStudyPeriodPlanUniqueCommand of(SubjectStudyPeriodPlanDto dto) {
        SubjectStudyPeriodPlanUniqueCommand command = new SubjectStudyPeriodPlanUniqueCommand();
        command.setId(dto.getId());
        command.setSubject(dto.getSubject());
        command.setStudyForms(dto.getStudyForms());
        command.setCurriculums(dto.getCurriculums());
        return command;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSubject() {
        return subject;
    }
    public void setSubject(Long subject) {
        this.subject = subject;
    }
    public Set<String> getStudyForms() {
        return studyForms;
    }
    public void setStudyForms(Set<String> studyForms) {
        this.studyForms = studyForms;
    }
    public Set<Long> getCurriculums() {
        return curriculums;
    }
    public void setCurriculums(Set<Long> curriculums) {
        this.curriculums = curriculums;
    }
}
