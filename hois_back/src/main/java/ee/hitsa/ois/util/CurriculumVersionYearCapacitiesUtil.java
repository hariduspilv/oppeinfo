package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleYearCapacity;

public abstract class CurriculumVersionYearCapacitiesUtil {

    public static BigDecimal calculate(Set<CurriculumVersionOccupationModule> modules, Short year) {
        BigDecimal sum = BigDecimal.ZERO;
        for (CurriculumVersionOccupationModule module : modules) {

            Optional<CurriculumVersionOccupationModuleYearCapacity> capacity = module.getYearCapacities().stream()
                    .filter(c -> year.equals(c.getStudyYearNumber())).findFirst();

            if (capacity.isPresent()) {
                sum = sum.add(capacity.get().getCredits());
            }
        }
        return sum;
    }
}
