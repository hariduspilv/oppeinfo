package ee.hitsa.ois.report.certificate;

import java.time.LocalDate;

public class CertificateEventAcademicLeave extends CertificateEvent {
    
    private final String name;

    public CertificateEventAcademicLeave(String name, LocalDate start, LocalDate end) {
        super(start, end);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
