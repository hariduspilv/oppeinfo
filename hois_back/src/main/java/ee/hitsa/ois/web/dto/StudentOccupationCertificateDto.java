package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.student.StudentOccupationCertificate;
import ee.hitsa.ois.util.EntityUtil;

public class StudentOccupationCertificateDto {

    private final String certificateNr;
    private final String idcode;
    private final String firstname;
    private final String lastname;
    private final String type;
    private final String occupationCode;
    private final String partOccupationCode;
    private final String specialityCode;
    private final AutocompleteResult occupation;
    private final String issuer;
    private final LocalDate issueDate;
    private final LocalDate validFrom;
    private final LocalDate validThru;
    private final String language;
    private final String languageCode;

    public StudentOccupationCertificateDto(String certificateNr, String occupationCode, String partOccupationCode, String specialityCode) {
        this.certificateNr = certificateNr;
        this.occupationCode = occupationCode;
        this.partOccupationCode = partOccupationCode;
        this.specialityCode = specialityCode;

        idcode = null;
        firstname = null;
        lastname = null;
        type = null;
        occupation = null;
        issuer = null;
        issueDate = null;
        validFrom = null;
        validThru = null;
        language = null;
        languageCode = null;
    }

    public StudentOccupationCertificateDto(StudentOccupationCertificate certificate) {
        certificateNr = certificate.getCertificateNr();
        Person person = certificate.getStudent().getPerson();
        idcode = person.getIdcode();
        firstname = person.getFirstname();
        lastname = person.getLastname();
        occupationCode = EntityUtil.getNullableCode(certificate.getOccupation());
        partOccupationCode = EntityUtil.getNullableCode(certificate.getPartOccupation());
        specialityCode = EntityUtil.getNullableCode(certificate.getSpeciality());
        Classifier occupationCl;
        if(certificate.getSpeciality() != null) {
            type = "occupationcertificate.type.speciality";
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
        language = certificate.getLanguage();
        languageCode = EntityUtil.getNullableCode(certificate.getLanguageCode());
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

    public String getOccupationCode() {
        return occupationCode;
    }

    public String getPartOccupationCode() {
        return partOccupationCode;
    }

    public String getSpecialityCode() {
        return specialityCode;
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

    public String getLanguage() {
        return language;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getPartOccupationSpecialityCode() {
        return specialityCode != null ? specialityCode : partOccupationCode;
    }
}
