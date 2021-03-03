package ee.hitsa.ois.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

public class StateCurriculumSubjectReport {
    // EHIS_AINE
    private String subject;
    // syllabus url
    private String riigiteatajaUrl;
    private Boolean isRequired;
    private Long coursesOrWeeks;
    private List<StateCurriculumCompetenceReport> competences = new ArrayList<>();

    public StateCurriculumSubjectReport(StateCurriculumModule module) {
        this.subject = EntityUtil.getCode(module.getModule());
        this.riigiteatajaUrl = module.getRiigiteatajaUrl();
        this.isRequired = module.getIsAdditional();
        this.coursesOrWeeks = module.getCoursesOrWeeks();
        this.competences = StreamUtil.toMappedList(p -> new StateCurriculumCompetenceReport(p), module.getModuleCompetences());
        Collections.sort(this.competences, Comparator.comparing(StateCurriculumCompetenceReport::getCompetence)
                .thenComparing(Comparator.comparing(StateCurriculumCompetenceReport::getDescription, Comparator.nullsLast(String::compareToIgnoreCase))));
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRiigiteatajaUrl() {
        return riigiteatajaUrl;
    }

    public void setRiigiteatajaUrl(String riigiteatajaUrl) {
        this.riigiteatajaUrl = riigiteatajaUrl;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Long getCoursesOrWeeks() {
        return coursesOrWeeks;
    }

    public void setCoursesOrWeeks(Long coursesOrWeeks) {
        this.coursesOrWeeks = coursesOrWeeks;
    }

    public List<StateCurriculumCompetenceReport> getCompetences() {
        return competences;
    }

    public void setCompetences(List<StateCurriculumCompetenceReport> competences) {
        this.competences = competences;
    }

}
