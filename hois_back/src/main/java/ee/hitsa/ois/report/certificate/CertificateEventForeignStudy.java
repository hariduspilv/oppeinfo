package ee.hitsa.ois.report.certificate;

import java.time.LocalDate;

public class CertificateEventForeignStudy extends CertificateEvent {

    private final String name;

    public CertificateEventForeignStudy(String name, LocalDate start, LocalDate end) {
        super(start, end);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
