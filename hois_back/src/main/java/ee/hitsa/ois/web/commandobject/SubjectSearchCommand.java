package ee.hitsa.ois.web.commandobject;

import java.math.BigDecimal;
import java.util.Collection;

public class SubjectSearchCommand extends SearchCommand {

    private Collection<String> languages;
    private Collection<Long> departments;
    private Collection<String> assessments;
    private Collection<Long> curricula;
    private Collection<Long> curriculaVersion;
    private Collection<String> status;
    private Collection<String> ehisSchools;
    private BigDecimal from;
    private BigDecimal thru;

    public Collection<String> getEhisSchools() {
        return ehisSchools;
    }

    public void setEhisSchools(Collection<String> ehisSchools) {
        this.ehisSchools = ehisSchools;
    }

    public Collection<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Collection<String> languages) {
        this.languages = languages;
    }

    public Collection<Long> getDepartments() {
        return departments;
    }

    public void setDepartments(Collection<Long> departments) {
        this.departments = departments;
    }

    public Collection<String> getAssessments() {
        return assessments;
    }

    public void setAssessments(Collection<String> assessments) {
        this.assessments = assessments;
    }

    public Collection<Long> getCurricula() {
        return curricula;
    }

    public void setCurricula(Collection<Long> curricula) {
        this.curricula = curricula;
    }

    public Collection<String> getStatus() {
        return status;
    }

    public void setStatus(Collection<String> status) {
        this.status = status;
    }

    public BigDecimal getFrom() {
        return from;
    }

    public void setFrom(BigDecimal from) {
        this.from = from;
    }

    public BigDecimal getThru() {
        return thru;
    }

    public void setThru(BigDecimal thru) {
        this.thru = thru;
    }

    public Collection<Long> getCurriculaVersion() {
        return curriculaVersion;
    }

    public void setCurriculaVersion(Collection<Long> curriculaVersion) {
        this.curriculaVersion = curriculaVersion;
    }
}
