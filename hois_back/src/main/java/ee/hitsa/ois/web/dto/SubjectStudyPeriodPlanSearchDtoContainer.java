package ee.hitsa.ois.web.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;

public class SubjectStudyPeriodPlanSearchDtoContainer {

    private AutocompleteResult subject;
    private Set<SubjectStudyPeriodPlanSearchDto> plans;
    private BigDecimal credits;

    public static SubjectStudyPeriodPlanSearchDtoContainer of (Subject subject, Long studyPeriodId) {
        SubjectStudyPeriodPlanSearchDtoContainer container = 
                new SubjectStudyPeriodPlanSearchDtoContainer();
        container.setSubject(AutocompleteResult.of(subject, false));
        container.setCredits(subject.getCredits());
        container.setPlans(StreamUtil.toMappedSet
              (SubjectStudyPeriodPlanSearchDto::of, 
                      subject.getSubjectStudyPeriodPlans().stream().filter(p ->
                  EntityUtil.getId(p.getStudyPeriod()).equals(studyPeriodId)
              ).collect(Collectors.toSet())));
        return container;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public Set<SubjectStudyPeriodPlanSearchDto> getPlans() {
        return plans;
    }

    public void setPlans(Set<SubjectStudyPeriodPlanSearchDto> plans) {
        this.plans = plans;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }
}
