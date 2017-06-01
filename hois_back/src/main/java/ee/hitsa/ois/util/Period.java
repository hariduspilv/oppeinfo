package ee.hitsa.ois.util;

import java.time.LocalDate;

import ee.hitsa.ois.domain.StudyPeriod;

public interface Period {

    Boolean getIsPeriod();
    StudyPeriod getStudyPeriodStart();
    StudyPeriod getStudyPeriodEnd();
    LocalDate getStartDate();
    LocalDate getEndDate();
}
