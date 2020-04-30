package ee.hitsa.ois.report.certificate;

import ee.hitsa.ois.domain.StudyPeriodEvent;

public class CertificateReportSession extends CertificateEvent {

    public CertificateReportSession(StudyPeriodEvent event) {
        super(event.getStart().toLocalDate(), event.getEnd().toLocalDate());
    }
}
