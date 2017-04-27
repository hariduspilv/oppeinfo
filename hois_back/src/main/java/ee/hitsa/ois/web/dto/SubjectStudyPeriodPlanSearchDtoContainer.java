package ee.hitsa.ois.web.dto;

import java.util.Set;

import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.util.StreamUtil;

public class SubjectStudyPeriodPlanSearchDtoContainer {

    private AutocompleteResult subject;
    private Set<SubjectStudyPeriodPlanSearchDto> plans;
    // TODO: filter plans
    public static SubjectStudyPeriodPlanSearchDtoContainer of (Subject subject) {
        SubjectStudyPeriodPlanSearchDtoContainer container = 
                new SubjectStudyPeriodPlanSearchDtoContainer();
        container.setSubject(AutocompleteResult.of(subject));
        container.setPlans(StreamUtil.toMappedSet
              (SubjectStudyPeriodPlanSearchDto::of, subject.getSubjectStudyPeriodPlans()));
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
}
