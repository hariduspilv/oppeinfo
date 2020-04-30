package ee.hitsa.ois.report.certificate;

import java.time.LocalDate;

import ee.hitsa.ois.util.DateUtils;

public class CertificateEvent {

    private final String start;
    private final String end;

    public CertificateEvent(LocalDate start, LocalDate end) {
        this.start = DateUtils.date(start);
        this.end = end != null ? DateUtils.date(end) : null;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
