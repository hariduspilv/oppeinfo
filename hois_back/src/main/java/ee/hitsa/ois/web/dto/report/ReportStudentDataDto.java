package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.report.StudentDataCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ReportStudentDataDto {
    private Integer nr;
    private Long studentId;
    
    private String firstname;
    private String lastname;
    private String fullname;
    private String sex;
    private String idcode;
    private String bankaccount;
    private LocalDate birthdate;
    private String residenceCountry;
    private String citizenship;
    
    private Boolean guestStudent;
    private Boolean foreignStudent;
    private Boolean cumLaude;
    private LocalDate immatDate;
    private LocalDate finishedDate;
    private String directiveTypes;
    private LocalDate directiveConfirmDate;
    private String directiveReasons;
    private AutocompleteResult studentGroups;
    private String studentStatuses;
    private String studyForm;
    private String studyLoad;
    private String schoolDepartment;
    private AutocompleteResult curriculum;
    private String ehisCode;
    private String studyLevel;
    private AutocompleteResult speciality;
    private Long studyYearNumber;
    private String fin;
    private String language;
    private BigDecimal curriculumPercentage;
    private String address;
    private String phone;
    private String officialEmail;
    private String personalEmail;
    
    private BigDecimal eap;
    private BigDecimal eapSum;
    private BigDecimal weightedAverageSum;
    private BigDecimal weightedAverage;
    private BigDecimal averageSum;
    private BigDecimal average;
    private Long debt;
    private Long debtSum;
    private Long debtPointsSum;
    private Long debtPoints;
    private Long declaredEap;
    private String activeResult;
    private AutocompleteResult activeResultSubject;
    private AutocompleteResult declaredSubject;
    private LocalDate declarationConfirmation;
    private String previousSchoolName;
    private Long completedSchoolYear;
    private String exmatReason;
    private String akadReason;
    private String stiptoetlReason;
    
    public ReportStudentDataDto(Object r, StudentDataCommand criteria, Integer order) {
        this.nr = order;
        this.studentId = resultAsLong(r, 0);
        this.firstname = resultAsString(r, 1);
        this.lastname = resultAsString(r, 2);
        this.guestStudent = resultAsBoolean(r, 9);
        if (Boolean.TRUE.equals(criteria.getFullnameShow())) {
            if (Boolean.TRUE.equals(criteria.getFullname())) {
                this.fullname = this.lastname + " " + this.firstname + (Boolean.TRUE.equals(this.guestStudent) ? " (KY)" : "");
            } else {
                this.fullname = this.firstname + " " + this.lastname + (Boolean.TRUE.equals(this.guestStudent) ? " (KY)" : "");
            }
        }
        this.sex = resultAsString(r, 3);
        this.idcode = resultAsString(r, 4);
        this.bankaccount = resultAsString(r, 5);
        this.birthdate = JpaQueryUtil.resultAsLocalDate(r, 6);
        this.residenceCountry = resultAsString(r, 7);
        this.citizenship = resultAsString(r, 8);
        this.foreignStudent = resultAsBoolean(r, 10);
        this.cumLaude = resultAsBoolean(r, 11);
        this.immatDate = resultAsLocalDate(r, 12);
        this.finishedDate = resultAsLocalDate(r, 13);
        this.directiveTypes = resultAsString(r, 14);
        this.directiveConfirmDate = resultAsLocalDate(r, 15);
        this.directiveReasons = resultAsString(r, 16);
        if (directiveReasons != null) {
            if (directiveReasons.startsWith("EKSMAT_POHJUS")) {
                exmatReason = directiveReasons;
            } else if (directiveReasons.startsWith("AKADPUHKUS_POHJUS")) {
                akadReason = directiveReasons;
            } else if (directiveReasons.startsWith("KASKKIRI_STIPTOETL_POHJUS")) {
                stiptoetlReason = directiveReasons;
            }
        }
        this.directiveReasons = resultAsString(r, 16);
        this.studentGroups = new AutocompleteResult(resultAsLong(r, 17), resultAsString(r, 18), resultAsString(r, 18));
        this.studentStatuses = resultAsString(r, 19);
        this.studyForm = resultAsString(r, 20);
        this.studyLoad = resultAsString(r, 21);
        this.schoolDepartment = resultAsString(r, 22);
        this.curriculum = new AutocompleteResult(resultAsLong(r, 23), resultAsString(r, 24), resultAsString(r, 25));
        this.ehisCode = resultAsString(r, 26);
        this.studyLevel = resultAsString(r, 27);
        this.speciality = new AutocompleteResult(null, resultAsString(r, 28), resultAsString(r, 29));
        this.studyYearNumber = resultAsLong(r, 30);
        this.fin = resultAsString(r, 31);
        this.language = resultAsString(r, 32);
        this.curriculumPercentage = resultAsDecimal(r, 33);
        this.address = resultAsString(r, 34);
        this.phone = resultAsString(r, 35);
        this.officialEmail = resultAsString(r, 36);
        this.personalEmail = resultAsString(r, 37);
        this.eap = resultAsDecimal(r, 38);
        this.weightedAverageSum = resultAsDecimal(r, 39);
        this.eapSum = resultAsDecimal(r, 40);
        this.weightedAverage = resultAsDecimal(r, 41);
        this.averageSum = resultAsDecimal(r, 42);
        this.average = resultAsDecimal(r, 43);
        this.debtSum = resultAsLong(r, 44);
        this.debt = resultAsLong(r, 45);
        this.debtPointsSum = resultAsLong(r, 46);
        this.debtPoints = resultAsLong(r, 47);
        this.declaredEap = resultAsLong(r, 48);
        this.activeResult = resultAsString(r, 49);
        if (resultAsLong(r, 50) != null) {
            this.activeResultSubject = new AutocompleteResult(null, resultAsString(r, 51), resultAsString(r, 52));
        }
        if (resultAsLong(r, 53) != null) {
            this.declaredSubject = new AutocompleteResult(null, resultAsString(r, 54), resultAsString(r, 55));
        }
        this.declarationConfirmation = resultAsLocalDate(r, 56);
        this.previousSchoolName = resultAsString(r, 57);
        this.completedSchoolYear = resultAsLong(r, 58);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getResidenceCountry() {
        return residenceCountry;
    }

    public void setResidenceCountry(String residenceCountry) {
        this.residenceCountry = residenceCountry;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Boolean getGuestStudent() {
        return guestStudent;
    }

    public void setGuestStudent(Boolean guestStudent) {
        this.guestStudent = guestStudent;
    }

    public Boolean getForeignStudent() {
        return foreignStudent;
    }

    public void setForeignStudent(Boolean foreignStudent) {
        this.foreignStudent = foreignStudent;
    }

    public Boolean getCumLaude() {
        return cumLaude;
    }

    public void setCumLaude(Boolean cumLaude) {
        this.cumLaude = cumLaude;
    }

    public LocalDate getImmatDate() {
        return immatDate;
    }

    public void setImmatDate(LocalDate immatDate) {
        this.immatDate = immatDate;
    }

    public LocalDate getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(LocalDate finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getDirectiveTypes() {
        return directiveTypes;
    }

    public void setDirectiveTypes(String directiveTypes) {
        this.directiveTypes = directiveTypes;
    }

    public LocalDate getDirectiveConfirmDate() {
        return directiveConfirmDate;
    }

    public void setDirectiveConfirmDate(LocalDate directiveConfirmDate) {
        this.directiveConfirmDate = directiveConfirmDate;
    }

    public String getDirectiveReasons() {
        return directiveReasons;
    }

    public void setDirectiveReasons(String directiveReasons) {
        this.directiveReasons = directiveReasons;
    }

    public AutocompleteResult getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(AutocompleteResult studentGroups) {
        this.studentGroups = studentGroups;
    }

    public String getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm;
    }

    public String getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(String studyLoad) {
        this.studyLoad = studyLoad;
    }

    public String getSchoolDepartment() {
        return schoolDepartment;
    }

    public void setSchoolDepartment(String schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }

    public AutocompleteResult getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(AutocompleteResult curriculum) {
        this.curriculum = curriculum;
    }

    public String getEhisCode() {
        return ehisCode;
    }

    public void setEhisCode(String ehisCode) {
        this.ehisCode = ehisCode;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public Long getStudyYearNumber() {
        return studyYearNumber;
    }

    public void setStudyYearNumber(Long studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BigDecimal getCurriculumPercentage() {
        return curriculumPercentage;
    }

    public void setCurriculumPercentage(BigDecimal curriculumPercentage) {
        this.curriculumPercentage = curriculumPercentage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public BigDecimal getEap() {
        return eap;
    }

    public void setEap(BigDecimal eap) {
        this.eap = eap;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public BigDecimal getEapSum() {
        return eapSum;
    }

    public void setEapSum(BigDecimal eapSum) {
        this.eapSum = eapSum;
    }

    public BigDecimal getAverageSum() {
        return averageSum;
    }

    public void setAverageSum(BigDecimal averageSum) {
        this.averageSum = averageSum;
    }

    public BigDecimal getWeightedAverageSum() {
        return weightedAverageSum;
    }

    public void setWeightedAverageSum(BigDecimal weightedAverageSum) {
        this.weightedAverageSum = weightedAverageSum;
    }

    public BigDecimal getWeightedAverage() {
        return weightedAverage;
    }

    public void setWeightedAverage(BigDecimal weightedAverage) {
        this.weightedAverage = weightedAverage;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }

    public Long getDebtSum() {
        return debtSum;
    }

    public void setDebtSum(Long debtSum) {
        this.debtSum = debtSum;
    }

    public Long getDebtPointsSum() {
        return debtPointsSum;
    }

    public void setDebtPointsSum(Long debtPointsSum) {
        this.debtPointsSum = debtPointsSum;
    }

    public Long getDebtPoints() {
        return debtPoints;
    }

    public void setDebtPoints(Long debtPoints) {
        this.debtPoints = debtPoints;
    }

    public void setActiveResult(String activeResult) {
        this.activeResult = activeResult;
    }

    public String getActiveResult() {
        return activeResult;
    }

    public AutocompleteResult getActiveResultSubject() {
        return activeResultSubject;
    }

    public void setActiveResultSubject(AutocompleteResult activeResultSubject) {
        this.activeResultSubject = activeResultSubject;
    }

    public AutocompleteResult getDeclaredSubject() {
        return declaredSubject;
    }

    public void setDeclaredSubject(AutocompleteResult declaredSubject) {
        this.declaredSubject = declaredSubject;
    }

    public LocalDate getDeclarationConfirmation() {
        return declarationConfirmation;
    }

    public void setDeclarationConfirmation(LocalDate declarationConfirmation) {
        this.declarationConfirmation = declarationConfirmation;
    }

    public String getPreviousSchoolName() {
        return previousSchoolName;
    }

    public void setPreviousSchoolName(String previousSchoolName) {
        this.previousSchoolName = previousSchoolName;
    }

    public String getExmatReason() {
        return exmatReason;
    }

    public void setExmatReason(String exmatReason) {
        this.exmatReason = exmatReason;
    }

    public String getAkadReason() {
        return akadReason;
    }

    public void setAkadReason(String akadReason) {
        this.akadReason = akadReason;
    }

    public String getStiptoetlReason() {
        return stiptoetlReason;
    }

    public void setStiptoetlReason(String stiptoetlReason) {
        this.stiptoetlReason = stiptoetlReason;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getStudentStatuses() {
        return studentStatuses;
    }

    public void setStudentStatuses(String studentStatuses) {
        this.studentStatuses = studentStatuses;
    }

    public Long getDeclaredEap() {
        return declaredEap;
    }

    public void setDeclaredEap(Long declaredEap) {
        this.declaredEap = declaredEap;
    }

    public Long getCompletedSchoolYear() {
        return completedSchoolYear;
    }

    public void setCompletedSchoolYear(Long completedSchoolYear) {
        this.completedSchoolYear = completedSchoolYear;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public AutocompleteResult getSpeciality() {
        return speciality;
    }

    public void setSpeciality(AutocompleteResult speciality) {
        this.speciality = speciality;
    }
}
