package ee.hitsa.ois.repository.model;

import java.math.BigDecimal;

public interface ITeacherLoadReportResult {

    Long getItemId();

    String getNameEt();

    String getNameEn();

    String getGroups();

    String getSubgroups();

    Long getStudyPeriodId();

    String getSubjectCode();

    BigDecimal getSubjectCredits();

    String getAssessmentCode();

    BigDecimal getHoursContact();
}
