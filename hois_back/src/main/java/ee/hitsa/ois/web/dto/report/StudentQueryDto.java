package ee.hitsa.ois.web.dto.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class StudentQueryDto extends SchoolQueryDto {
    private Boolean nrShow;
    // Person data
    
    private String firstname;
    private Boolean firstnameShow;
    
    private String lastname;
    private Boolean lastnameShow;
    
    private Boolean fullnameShow;
    private Boolean fullname;
    
    private String sex;
    private Boolean sexShow;
    
    private String idcode;
    private Boolean idcodeShow;
    
    private String bankaccount;
    private Boolean bankaccountShow;
    
    private LocalDate birthdateFrom;
    private Boolean birthdateShow;
    private LocalDate birthdateThru;
    
    private List<ClassifierResult> residenceCountry;
    private Boolean residenceCountryShow;
    
    private List<ClassifierResult> citizenship;
    private Boolean citizenshipShow;
    
    private Boolean age;
    private Boolean ageShow;
    
    // Study data
    private Boolean guestStudent;
    private Boolean guestStudentShow;
    
    private Boolean foreignStudent;
    private Boolean foreignStudentShow;
    
    private Boolean cumLaude;
    private Boolean cumLaudeShow;
    
    private LocalDate immatDateFrom;
    private Boolean immatDateShow;
    private LocalDate immatDateThru;
    
    private LocalDate finishedDateFrom;
    private Boolean finishedDate;
    private Boolean finishedDateShow;
    private LocalDate finishedDateThru;
    
    private List<String> directiveTypes;
    private Boolean directiveTypesShow;
    
    private List<String> directiveReasons;
    private Boolean directiveReasonsShow;
    
    private LocalDate directiveConfirmDateFrom;
    private Boolean directiveConfirmDateShow;
    private LocalDate directiveConfirmDateThru;
    
    private LocalDate akadDateFrom;
    private LocalDate akadDateThru;
    
    private LocalDate valisDateFrom;
    private LocalDate valisDateThru;
    
    private LocalDate guestDateFrom;
    private LocalDate guestDateThru;
    
    private List<AutocompleteResult> studentGroups;
    private Boolean studentGroupsShow;
    
    private List<String> studentStatuses;
    private Boolean studentStatusesShow;
    
    private String regNr;
    private Boolean regNrShow;
    
    private LocalDate nominalStudyEndFrom;
    private LocalDate nominalStudyEndThru;
    private Boolean nominalStudyEndShow;
    
    private List<String> studyForm;
    private Boolean studyFormShow;
    
    private List<String> studyLoad;
    private Boolean studyLoadShow;
    
    private List<AutocompleteResult> schoolDepartment;
    private Boolean schoolDepartmentShow;
    
    private List<AutocompleteResult> curriculum;
    private Boolean curriculumShow;
    
    private String ehisCode;
    private Boolean ehisCodeShow;
    
    private List<String> studyLevel;
    private Boolean studyLevelShow;
    
    private List<ClassifierResult> speciality;
    private List<AutocompleteResult> specialityHigher;
    private Boolean specialityShow;
    
    private Long studyYearNumber;
    private Boolean studyYearNumberShow;
    
    private List<String> fin;
    private Boolean finShow;
    
    private List<String> language;
    private Boolean languageShow;
    
    private Boolean curriculumPercentageShow;
    private BigDecimal curriculumPercentageFrom;
    private BigDecimal curriculumPercentageThru;
    
    private List<String> foreignLanguage;
    private Boolean foreignLanguageShow;
    
    private Boolean specialNeed;
    private Boolean specialNeedShow;
    
    private List<String> specialNeedCode;
    private Boolean specialNeedCodeShow;
    
    private String studyCompany;
    private Boolean studyCompanyShow;
    
    // Contact data
    private String address;
    private Boolean addressShow;
    
    private String phone;
    private Boolean phoneShow;
    
    private String officialEmail;
    private Boolean officialEmailShow;
    
    private String personalEmail;
    private Boolean personalEmailShow;
    
    private String otherContact;
    private Boolean otherContactShow;
    
    /**
     * Representative data
     */
    
    private String representativeName;
    private Boolean representativeNameShow;
    
    private String representativeIdcode;
    private Boolean representativeIdcodeShow;
    
    private List<String> representativeRelation;
    private Boolean representativeRelationShow;
    
    private String representativePhone;
    private Boolean representativePhoneShow;
    
    private String representativeEmail;
    private Boolean representativeEmailShow;
    
    private String representativeOtherContact;
    private Boolean representativeOtherContactShow;
    
    private Boolean representativeVisible;
    private Boolean representativeVisibleShow;
    
    private Boolean onlyWithRepresentative;
    
    // Statistics data
    private String eapSign;
    private Long eap;
    private Boolean eapShow;
    
    private String eapSumSign;
    private Long eapSum;
    private Boolean eapSumApel;
    private LocalDate eapSumFrom;
    private LocalDate eapSumThru;
    private Long eapSumPeriod;
    private Boolean eapSumShow;
    
    private String weightedAverageSumSign;
    private BigDecimal weightedAverageSum;
    private Boolean weightedAverageSumShow;
    
    private String weightedAverageSign;
    private BigDecimal weightedAverage;
    private LocalDate weightedAverageFrom;
    private LocalDate weightedAverageThru;
    private Long weightedAveragePeriod;
    private Boolean weightedAverageShow;
    
    private String averageSumSign;
    private BigDecimal averageSum;
    private Boolean averageSumShow;
    
    private String averageSign;
    private BigDecimal average;
    private LocalDate averageFrom;
    private LocalDate averageThru;
    private Long averagePeriod;
    private Boolean averageShow;
    
    private String debtSumSign;
    private BigDecimal debtSum;
    private Boolean debtSumShow;
    
    private Long debt;
    private String debtSign;
    private LocalDate debtFrom;
    private LocalDate debtThru;
    private Long debtPeriod;
    private Boolean debtShow;
    
    private String debtPointsSumSign;
    private BigDecimal debtPointsSum;
    private Boolean debtPointsSumShow;
    
    private BigDecimal debtPoints;
    private Boolean debtPointsShow;
    private String debtPointsSign;
    private LocalDate debtPointsFrom;
    private LocalDate debtPointsThru;
    private Long debtPointsPeriod;
    
    private String declaredEapSign;
    private BigDecimal declaredEap;
    private Boolean declaredEapShow;
    private LocalDate declaredEapFrom;
    private LocalDate declaredEapThru;
    private Long declaredEapPeriod;
    
    private String apelEapSign;
    private BigDecimal apelEap;
    private Boolean apelEapShow;
    
    //Other info
    private List<AutocompleteResult> activeResult;
    private Boolean activeResultShow;
    private Boolean activeResultPositive;
    
    private List<AutocompleteResult> declaredSubject;
    private Boolean declaredSubjectShow;
    private Boolean declaredSubjectRepetitive;
    
    private Boolean declarationConfirmationShow;
    private LocalDate declarationConfirmationFrom;
    private LocalDate declarationConfirmationThru;
    
    private Boolean previousSchoolNameShow;
    private String previousSchoolName;
    
    private Boolean completedSchoolYearShow;
    private String completedSchoolYearSign;
    private Long completedSchoolYear;
    
    private List<String> previousStudyLevel;
    private Boolean previousStudyLevelShow;
    
    private List<String> dormitory;
    private Boolean dormitoryShow;
    
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public Boolean getFirstnameShow() {
        return firstnameShow;
    }
    public void setFirstnameShow(Boolean firstnameShow) {
        this.firstnameShow = firstnameShow;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public Boolean getLastnameShow() {
        return lastnameShow;
    }
    public void setLastnameShow(Boolean lastnameShow) {
        this.lastnameShow = lastnameShow;
    }
    public Boolean getFullnameShow() {
        return fullnameShow;
    }
    public void setFullnameShow(Boolean fullnameShow) {
        this.fullnameShow = fullnameShow;
    }
    public Boolean getFullname() {
        return fullname;
    }
    public void setFullname(Boolean fullname) {
        this.fullname = fullname;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Boolean getSexShow() {
        return sexShow;
    }
    public void setSexShow(Boolean sexShow) {
        this.sexShow = sexShow;
    }
    public String getIdcode() {
        return idcode;
    }
    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }
    public Boolean getIdcodeShow() {
        return idcodeShow;
    }
    public void setIdcodeShow(Boolean idcodeShow) {
        this.idcodeShow = idcodeShow;
    }
    public Boolean getBankaccountShow() {
        return bankaccountShow;
    }
    public void setBankaccountShow(Boolean bankaccountShow) {
        this.bankaccountShow = bankaccountShow;
    }
    public LocalDate getBirthdateFrom() {
        return birthdateFrom;
    }
    public void setBirthdateFrom(LocalDate birthdateFrom) {
        this.birthdateFrom = birthdateFrom;
    }
    public Boolean getBirthdateShow() {
        return birthdateShow;
    }
    public void setBirthdateShow(Boolean birthdateShow) {
        this.birthdateShow = birthdateShow;
    }
    public LocalDate getBirthdateThru() {
        return birthdateThru;
    }
    public void setBirthdateThru(LocalDate birthdateThru) {
        this.birthdateThru = birthdateThru;
    }
    public List<ClassifierResult> getResidenceCountry() {
        return residenceCountry;
    }
    public void setResidenceCountry(List<ClassifierResult> residenceCountry) {
        this.residenceCountry = residenceCountry;
    }
    public Boolean getResidenceCountryShow() {
        return residenceCountryShow;
    }
    public void setResidenceCountryShow(Boolean residenceCountryShow) {
        this.residenceCountryShow = residenceCountryShow;
    }
    public List<ClassifierResult> getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(List<ClassifierResult> citizenship) {
        this.citizenship = citizenship;
    }
    public Boolean getCitizenshipShow() {
        return citizenshipShow;
    }
    public void setCitizenshipShow(Boolean citizenshipShow) {
        this.citizenshipShow = citizenshipShow;
    }
    public String getBankaccount() {
        return bankaccount;
    }
    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }
    public Boolean getGuestStudent() {
        return guestStudent;
    }
    public void setGuestStudent(Boolean guestStudent) {
        this.guestStudent = guestStudent;
    }
    public Boolean getGuestStudentShow() {
        return guestStudentShow;
    }
    public void setGuestStudentShow(Boolean guestStudentShow) {
        this.guestStudentShow = guestStudentShow;
    }
    public Boolean getForeignStudent() {
        return foreignStudent;
    }
    public void setForeignStudent(Boolean foreignStudent) {
        this.foreignStudent = foreignStudent;
    }
    public Boolean getForeignStudentShow() {
        return foreignStudentShow;
    }
    public void setForeignStudentShow(Boolean foreignStudentShow) {
        this.foreignStudentShow = foreignStudentShow;
    }
    public Boolean getCumLaude() {
        return cumLaude;
    }
    public void setCumLaude(Boolean cumLaude) {
        this.cumLaude = cumLaude;
    }
    public Boolean getCumLaudeShow() {
        return cumLaudeShow;
    }
    public void setCumLaudeShow(Boolean cumLaudeShow) {
        this.cumLaudeShow = cumLaudeShow;
    }
    public LocalDate getImmatDateFrom() {
        return immatDateFrom;
    }
    public void setImmatDateFrom(LocalDate immatDateFrom) {
        this.immatDateFrom = immatDateFrom;
    }
    public Boolean getImmatDateShow() {
        return immatDateShow;
    }
    public void setImmatDateShow(Boolean immatDateShow) {
        this.immatDateShow = immatDateShow;
    }
    public LocalDate getImmatDateThru() {
        return immatDateThru;
    }
    public void setImmatDateThru(LocalDate immatDateThru) {
        this.immatDateThru = immatDateThru;
    }
    public LocalDate getFinishedDateFrom() {
        return finishedDateFrom;
    }
    public void setFinishedDateFrom(LocalDate finishedDateFrom) {
        this.finishedDateFrom = finishedDateFrom;
    }
    public Boolean getFinishedDate() {
        return finishedDate;
    }
    public void setFinishedDate(Boolean finishedDate) {
        this.finishedDate = finishedDate;
    }
    public Boolean getFinishedDateShow() {
        return finishedDateShow;
    }
    public void setFinishedDateShow(Boolean finishedDateShow) {
        this.finishedDateShow = finishedDateShow;
    }
    public LocalDate getFinishedDateThru() {
        return finishedDateThru;
    }
    public void setFinishedDateThru(LocalDate finishedDateThru) {
        this.finishedDateThru = finishedDateThru;
    }
    public Boolean getDirectiveTypesShow() {
        return directiveTypesShow;
    }
    public void setDirectiveTypesShow(Boolean directiveTypesShow) {
        this.directiveTypesShow = directiveTypesShow;
    }
    public Boolean getDirectiveReasonsShow() {
        return directiveReasonsShow;
    }
    public void setDirectiveReasonsShow(Boolean directiveReasonsShow) {
        this.directiveReasonsShow = directiveReasonsShow;
    }
    public LocalDate getDirectiveConfirmDateFrom() {
        return directiveConfirmDateFrom;
    }
    public void setDirectiveConfirmDateFrom(LocalDate directiveConfirmDateFrom) {
        this.directiveConfirmDateFrom = directiveConfirmDateFrom;
    }
    public Boolean getDirectiveConfirmDateShow() {
        return directiveConfirmDateShow;
    }
    public void setDirectiveConfirmDateShow(Boolean directiveConfirmDateShow) {
        this.directiveConfirmDateShow = directiveConfirmDateShow;
    }
    public LocalDate getDirectiveConfirmDateThru() {
        return directiveConfirmDateThru;
    }
    public void setDirectiveConfirmDateThru(LocalDate directiveConfirmDateThru) {
        this.directiveConfirmDateThru = directiveConfirmDateThru;
    }
    public List<AutocompleteResult> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(List<AutocompleteResult> studentGroups) {
        this.studentGroups = studentGroups;
    }
    public Boolean getStudentGroupsShow() {
        return studentGroupsShow;
    }
    public void setStudentGroupsShow(Boolean studentGroupsShow) {
        this.studentGroupsShow = studentGroupsShow;
    }
    public Boolean getStudentStatusesShow() {
        return studentStatusesShow;
    }
    public void setStudentStatusesShow(Boolean studentStatusesShow) {
        this.studentStatusesShow = studentStatusesShow;
    }
    public Boolean getStudyFormShow() {
        return studyFormShow;
    }
    public void setStudyFormShow(Boolean studyFormShow) {
        this.studyFormShow = studyFormShow;
    }
    public Boolean getStudyLoadShow() {
        return studyLoadShow;
    }
    public void setStudyLoadShow(Boolean studyLoadShow) {
        this.studyLoadShow = studyLoadShow;
    }
    public List<AutocompleteResult> getSchoolDepartment() {
        return schoolDepartment;
    }
    public void setSchoolDepartment(List<AutocompleteResult> schoolDepartment) {
        this.schoolDepartment = schoolDepartment;
    }
    public Boolean getSchoolDepartmentShow() {
        return schoolDepartmentShow;
    }
    public void setSchoolDepartmentShow(Boolean schoolDepartmentShow) {
        this.schoolDepartmentShow = schoolDepartmentShow;
    }
    public List<AutocompleteResult> getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(List<AutocompleteResult> curriculum) {
        this.curriculum = curriculum;
    }
    public Boolean getCurriculumShow() {
        return curriculumShow;
    }
    public void setCurriculumShow(Boolean curriculumShow) {
        this.curriculumShow = curriculumShow;
    }
    public String getEhisCode() {
        return ehisCode;
    }
    public void setEhisCode(String ehisCode) {
        this.ehisCode = ehisCode;
    }
    public Boolean getEhisCodeShow() {
        return ehisCodeShow;
    }
    public void setEhisCodeShow(Boolean ehisCodeShow) {
        this.ehisCodeShow = ehisCodeShow;
    }
    public Boolean getStudyLevelShow() {
        return studyLevelShow;
    }
    public void setStudyLevelShow(Boolean studyLevelShow) {
        this.studyLevelShow = studyLevelShow;
    }
    public List<ClassifierResult> getSpeciality() {
        return speciality;
    }
    public void setSpeciality(List<ClassifierResult> speciality) {
        this.speciality = speciality;
    }
    public Boolean getSpecialityShow() {
        return specialityShow;
    }
    public void setSpecialityShow(Boolean specialityShow) {
        this.specialityShow = specialityShow;
    }
    public Long getStudyYearNumber() {
        return studyYearNumber;
    }
    public void setStudyYearNumber(Long studyYearNumber) {
        this.studyYearNumber = studyYearNumber;
    }
    public Boolean getStudyYearNumberShow() {
        return studyYearNumberShow;
    }
    public void setStudyYearNumberShow(Boolean studyYearNumberShow) {
        this.studyYearNumberShow = studyYearNumberShow;
    }
    public Boolean getFinShow() {
        return finShow;
    }
    public void setFinShow(Boolean finShow) {
        this.finShow = finShow;
    }
    public Boolean getLanguageShow() {
        return languageShow;
    }
    public void setLanguageShow(Boolean languageShow) {
        this.languageShow = languageShow;
    }
    public Boolean getCurriculumPercentageShow() {
        return curriculumPercentageShow;
    }
    public void setCurriculumPercentageShow(Boolean curriculumPercentageShow) {
        this.curriculumPercentageShow = curriculumPercentageShow;
    }
    public BigDecimal getCurriculumPercentageFrom() {
        return curriculumPercentageFrom;
    }
    public void setCurriculumPercentageFrom(BigDecimal curriculumPercentageFrom) {
        this.curriculumPercentageFrom = curriculumPercentageFrom;
    }
    public BigDecimal getCurriculumPercentageThru() {
        return curriculumPercentageThru;
    }
    public void setCurriculumPercentageThru(BigDecimal curriculumPercentageThru) {
        this.curriculumPercentageThru = curriculumPercentageThru;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Boolean getAddressShow() {
        return addressShow;
    }
    public void setAddressShow(Boolean addressShow) {
        this.addressShow = addressShow;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Boolean getPhoneShow() {
        return phoneShow;
    }
    public void setPhoneShow(Boolean phoneShow) {
        this.phoneShow = phoneShow;
    }
    public String getOfficialEmail() {
        return officialEmail;
    }
    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }
    public Boolean getOfficialEmailShow() {
        return officialEmailShow;
    }
    public void setOfficialEmailShow(Boolean officialEmailShow) {
        this.officialEmailShow = officialEmailShow;
    }
    public String getPersonalEmail() {
        return personalEmail;
    }
    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }
    public Boolean getPersonalEmailShow() {
        return personalEmailShow;
    }
    public void setPersonalEmailShow(Boolean personalEmailShow) {
        this.personalEmailShow = personalEmailShow;
    }
    public String getEapSign() {
        return eapSign;
    }
    public void setEapSign(String eapSign) {
        this.eapSign = eapSign;
    }
    public Long getEap() {
        return eap;
    }
    public void setEap(Long eap) {
        this.eap = eap;
    }
    public Boolean getEapShow() {
        return eapShow;
    }
    public void setEapShow(Boolean eapShow) {
        this.eapShow = eapShow;
    }
    public String getEapSumSign() {
        return eapSumSign;
    }
    public void setEapSumSign(String eapSumSign) {
        this.eapSumSign = eapSumSign;
    }
    public Long getEapSum() {
        return eapSum;
    }
    public void setEapSum(Long eapSum) {
        this.eapSum = eapSum;
    }
    public Boolean getEapSumApel() {
        return eapSumApel;
    }
    public void setEapSumApel(Boolean eapSumApel) {
        this.eapSumApel = eapSumApel;
    }
    public LocalDate getEapSumFrom() {
        return eapSumFrom;
    }
    public void setEapSumFrom(LocalDate eapSumFrom) {
        this.eapSumFrom = eapSumFrom;
    }
    public LocalDate getEapSumThru() {
        return eapSumThru;
    }
    public void setEapSumThru(LocalDate eapSumThru) {
        this.eapSumThru = eapSumThru;
    }
    public Long getEapSumPeriod() {
        return eapSumPeriod;
    }
    public void setEapSumPeriod(Long eapSumPeriod) {
        this.eapSumPeriod = eapSumPeriod;
    }
    public Boolean getEapSumShow() {
        return eapSumShow;
    }
    public void setEapSumShow(Boolean eapSumShow) {
        this.eapSumShow = eapSumShow;
    }
    public String getWeightedAverageSumSign() {
        return weightedAverageSumSign;
    }
    public void setWeightedAverageSumSign(String weightedAverageSumSign) {
        this.weightedAverageSumSign = weightedAverageSumSign;
    }
    public BigDecimal getWeightedAverageSum() {
        return weightedAverageSum;
    }
    public void setWeightedAverageSum(BigDecimal weightedAverageSum) {
        this.weightedAverageSum = weightedAverageSum;
    }
    public Boolean getWeightedAverageSumShow() {
        return weightedAverageSumShow;
    }
    public void setWeightedAverageSumShow(Boolean weightedAverageSumShow) {
        this.weightedAverageSumShow = weightedAverageSumShow;
    }
    public String getWeightedAverageSign() {
        return weightedAverageSign;
    }
    public void setWeightedAverageSign(String weightedAverageSign) {
        this.weightedAverageSign = weightedAverageSign;
    }
    public BigDecimal getWeightedAverage() {
        return weightedAverage;
    }
    public void setWeightedAverage(BigDecimal weightedAverage) {
        this.weightedAverage = weightedAverage;
    }
    public LocalDate getWeightedAverageFrom() {
        return weightedAverageFrom;
    }
    public void setWeightedAverageFrom(LocalDate weightedAverageFrom) {
        this.weightedAverageFrom = weightedAverageFrom;
    }
    public LocalDate getWeightedAverageThru() {
        return weightedAverageThru;
    }
    public void setWeightedAverageThru(LocalDate weightedAverageThru) {
        this.weightedAverageThru = weightedAverageThru;
    }
    public Long getWeightedAveragePeriod() {
        return weightedAveragePeriod;
    }
    public void setWeightedAveragePeriod(Long weightedAveragePeriod) {
        this.weightedAveragePeriod = weightedAveragePeriod;
    }
    public Boolean getWeightedAverageShow() {
        return weightedAverageShow;
    }
    public void setWeightedAverageShow(Boolean weightedAverageShow) {
        this.weightedAverageShow = weightedAverageShow;
    }
    public String getAverageSumSign() {
        return averageSumSign;
    }
    public void setAverageSumSign(String averageSumSign) {
        this.averageSumSign = averageSumSign;
    }
    public BigDecimal getAverageSum() {
        return averageSum;
    }
    public void setAverageSum(BigDecimal averageSum) {
        this.averageSum = averageSum;
    }
    public Boolean getAverageSumShow() {
        return averageSumShow;
    }
    public void setAverageSumShow(Boolean averageSumShow) {
        this.averageSumShow = averageSumShow;
    }
    public String getAverageSign() {
        return averageSign;
    }
    public void setAverageSign(String averageSign) {
        this.averageSign = averageSign;
    }
    public BigDecimal getAverage() {
        return average;
    }
    public void setAverage(BigDecimal average) {
        this.average = average;
    }
    public LocalDate getAverageFrom() {
        return averageFrom;
    }
    public void setAverageFrom(LocalDate averageFrom) {
        this.averageFrom = averageFrom;
    }
    public LocalDate getAverageThru() {
        return averageThru;
    }
    public void setAverageThru(LocalDate averageThru) {
        this.averageThru = averageThru;
    }
    public Long getAveragePeriod() {
        return averagePeriod;
    }
    public void setAveragePeriod(Long averagePeriod) {
        this.averagePeriod = averagePeriod;
    }
    public Boolean getAverageShow() {
        return averageShow;
    }
    public void setAverageShow(Boolean averageShow) {
        this.averageShow = averageShow;
    }
    public String getDebtSumSign() {
        return debtSumSign;
    }
    public void setDebtSumSign(String debtSumSign) {
        this.debtSumSign = debtSumSign;
    }
    public BigDecimal getDebtSum() {
        return debtSum;
    }
    public void setDebtSum(BigDecimal debtSum) {
        this.debtSum = debtSum;
    }
    public Boolean getDebtSumShow() {
        return debtSumShow;
    }
    public void setDebtSumShow(Boolean debtSumShow) {
        this.debtSumShow = debtSumShow;
    }
    public Long getDebt() {
        return debt;
    }
    public void setDebt(Long debt) {
        this.debt = debt;
    }
    public String getDebtSign() {
        return debtSign;
    }
    public void setDebtSign(String debtSign) {
        this.debtSign = debtSign;
    }
    public LocalDate getDebtFrom() {
        return debtFrom;
    }
    public void setDebtFrom(LocalDate debtFrom) {
        this.debtFrom = debtFrom;
    }
    public LocalDate getDebtThru() {
        return debtThru;
    }
    public void setDebtThru(LocalDate debtThru) {
        this.debtThru = debtThru;
    }
    public Long getDebtPeriod() {
        return debtPeriod;
    }
    public void setDebtPeriod(Long debtPeriod) {
        this.debtPeriod = debtPeriod;
    }
    public Boolean getDebtShow() {
        return debtShow;
    }
    public void setDebtShow(Boolean debtShow) {
        this.debtShow = debtShow;
    }
    public String getDebtPointsSumSign() {
        return debtPointsSumSign;
    }
    public void setDebtPointsSumSign(String debtPointsSumSign) {
        this.debtPointsSumSign = debtPointsSumSign;
    }
    public BigDecimal getDebtPointsSum() {
        return debtPointsSum;
    }
    public void setDebtPointsSum(BigDecimal debtPointsSum) {
        this.debtPointsSum = debtPointsSum;
    }
    public Boolean getDebtPointsSumShow() {
        return debtPointsSumShow;
    }
    public void setDebtPointsSumShow(Boolean debtPointsSumShow) {
        this.debtPointsSumShow = debtPointsSumShow;
    }
    public BigDecimal getDebtPoints() {
        return debtPoints;
    }
    public void setDebtPoints(BigDecimal debtPoints) {
        this.debtPoints = debtPoints;
    }
    public Boolean getDebtPointsShow() {
        return debtPointsShow;
    }
    public void setDebtPointsShow(Boolean debtPointsShow) {
        this.debtPointsShow = debtPointsShow;
    }
    public String getDebtPointsSign() {
        return debtPointsSign;
    }
    public void setDebtPointsSign(String debtPointsSign) {
        this.debtPointsSign = debtPointsSign;
    }
    public LocalDate getDebtPointsFrom() {
        return debtPointsFrom;
    }
    public void setDebtPointsFrom(LocalDate debtPointsFrom) {
        this.debtPointsFrom = debtPointsFrom;
    }
    public LocalDate getDebtPointsThru() {
        return debtPointsThru;
    }
    public void setDebtPointsThru(LocalDate debtPointsThru) {
        this.debtPointsThru = debtPointsThru;
    }
    public Long getDebtPointsPeriod() {
        return debtPointsPeriod;
    }
    public void setDebtPointsPeriod(Long debtPointsPeriod) {
        this.debtPointsPeriod = debtPointsPeriod;
    }
    public String getDeclaredEapSign() {
        return declaredEapSign;
    }
    public void setDeclaredEapSign(String declaredEapSign) {
        this.declaredEapSign = declaredEapSign;
    }
    public BigDecimal getDeclaredEap() {
        return declaredEap;
    }
    public void setDeclaredEap(BigDecimal declaredEap) {
        this.declaredEap = declaredEap;
    }
    public Boolean getDeclaredEapShow() {
        return declaredEapShow;
    }
    public void setDeclaredEapShow(Boolean declaredEapShow) {
        this.declaredEapShow = declaredEapShow;
    }
    public LocalDate getDeclaredEapFrom() {
        return declaredEapFrom;
    }
    public void setDeclaredEapFrom(LocalDate declaredEapFrom) {
        this.declaredEapFrom = declaredEapFrom;
    }
    public LocalDate getDeclaredEapThru() {
        return declaredEapThru;
    }
    public void setDeclaredEapThru(LocalDate declaredEapThru) {
        this.declaredEapThru = declaredEapThru;
    }
    public Long getDeclaredEapPeriod() {
        return declaredEapPeriod;
    }
    public void setDeclaredEapPeriod(Long declaredEapPeriod) {
        this.declaredEapPeriod = declaredEapPeriod;
    }
    public List<AutocompleteResult> getActiveResult() {
        return activeResult;
    }
    public void setActiveResult(List<AutocompleteResult> activeResult) {
        this.activeResult = activeResult;
    }
    public Boolean getActiveResultShow() {
        return activeResultShow;
    }
    public void setActiveResultShow(Boolean activeResultShow) {
        this.activeResultShow = activeResultShow;
    }
    public Boolean getActiveResultPositive() {
        return activeResultPositive;
    }
    public void setActiveResultPositive(Boolean activeResultPositive) {
        this.activeResultPositive = activeResultPositive;
    }
    public List<AutocompleteResult> getDeclaredSubject() {
        return declaredSubject;
    }
    public void setDeclaredSubject(List<AutocompleteResult> declaredSubject) {
        this.declaredSubject = declaredSubject;
    }
    public Boolean getDeclaredSubjectShow() {
        return declaredSubjectShow;
    }
    public void setDeclaredSubjectShow(Boolean declaredSubjectShow) {
        this.declaredSubjectShow = declaredSubjectShow;
    }
    public Boolean getDeclaredSubjectRepetitive() {
        return declaredSubjectRepetitive;
    }
    public void setDeclaredSubjectRepetitive(Boolean declaredSubjectRepetitive) {
        this.declaredSubjectRepetitive = declaredSubjectRepetitive;
    }
    public Boolean getDeclarationConfirmationShow() {
        return declarationConfirmationShow;
    }
    public void setDeclarationConfirmationShow(Boolean declarationConfirmationShow) {
        this.declarationConfirmationShow = declarationConfirmationShow;
    }
    public LocalDate getDeclarationConfirmationFrom() {
        return declarationConfirmationFrom;
    }
    public void setDeclarationConfirmationFrom(LocalDate declarationConfirmationFrom) {
        this.declarationConfirmationFrom = declarationConfirmationFrom;
    }
    public LocalDate getDeclarationConfirmationThru() {
        return declarationConfirmationThru;
    }
    public void setDeclarationConfirmationThru(LocalDate declarationConfirmationThru) {
        this.declarationConfirmationThru = declarationConfirmationThru;
    }
    public Boolean getPreviousSchoolNameShow() {
        return previousSchoolNameShow;
    }
    public void setPreviousSchoolNameShow(Boolean previousSchoolNameShow) {
        this.previousSchoolNameShow = previousSchoolNameShow;
    }
    public String getPreviousSchoolName() {
        return previousSchoolName;
    }
    public void setPreviousSchoolName(String previousSchoolName) {
        this.previousSchoolName = previousSchoolName;
    }
    public Boolean getCompletedSchoolYearShow() {
        return completedSchoolYearShow;
    }
    public void setCompletedSchoolYearShow(Boolean completedSchoolYearShow) {
        this.completedSchoolYearShow = completedSchoolYearShow;
    }
    public String getCompletedSchoolYearSign() {
        return completedSchoolYearSign;
    }
    public void setCompletedSchoolYearSign(String completedSchoolYearSign) {
        this.completedSchoolYearSign = completedSchoolYearSign;
    }
    public Long getCompletedSchoolYear() {
        return completedSchoolYear;
    }
    public void setCompletedSchoolYear(Long completedSchoolYear) {
        this.completedSchoolYear = completedSchoolYear;
    }
    public List<String> getDirectiveTypes() {
        return directiveTypes;
    }
    public void setDirectiveTypes(List<String> directiveTypes) {
        this.directiveTypes = directiveTypes;
    }
    public List<String> getDirectiveReasons() {
        return directiveReasons;
    }
    public void setDirectiveReasons(List<String> directiveReasons) {
        this.directiveReasons = directiveReasons;
    }
    public List<String> getStudentStatuses() {
        return studentStatuses;
    }
    public void setStudentStatuses(List<String> studentStatuses) {
        this.studentStatuses = studentStatuses;
    }
    public List<String> getStudyForm() {
        return studyForm;
    }
    public void setStudyForm(List<String> studyForm) {
        this.studyForm = studyForm;
    }
    public List<String> getStudyLoad() {
        return studyLoad;
    }
    public void setStudyLoad(List<String> studyLoad) {
        this.studyLoad = studyLoad;
    }
    public List<String> getStudyLevel() {
        return studyLevel;
    }
    public void setStudyLevel(List<String> studyLevel) {
        this.studyLevel = studyLevel;
    }
    public List<String> getFin() {
        return fin;
    }
    public void setFin(List<String> fin) {
        this.fin = fin;
    }
    public List<String> getLanguage() {
        return language;
    }
    public void setLanguage(List<String> language) {
        this.language = language;
    }
    public Boolean getNrShow() {
        return nrShow;
    }
    public void setNrShow(Boolean nrShow) {
        this.nrShow = nrShow;
    }
    public List<AutocompleteResult> getSpecialityHigher() {
        return specialityHigher;
    }
    public void setSpecialityHigher(List<AutocompleteResult> specialityHigher) {
        this.specialityHigher = specialityHigher;
    }
    public String getRegNr() {
        return regNr;
    }
    public void setRegNr(String regNr) {
        this.regNr = regNr;
    }
    public Boolean getRegNrShow() {
        return regNrShow;
    }
    public void setRegNrShow(Boolean regNrShow) {
        this.regNrShow = regNrShow;
    }
    public LocalDate getNominalStudyEndFrom() {
        return nominalStudyEndFrom;
    }
    public void setNominalStudyEndFrom(LocalDate nominalStudyEndFrom) {
        this.nominalStudyEndFrom = nominalStudyEndFrom;
    }
    public LocalDate getNominalStudyEndThru() {
        return nominalStudyEndThru;
    }
    public void setNominalStudyEndThru(LocalDate nominalStudyEndThru) {
        this.nominalStudyEndThru = nominalStudyEndThru;
    }
    public Boolean getNominalStudyEndShow() {
        return nominalStudyEndShow;
    }
    public void setNominalStudyEndShow(Boolean nominalStudyEndShow) {
        this.nominalStudyEndShow = nominalStudyEndShow;
    }
    public List<String> getForeignLanguage() {
        return foreignLanguage;
    }
    public void setForeignLanguage(List<String> foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }
    public Boolean getForeignLanguageShow() {
        return foreignLanguageShow;
    }
    public void setForeignLanguageShow(Boolean foreignLanguageShow) {
        this.foreignLanguageShow = foreignLanguageShow;
    }
    public List<String> getPreviousStudyLevel() {
        return previousStudyLevel;
    }
    public void setPreviousStudyLevel(List<String> previousStudyLevel) {
        this.previousStudyLevel = previousStudyLevel;
    }
    public Boolean getPreviousStudyLevelShow() {
        return previousStudyLevelShow;
    }
    public void setPreviousStudyLevelShow(Boolean previousStudyLevelShow) {
        this.previousStudyLevelShow = previousStudyLevelShow;
    }
    public List<String> getDormitory() {
        return dormitory;
    }
    public void setDormitory(List<String> dormitory) {
        this.dormitory = dormitory;
    }
    public Boolean getDormitoryShow() {
        return dormitoryShow;
    }
    public void setDormitoryShow(Boolean dormitoryShow) {
        this.dormitoryShow = dormitoryShow;
    }
    public Boolean getAge() {
        return age;
    }
    public void setAge(Boolean age) {
        this.age = age;
    }
    public Boolean getAgeShow() {
        return ageShow;
    }
    public void setAgeShow(Boolean ageShow) {
        this.ageShow = ageShow;
    }
    public Boolean getSpecialNeed() {
        return specialNeed;
    }
    public void setSpecialNeed(Boolean specialNeed) {
        this.specialNeed = specialNeed;
    }
    public Boolean getSpecialNeedShow() {
        return specialNeedShow;
    }
    public void setSpecialNeedShow(Boolean specialNeedShow) {
        this.specialNeedShow = specialNeedShow;
    }
    public List<String> getSpecialNeedCode() {
        return specialNeedCode;
    }
    public void setSpecialNeedCode(List<String> specialNeedCode) {
        this.specialNeedCode = specialNeedCode;
    }
    public Boolean getSpecialNeedCodeShow() {
        return specialNeedCodeShow;
    }
    public void setSpecialNeedCodeShow(Boolean specialNeedCodeShow) {
        this.specialNeedCodeShow = specialNeedCodeShow;
    }
    public String getStudyCompany() {
        return studyCompany;
    }
    public void setStudyCompany(String studyCompany) {
        this.studyCompany = studyCompany;
    }
    public Boolean getStudyCompanyShow() {
        return studyCompanyShow;
    }
    public void setStudyCompanyShow(Boolean studyCompanyShow) {
        this.studyCompanyShow = studyCompanyShow;
    }
    public String getOtherContact() {
        return otherContact;
    }
    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact;
    }
    public Boolean getOtherContactShow() {
        return otherContactShow;
    }
    public void setOtherContactShow(Boolean otherContactShow) {
        this.otherContactShow = otherContactShow;
    }
    public String getApelEapSign() {
        return apelEapSign;
    }
    public void setApelEapSign(String apelEapSign) {
        this.apelEapSign = apelEapSign;
    }
    public BigDecimal getApelEap() {
        return apelEap;
    }
    public void setApelEap(BigDecimal apelEap) {
        this.apelEap = apelEap;
    }
    public Boolean getApelEapShow() {
        return apelEapShow;
    }
    public void setApelEapShow(Boolean apelEapShow) {
        this.apelEapShow = apelEapShow;
    }
    public String getRepresentativeName() {
        return representativeName;
    }
    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }
    public Boolean getRepresentativeNameShow() {
        return representativeNameShow;
    }
    public void setRepresentativeNameShow(Boolean representativeNameShow) {
        this.representativeNameShow = representativeNameShow;
    }
    public String getRepresentativeIdcode() {
        return representativeIdcode;
    }
    public void setRepresentativeIdcode(String representativeIdcode) {
        this.representativeIdcode = representativeIdcode;
    }
    public Boolean getRepresentativeIdcodeShow() {
        return representativeIdcodeShow;
    }
    public void setRepresentativeIdcodeShow(Boolean representativeIdcodeShow) {
        this.representativeIdcodeShow = representativeIdcodeShow;
    }
    public List<String> getRepresentativeRelation() {
        return representativeRelation;
    }
    public void setRepresentativeRelation(List<String> representativeRelation) {
        this.representativeRelation = representativeRelation;
    }
    public Boolean getRepresentativeRelationShow() {
        return representativeRelationShow;
    }
    public void setRepresentativeRelationShow(Boolean representativeRelationShow) {
        this.representativeRelationShow = representativeRelationShow;
    }
    public String getRepresentativePhone() {
        return representativePhone;
    }
    public void setRepresentativePhone(String representativePhone) {
        this.representativePhone = representativePhone;
    }
    public Boolean getRepresentativePhoneShow() {
        return representativePhoneShow;
    }
    public void setRepresentativePhoneShow(Boolean representativePhoneShow) {
        this.representativePhoneShow = representativePhoneShow;
    }
    public String getRepresentativeOtherContact() {
        return representativeOtherContact;
    }
    public void setRepresentativeOtherContact(String representativeOtherContact) {
        this.representativeOtherContact = representativeOtherContact;
    }
    public Boolean getRepresentativeOtherContactShow() {
        return representativeOtherContactShow;
    }
    public void setRepresentativeOtherContactShow(Boolean representativeOtherContactShow) {
        this.representativeOtherContactShow = representativeOtherContactShow;
    }
    public Boolean getRepresentativeVisible() {
        return representativeVisible;
    }
    public void setRepresentativeVisible(Boolean representativeVisible) {
        this.representativeVisible = representativeVisible;
    }
    public Boolean getRepresentativeVisibleShow() {
        return representativeVisibleShow;
    }
    public void setRepresentativeVisibleShow(Boolean representativeVisibleShow) {
        this.representativeVisibleShow = representativeVisibleShow;
    }
    public Boolean getOnlyWithRepresentative() {
        return onlyWithRepresentative;
    }
    public void setOnlyWithRepresentative(Boolean onlyWithRepresentative) {
        this.onlyWithRepresentative = onlyWithRepresentative;
    }
    public String getRepresentativeEmail() {
        return representativeEmail;
    }
    public void setRepresentativeEmail(String representativeEmail) {
        this.representativeEmail = representativeEmail;
    }
    public Boolean getRepresentativeEmailShow() {
        return representativeEmailShow;
    }
    public void setRepresentativeEmailShow(Boolean representativeEmailShow) {
        this.representativeEmailShow = representativeEmailShow;
    }
    public LocalDate getAkadDateFrom() {
        return akadDateFrom;
    }
    public void setAkadDateFrom(LocalDate akadDateFrom) {
        this.akadDateFrom = akadDateFrom;
    }
    public LocalDate getAkadDateThru() {
        return akadDateThru;
    }
    public void setAkadDateThru(LocalDate akadDateThru) {
        this.akadDateThru = akadDateThru;
    }
    public LocalDate getValisDateFrom() {
        return valisDateFrom;
    }
    public void setValisDateFrom(LocalDate valisDateFrom) {
        this.valisDateFrom = valisDateFrom;
    }
    public LocalDate getValisDateThru() {
        return valisDateThru;
    }
    public void setValisDateThru(LocalDate valisDateThru) {
        this.valisDateThru = valisDateThru;
    }
    public LocalDate getGuestDateFrom() {
        return guestDateFrom;
    }
    public void setGuestDateFrom(LocalDate guestDateFrom) {
        this.guestDateFrom = guestDateFrom;
    }
    public LocalDate getGuestDateThru() {
        return guestDateThru;
    }
    public void setGuestDateThru(LocalDate guestDateThru) {
        this.guestDateThru = guestDateThru;
    }

}
