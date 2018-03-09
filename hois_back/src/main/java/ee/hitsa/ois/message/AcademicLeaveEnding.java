package ee.hitsa.ois.message;

import java.time.LocalDate;

import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.util.DateUtils;

public class AcademicLeaveEnding extends StudentMessage {

    private final LocalDate endDate;

    public AcademicLeaveEnding() {
        endDate = null;
    }

    public AcademicLeaveEnding(DirectiveStudent directiveStudent) {
        super(directiveStudent.getStudent());

        endDate = DateUtils.periodEnd(directiveStudent);
    }

    public String getAkPuhkuseLoppemiseKuupaev() {
        return DateUtils.date(endDate);
    }
}
