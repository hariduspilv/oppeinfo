package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;

public class StudentOccupationCertificateDto {

    private final String certificateNr;
    private final String idcode;
    private final String firstname;
    private final String lastname;
    private final String type;
    private final AutocompleteResult occupation;
    private final String issuer;
    private final LocalDate issueDate;
    private final LocalDate validFrom;
    private final LocalDate validThru;

    public StudentOccupationCertificateDto(StudentOccupationCertificate certificate) {
        certificateNr = certificate.getCertificateNr();
        Person person = certificate.getStudent().getPerson();
        idcode = person.getIdcode();
        firstname = person.getFirstname();
        lastname = person.getLastname();
        Classifier occupationCl;
        if(certificate.getSpeciality() != null) {
            type = "occupationcertificate.type.spaciality";
            occupationCl = certificate.getSpeciality();
        } else if(certificate.getPartOccupation() != null) {
            type = "occupationcertificate.type.partoccupation";
            occupationCl = certificate.getPartOccupation();
        } else {
            type = "occupationcertificate.type.occupation";
            occupationCl = certificate.getOccupation();
        }
        occupation = occupationCl != null ? new AutocompleteResult(null, occupationCl) : null;
        issuer = certificate.getIssuer();
        issueDate = certificate.getIssueDate();
        validFrom = certificate.getValidFrom();
        validThru = certificate.getValidThru();
    }

    public String getCertificateNr() {
        return certificateNr;
    }

    public String getIdcode() {
        return idcode;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getType() {
        return type;
    }

    public AutocompleteResult getOccupation() {
        return occupation;
    }

    public String getIssuer() {
        return issuer;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }
}
