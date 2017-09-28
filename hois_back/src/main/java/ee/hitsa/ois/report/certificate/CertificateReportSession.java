package ee.hitsa.ois.report.certificate;

import ee.hitsa.ois.domain.StudyPeriodEvent;
import ee.hitsa.ois.util.DateUtils;

public class CertificateReportSession {
    
    private String start;
    private String end;
    
    public static CertificateReportSession of(StudyPeriodEvent event) {
        CertificateReportSession session = new CertificateReportSession();
        session.setStart(DateUtils.date(event.getStart().toLocalDate()));
        session.setEnd(DateUtils.date(event.getEnd().toLocalDate()));
        return session;
    }
    
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
}
