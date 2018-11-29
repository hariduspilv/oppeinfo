package ee.hitsa.ois.domain.school;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.util.Translatable;

/**
 * TODO: Logo file does not need to be sent with school, but on request only!
 * Disadvantage of solution below with @JsonIdentityReference annotation
 * is that updating school without changing logo does not work!
 *
 * Annotation below comes to BaseEntityWithId class
 * @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
 *
 * Commented out code might be used for solution of this issue.
 *
 * Currently for displaying list of schools is used SchoolWithoutLogo interface
 */
@Entity
public class School extends BaseEntityWithId implements Translatable {

    @Column(nullable = false)
    private String nameEt;
    @Column(nullable = false)
    private String nameEn;
    @Column(nullable = false)
    private String email;
    private String code;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "ois_file_id")
    private OisFile logo;
    @JsonIgnore
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchoolStudyLevel> studyLevels;
    @JsonIgnore
    @OneToMany(mappedBy = "school", orphanRemoval = true)
    private List<SchoolDepartment> schoolDepartments;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier ehisSchool;
    private String address;
    private String addressAds;
    private String addressAdsOid;
    private String phone;
    private String emailDomain;
    private Boolean generateUserEmail;
    private String rtipSchoolCode;
    private String adUrl;
    private Long adPort;
    private String adDomain;
    private String adBase;
    private String adIdcodeField;
    private String nameGenitiveEt;
    private Boolean isMinorStudentAbsence = Boolean.FALSE;
    private Boolean isLetterGrade = Boolean.FALSE;
    private String finalSchoolType;
    @Column(name = "final_62")
    private String final62;
    private String finalSchoolTypeEn;
    @Column(name = "final_en_62")
    private String finalEn62;

    @JsonIgnore
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyYearScheduleLegend> studyYearScheduleLegends;

    @Override
    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    @Override
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SchoolStudyLevel> getStudyLevels() {
        return studyLevels;
    }

    public void setStudyLevels(List<SchoolStudyLevel> studyLevels) {
        this.studyLevels = studyLevels;
    }

    public OisFile getLogo() {
        return logo;
    }

    public void setLogo(OisFile logo) {
        this.logo = logo;
    }

    public Classifier getEhisSchool() {
        return ehisSchool;
    }

    public void setEhisSchool(Classifier ehisSchool) {
        this.ehisSchool = ehisSchool;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressAds() {
        return addressAds;
    }

    public void setAddressAds(String addressAds) {
        this.addressAds = addressAds;
    }

    public String getAddressAdsOid() {
        return addressAdsOid;
    }

    public void setAddressAdsOid(String addressAdsOid) {
        this.addressAdsOid = addressAdsOid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public Boolean getGenerateUserEmail() {
        return generateUserEmail;
    }

    public void setGenerateUserEmail(Boolean generateUserEmail) {
        this.generateUserEmail = generateUserEmail;
    }

    public List<SchoolDepartment> getSchoolDepartments() {
        return schoolDepartments;
    }

    public void setSchoolDepartments(List<SchoolDepartment> schoolDepartments) {
        this.schoolDepartments = schoolDepartments;
    }

    public List<StudyYearScheduleLegend> getStudyYearScheduleLegends() {
        return studyYearScheduleLegends != null ? studyYearScheduleLegends : (studyYearScheduleLegends = new ArrayList<>());
    }

    public void setStudyYearScheduleLegends(List<StudyYearScheduleLegend> studyYearScheduleLegends) {
        getStudyYearScheduleLegends().clear();
        if(studyYearScheduleLegends != null) {
            getStudyYearScheduleLegends().addAll(studyYearScheduleLegends);
        }
    }

    public String getRtipSchoolCode() {
        return rtipSchoolCode;
    }

    public void setRtipSchoolCode(String rtipSchoolCode) {
        this.rtipSchoolCode = rtipSchoolCode;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public Long getAdPort() {
        return adPort;
    }

    public void setAdPort(Long adPort) {
        this.adPort = adPort;
    }

    public String getAdDomain() {
        return adDomain;
    }

    public void setAdDomain(String adDomain) {
        this.adDomain = adDomain;
    }

    public String getAdBase() {
        return adBase;
    }

    public void setAdBase(String adBase) {
        this.adBase = adBase;
    }

    public String getAdIdcodeField() {
        return adIdcodeField;
    }

    public void setAdIdcodeField(String adIdcodeField) {
        this.adIdcodeField = adIdcodeField;
    }

    public String getNameGenitiveEt() {
        return nameGenitiveEt;
    }

    public void setNameGenitiveEt(String nameGenitiveEt) {
        this.nameGenitiveEt = nameGenitiveEt;
    }

    public Boolean getIsMinorStudentAbsence() {
        return isMinorStudentAbsence;
    }

    public void setIsMinorStudentAbsence(Boolean isMinorStudentAbsence) {
        this.isMinorStudentAbsence = isMinorStudentAbsence;
    }

    public Boolean getIsLetterGrade() {
        return isLetterGrade;
    }

    public void setIsLetterGrade(Boolean isLetterGrade) {
        this.isLetterGrade = isLetterGrade;
    }

    public String getFinalSchoolType() {
        return finalSchoolType;
    }

    public void setFinalSchoolType(String finalSchoolType) {
        this.finalSchoolType = finalSchoolType;
    }

    public String getFinal62() {
        return final62;
    }

    public void setFinal62(String final62) {
        this.final62 = final62;
    }

    public String getFinalSchoolTypeEn() {
        return finalSchoolTypeEn;
    }

    public void setFinalSchoolTypeEn(String finalSchoolTypeEn) {
        this.finalSchoolTypeEn = finalSchoolTypeEn;
    }

    public String getFinalEn62() {
        return finalEn62;
    }

    public void setFinalEn62(String finalEn62) {
        this.finalEn62 = finalEn62;
    }

}
