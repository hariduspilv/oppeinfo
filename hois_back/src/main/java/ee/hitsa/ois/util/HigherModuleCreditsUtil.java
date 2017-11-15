package ee.hitsa.ois.util;

import java.math.BigDecimal;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionHigherModule;

public abstract class HigherModuleCreditsUtil {
    
    /**
     * set compulsory and total credits
     */
    public static void setCreduts(CurriculumVersionHigherModule module) {
        module.setCompulsoryStudyCredits(getCompulsoryCredits(module));
        module.setTotalCredits(getTotalCredits(module));
    }

    private static BigDecimal getCompulsoryCredits(CurriculumVersionHigherModule module) {

        return module.getSubjects().stream()
                .filter(subject -> Boolean.FALSE.equals(subject.getOptional()))
                .map(subject -> subject.getSubject().getCredits())
                .reduce(BigDecimal.ZERO, (credits, sum) -> credits.add(sum));
    }

    private static BigDecimal getTotalCredits(CurriculumVersionHigherModule module) {
        return module.getCompulsoryStudyCredits().add(module.getOptionalStudyCredits());
    }

}
