package ee.hitsa.ois.report.certificate;

import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.util.DateUtils;

public class CertificateReportSession {

    private final String start;
    private final String end;

    public CertificateReportSession(StudyPeriodEvent event) {
        start = DateUtils.date(event.getStart().toLocalDate());
        end = event.getEnd() != null ? DateUtils.date(event.getEnd().toLocalDate()) : null;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
