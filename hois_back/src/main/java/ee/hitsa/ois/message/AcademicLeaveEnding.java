package ee.hitsa.ois.message;

import java.time.LocalDate;

import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.util.DateUtils;

public class AcademicLeaveEnding {

    private LocalDate endDate;

    public AcademicLeaveEnding() {
    }

    public AcademicLeaveEnding(DirectiveStudent directiveStudent) {
        this.endDate = DateUtils.periodEnd(directiveStudent);
    }

    public String getAkPuhkuseLoppemiseKuupaev() {
        return DateUtils.date(endDate);
    }
}
