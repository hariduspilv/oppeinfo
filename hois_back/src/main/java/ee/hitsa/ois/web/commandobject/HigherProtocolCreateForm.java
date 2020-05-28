package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.validation.Conditional;
import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

@Conditional(selected = "subjectStudyPeriod", values = {"null"}, required = {"curriculumVersionHmodule"})
@Conditional(selected = "subjectStudyPeriod", values = {"null"}, required = {"teacher"})
@Conditional(selected = "curriculumVersionHmodule", values = {"null"}, required = {"subjectStudyPeriod"})
public class HigherProtocolCreateForm {

    private Long subjectStudyPeriod;
    private Long curriculumVersionHmodule;
    private Long teacher;
    @NotNull
    @ClassifierRestriction(MainClassCode.PROTOKOLLI_LIIK)
    private String protocolType;
    @NotEmpty
    private Set<Long> students;

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Long getCurriculumVersionHmodule() {
        return curriculumVersionHmodule;
    }

    public void setCurriculumVersionHmodule(Long curriculumVersionHmodule) {
        this.curriculumVersionHmodule = curriculumVersionHmodule;
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public Set<Long> getStudents() {
        return students;
    }

    public void setStudents(Set<Long> students) {
        this.students = students;
    }
}
