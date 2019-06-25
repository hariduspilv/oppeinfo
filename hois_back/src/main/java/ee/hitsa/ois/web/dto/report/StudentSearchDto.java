package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.util.CurriculumUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.StudentOccupationCertificateDto;

public class StudentSearchDto {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final String idcode;
    private final LocalDate studyStart;
    private final String studyLevel;
    private final AutocompleteResult curriculumVersion;
    private final String merCode;
    private final String studentGroup;
    private final String studyLoad;
    private final String studyForm;
    private final String status;
    private final String fin;
    private final String finSpecific;
    private final String language;
    private final String email;
    private final BigDecimal credits;
    private final String dormitory;
    private List<StudentOccupationCertificateDto> occupationCertificates;

    public StudentSearchDto(Object record) {
        id = resultAsLong(record, 0);
        firstName = resultAsString(record, 1);
        lastName = resultAsString(record, 2);
        fullName = PersonUtil.fullname(firstName, lastName);
        idcode = resultAsString(record, 3);
        studyStart = resultAsLocalDate(record, 4);
        studyLevel = resultAsString(record, 5);
        String code = resultAsString(record, 6);
        curriculumVersion = new AutocompleteResult(null, CurriculumUtil.versionName(code, resultAsString(record, 7)), CurriculumUtil.versionName(code, resultAsString(record, 8)));
        merCode = resultAsString(record, 9);
        studentGroup = resultAsString(record, 10);
        studyLoad = resultAsString(record, 11);
        studyForm = resultAsString(record, 12);
        status = resultAsString(record, 13);
        fin = resultAsString(record, 14);
        finSpecific = resultAsString(record, 15);
        language = resultAsString(record, 16);
        email = resultAsString(record, 17);
        credits = resultAsDecimal(record, 18);
        dormitory = resultAsString(record, 19);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIdcode() {
        return idcode;
    }

    public LocalDate getStudyStart() {
        return studyStart;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public AutocompleteResult getCurriculumVersion() {
        return curriculumVersion;
    }

    public String getMerCode() {
        return merCode;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public String getStudyLoad() {
        return studyLoad;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public String getStatus() {
        return status;
    }

    public String getFin() {
        return fin;
    }

    public String getFinSpecific() {
        return finSpecific;
    }

    public String getLanguage() {
        return language;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getCredits() {
        return credits;
    }

    public String getDormitory() {
        return dormitory;
    }

    public List<StudentOccupationCertificateDto> getOccupationCertificates() {
        return occupationCertificates;
    }

    public void setOccupationCertificates(List<StudentOccupationCertificateDto> occupationCertificates) {
        this.occupationCertificates = occupationCertificates;
    }
}
