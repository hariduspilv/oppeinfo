package ee.hitsa.ois.web.dto.sais;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import ee.hitsa.ois.domain.sais.SaisApplication;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.SaisAdmissionUtil;
import ee.hitsa.ois.util.StreamUtil;

public class SaisApplicationDto {

    private Long id;
    private String applicationNr;
    private String idcode;
    private String firstname;
    private String lastname;
    private String sex;
    private String residenceCountry;
    private String citizenship;
    private String language;
    private String email;
    private String address;
    private String phone;
    private String status;
    private String studyForm;
    private String studyLoad;
    private LocalDate submitted;
    private LocalDate saisChanged;
    private String saisAdmissionCode;
    private Boolean isHigher;
    private Set<SaisApplicationGraduatedSchoolDto> graduatedSchools;
    private Set<SaisApplicationGradeDto> grades;
    private Set<SaisApplicationOtherDataDto> otherData;

    public static SaisApplicationDto of(SaisApplication saisApplication) {
        SaisApplicationDto dto = EntityUtil.bindToDto(saisApplication, new SaisApplicationDto(), "graduatedSchools", "grades", "otherData");
        dto.setSaisAdmissionCode(saisApplication.getSaisAdmission().getCode());
        dto.getGraduatedSchools().addAll(StreamUtil.toMappedList(SaisApplicationGraduatedSchoolDto::new, saisApplication.getGraduatedSchools()));
        dto.getGrades().addAll(StreamUtil.toMappedList(SaisApplicationGradeDto::of, saisApplication.getGrades()));
        dto.getOtherData().addAll(StreamUtil.toMappedList(SaisApplicationOtherDataDto::of, saisApplication.getOtherData()));
        dto.setIsHigher(Boolean.valueOf(SaisAdmissionUtil.isHigher(saisApplication.getSaisAdmission())));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationNr() {
        return applicationNr;
    }

    public void setApplicationNr(String applicationNr) {
        this.applicationNr = applicationNr;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDate getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDate submitted) {
        this.submitted = submitted;
    }

    public LocalDate getSaisChanged() {
        return saisChanged;
    }

    public void setSaisChanged(LocalDate saisChanged) {
        this.saisChanged = saisChanged;
    }

    public String getSaisAdmissionCode() {
        return saisAdmissionCode;
    }

    public void setSaisAdmissionCode(String saisAdmissionCode) {
        this.saisAdmissionCode = saisAdmissionCode;
    }

    public Set<SaisApplicationGraduatedSchoolDto> getGraduatedSchools() {
        return graduatedSchools != null ? graduatedSchools : (graduatedSchools = new HashSet<>());
    }

    public void setGraduatedSchools(Set<SaisApplicationGraduatedSchoolDto> graduatedSchools) {
        this.graduatedSchools = graduatedSchools;
    }

    public Set<SaisApplicationGradeDto> getGrades() {
        return grades != null ? grades : (grades = new HashSet<>());
    }

    public void setGrades(Set<SaisApplicationGradeDto> grades) {
        this.grades = grades;
    }

    public Set<SaisApplicationOtherDataDto> getOtherData() {
        return otherData != null ? otherData : (otherData = new HashSet<>());
    }

    public void setOtherData(Set<SaisApplicationOtherDataDto> otherData) {
        this.otherData = otherData;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }


}
