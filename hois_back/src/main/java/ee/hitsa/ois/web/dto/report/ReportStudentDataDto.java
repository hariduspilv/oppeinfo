package ee.hitsa.ois.web.dto.report;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsBoolean;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLocalDate;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsDecimal;

import java.math.BigDecimal;
import java.time.LocalDate;

import ee.hitsa.ois.enums.DirectiveType;
import ee.hitsa.ois.enums.StudentType;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaQueryUtil;
import ee.hitsa.ois.web.commandobject.report.StudentDataCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ReportStudentDataDto {
    private Integer nr;
    private Long studentId;
    /**
     * Person data
     */
    private String firstname;
    private String lastname;
    private String fullname;
    private String sex;
    private String idcode;
    private String bankaccount;
    private LocalDate birthdate;
    private String residenceCountry;
    private String citizenship;
    private Boolean age;
    /**
     * Study data
     */
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
    private LocalDate nominalStudyEnd;
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
    private Boolean specialNeed;
    private String specialNeedCode;
    private String studyCompany;
    private LocalDate directiveStart;
    private LocalDate directiveEnd;
    /**
     * Contact data
     */
    private String address;
    private String phone;
    private String officialEmail;
    private String personalEmail;
    private String otherContact;
    /**
     * Representative data
     */
    private String representativeName;
    private String representativeIdcode;
    private String representativeRelation;
    private String representativePhone;
    private String representativeEmail;
    private String representativeOtherContact;
    private Boolean representativeVisible;
    /**
     * Statistics data
     */
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
    private BigDecimal apelEap;
    private String activeResult;
    private AutocompleteResult activeResultSubject;
    private AutocompleteResult declaredSubject;
    private LocalDate declarationConfirmation;
    private String previousSchoolName;
    private Long completedSchoolYear;
    private String exmatReason;
    private String akadReason;
    private String stiptoetlReason;
    private String foreignLanguage;
    private String previousStudyLevel;
    private String dormitory;
    private Long regNr;
    
    public ReportStudentDataDto(Object r, StudentDataCommand criteria, Integer order) {
        this.nr = order;
        this.studentId = resultAsLong(r, 0);
        this.firstname = resultAsString(r, 1);
        this.lastname = resultAsString(r, 2);
        this.guestStudent = Boolean.valueOf(StudentType.OPPUR_K.name().equals(resultAsString(r, 10)));
        boolean externalStudent = StudentType.OPPUR_E.name().equals(resultAsString(r, 10));
        if (Boolean.TRUE.equals(criteria.getFullnameShow())) {
            if (Boolean.TRUE.equals(criteria.getFullname())) {
                this.fullname = this.lastname + " " + this.firstname 
                        + (Boolean.TRUE.equals(this.guestStudent) ? " (KY)" : "")
                        + (externalStudent ? " (E)" : "");
            } else {
                this.fullname = this.firstname + " " + this.lastname 
                        + (Boolean.TRUE.equals(this.guestStudent) ? " (KY)" : "")
                        + (externalStudent ? " (E)" : "");
            }
        }
        this.sex = resultAsString(r, 3);
        this.idcode = resultAsString(r, 4);
        this.bankaccount = resultAsString(r, 5);
        this.birthdate = JpaQueryUtil.resultAsLocalDate(r, 6);
        this.residenceCountry = resultAsString(r, 7);
        this.citizenship = resultAsString(r, 8);
        this.age = resultAsBoolean(r, 9);
        
        this.foreignStudent = resultAsBoolean(r, 11);
        this.cumLaude = resultAsBoolean(r, 12);
        this.immatDate = resultAsLocalDate(r, 13);
        this.finishedDate = resultAsLocalDate(r, 14);
        this.directiveTypes = resultAsString(r, 15);
        this.directiveConfirmDate = resultAsLocalDate(r, 16);
        this.directiveReasons = resultAsString(r, 17);
        if (directiveReasons != null) {
            if (directiveReasons.startsWith("EKSMAT_POHJUS")) {
                exmatReason = directiveReasons;
            } else if (directiveReasons.startsWith("AKADPUHKUS_POHJUS")) {
                akadReason = directiveReasons;
            } else if (directiveReasons.startsWith("KASKKIRI_STIPTOETL_POHJUS")) {
                stiptoetlReason = directiveReasons;
            }
        }
        this.studentGroups = new AutocompleteResult(resultAsLong(r, 18), resultAsString(r, 19), resultAsString(r, 19));
        this.studentStatuses = resultAsString(r, 20);
        this.regNr = resultAsLong(r, 21);
        this.nominalStudyEnd = resultAsLocalDate(r, 22);
        this.studyForm = resultAsString(r, 23);
        this.studyLoad = resultAsString(r, 24);
        this.schoolDepartment = resultAsString(r, 25);
        this.curriculum = new AutocompleteResult(resultAsLong(r, 26), resultAsString(r, 27), resultAsString(r, 28));
        this.ehisCode = resultAsString(r, 29);
        this.studyLevel = resultAsString(r, 30);
        this.speciality = new AutocompleteResult(null, resultAsString(r, 31), resultAsString(r, 32));
        this.studyYearNumber = resultAsLong(r, 33);
        this.fin = resultAsString(r, 34);
        this.language = resultAsString(r, 35);
        this.foreignLanguage = resultAsString(r, 36);
        this.curriculumPercentage = resultAsDecimal(r, 37);
        this.specialNeed = resultAsBoolean(r, 38);
        this.specialNeedCode = resultAsString(r, 39);
        this.studyCompany = resultAsString(r, 40);
        if (EnumUtil.toNameList(DirectiveType.KASKKIRI_AKAD, DirectiveType.KASKKIRI_VALIS, DirectiveType.KASKKIRI_KYLALIS).contains(this.directiveTypes)) {
            this.directiveStart = resultAsLocalDate(r, 41);
            this.directiveEnd = resultAsLocalDate(r, 42);
        }
        
        this.address = resultAsString(r, 43);
        this.phone = resultAsString(r, 44);
        this.officialEmail = resultAsString(r, 45);
        this.personalEmail = resultAsString(r, 46);
        this.otherContact = resultAsString(r, 47);
        
        this.representativeName = resultAsString(r, 48);
        this.representativeIdcode = resultAsString(r, 49);
        this.representativeRelation = resultAsString(r, 50);
        this.representativePhone = resultAsString(r, 51);
        this.representativeEmail = resultAsString(r, 52);
        this.representativeOtherContact = resultAsString(r, 53);
        this.representativeVisible = resultAsBoolean(r, 54);
        
        this.eap = resultAsDecimal(r, 55);
        this.weightedAverageSum = resultAsDecimal(r, 56);
        this.eapSum = resultAsDecimal(r, 57);
        this.weightedAverage = resultAsDecimal(r, 58);
        this.averageSum = resultAsDecimal(r, 59);
        this.average = resultAsDecimal(r, 60);
        this.debtSum = resultAsLong(r, 61);
        this.debt = resultAsLong(r, 62);
        this.debtPointsSum = resultAsLong(r, 63);
        this.debtPoints = resultAsLong(r, 64);
        this.declaredEap = resultAsLong(r, 65);
        this.apelEap = resultAsDecimal(r, 66);
        this.activeResult = resultAsString(r, 67);
        if (resultAsLong(r, 68) != null) {
            this.activeResultSubject = new AutocompleteResult(null, resultAsString(r, 69), resultAsString(r, 70));
        }
        if (resultAsLong(r, 71) != null) {
            this.declaredSubject = new AutocompleteResult(null, resultAsString(r, 72), resultAsString(r, 73));
        }
        this.declarationConfirmation = resultAsLocalDate(r, 74);
        this.previousSchoolName = resultAsString(r, 75);
        this.completedSchoolYear = resultAsLong(r, 76);
        this.previousStudyLevel = resultAsString(r, 77);
        this.dormitory = resultAsString(r, 78);
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

    public String getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(String foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public LocalDate getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public void setNominalStudyEnd(LocalDate nominalStudyEnd) {
        this.nominalStudyEnd = nominalStudyEnd;
    }

    public String getPreviousStudyLevel() {
        return previousStudyLevel;
    }

    public void setPreviousStudyLevel(String previousStudyLevel) {
        this.previousStudyLevel = previousStudyLevel;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public Long getRegNr() {
        return regNr;
    }

    public void setRegNr(Long regNr) {
        this.regNr = regNr;
    }

    public String getOtherContact() {
        return otherContact;
    }

    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact;
    }

    public String getSpecialNeedCode() {
        return specialNeedCode;
    }

    public void setSpecialNeedCode(String specialNeedCode) {
        this.specialNeedCode = specialNeedCode;
    }

    public Boolean getSpecialNeed() {
        return specialNeed;
    }

    public void setSpecialNeed(Boolean specialNeed) {
        this.specialNeed = specialNeed;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getRepresentativeIdcode() {
        return representativeIdcode;
    }

    public void setRepresentativeIdcode(String representativeIdcode) {
        this.representativeIdcode = representativeIdcode;
    }

    public String getRepresentativeRelation() {
        return representativeRelation;
    }

    public void setRepresentativeRelation(String representativeRelation) {
        this.representativeRelation = representativeRelation;
    }

    public String getRepresentativePhone() {
        return representativePhone;
    }

    public void setRepresentativePhone(String representativePhone) {
        this.representativePhone = representativePhone;
    }

    public String getRepresentativeOtherContact() {
        return representativeOtherContact;
    }

    public void setRepresentativeOtherContact(String representativeOtherContact) {
        this.representativeOtherContact = representativeOtherContact;
    }

    public Boolean getRepresentativeVisible() {
        return representativeVisible;
    }

    public void setRepresentativeVisible(Boolean representativeVisible) {
        this.representativeVisible = representativeVisible;
    }

    public BigDecimal getApelEap() {
        return apelEap;
    }

    public void setApelEap(BigDecimal apelEap) {
        this.apelEap = apelEap;
    }

    public Boolean getAge() {
        return age;
    }

    public void setAge(Boolean age) {
        this.age = age;
    }

    public String getStudyCompany() {
        return studyCompany;
    }

    public void setStudyCompany(String studyCompany) {
        this.studyCompany = studyCompany;
    }

    public LocalDate getDirectiveStart() {
        return directiveStart;
    }

    public void setDirectiveStart(LocalDate directiveStart) {
        this.directiveStart = directiveStart;
    }

    public LocalDate getDirectiveEnd() {
        return directiveEnd;
    }

    public void setDirectiveEnd(LocalDate directiveEnd) {
        this.directiveEnd = directiveEnd;
    }

    public String getRepresentativeEmail() {
        return representativeEmail;
    }

    public void setRepresentativeEmail(String representativeEmail) {
        this.representativeEmail = representativeEmail;
    }
}
