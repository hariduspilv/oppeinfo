package ee.hitsa.ois.web.commandobject.student;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.Conditional;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@Conditional(selected = "isGuestStudent", values = {"false", "null"}, required = {"previousStudyLevel"})
public class StudentForm extends VersionedCommand {

    // contact data
    @Valid
    @NotNull
    private StudentPersonForm person;

    // study
    @ClassifierRestriction(MainClassCode.OPPEKEEL)
    private String studyLanguage;
    private AutocompleteResult curriculumSpeciality;
    private LocalDate nominalStudyEnd;
    @ClassifierRestriction(MainClassCode.FINTAPSUSTUS)
    private String finSpecific;
    @Size(max = 255)
    private String schoolEmail;
    @ClassifierRestriction(MainClassCode.OPPEASTE)
    private String previousStudyLevel;
    private String studyCompany;
    private String previousSchoolName;
    private LocalDate previousSchoolEndDate;
    @ClassifierRestriction(MainClassCode.YHISELAMU)
    private String dormitory;
    private Boolean isGuestStudent;
    // special needs
    private Boolean isSpecialNeed;
    @ClassifierRestriction(MainClassCode.ERIVAJADUS)
    private String specialNeed;
    private List<String> specialNeeds;
    private Boolean isRepresentativeMandatory;
    @Valid
    private OisFileCommand photo;
    private Boolean deleteCurrentPhoto;

    public StudentPersonForm getPerson() {
        return person;
    }

    public void setPerson(StudentPersonForm person) {
        this.person = person;
    }

    public String getStudyLanguage() {
        return studyLanguage;
    }

    public void setStudyLanguage(String studyLanguage) {
        this.studyLanguage = studyLanguage;
    }

    public AutocompleteResult getCurriculumSpeciality() {
        return curriculumSpeciality;
    }

    public void setCurriculumSpeciality(AutocompleteResult curriculumSpeciality) {
        this.curriculumSpeciality = curriculumSpeciality;
    }

    public LocalDate getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public void setNominalStudyEnd(LocalDate nominalStudyEnd) {
        this.nominalStudyEnd = nominalStudyEnd;
    }

    public String getFinSpecific() {
        return finSpecific;
    }

    public void setFinSpecific(String finSpecific) {
        this.finSpecific = finSpecific;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getPreviousStudyLevel() {
        return previousStudyLevel;
    }

    public void setPreviousStudyLevel(String previousStudyLevel) {
        this.previousStudyLevel = previousStudyLevel;
    }

    public String getStudyCompany() {
        return studyCompany;
    }

    public void setStudyCompany(String studyCompany) {
        this.studyCompany = studyCompany;
    }

    public String getPreviousSchoolName() {
        return previousSchoolName;
    }

    public void setPreviousSchoolName(String previousSchoolName) {
        this.previousSchoolName = previousSchoolName;
    }

    public LocalDate getPreviousSchoolEndDate() {
        return previousSchoolEndDate;
    }

    public void setPreviousSchoolEndDate(LocalDate previousSchoolEndDate) {
        this.previousSchoolEndDate = previousSchoolEndDate;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public Boolean getIsSpecialNeed() {
        return isSpecialNeed;
    }

    public void setIsSpecialNeed(Boolean isSpecialNeed) {
        this.isSpecialNeed = isSpecialNeed;
    }

    public String getSpecialNeed() {
        return specialNeed;
    }

    public void setSpecialNeed(String specialNeed) {
        this.specialNeed = specialNeed;
    }

    public List<String> getSpecialNeeds() {
        return specialNeeds;
    }

    public void setSpecialNeeds(List<String> specialNeeds) {
        this.specialNeeds = specialNeeds;
    }

    public Boolean getIsRepresentativeMandatory() {
        return isRepresentativeMandatory;
    }

    public void setIsRepresentativeMandatory(Boolean isRepresentativeMandatory) {
        this.isRepresentativeMandatory = isRepresentativeMandatory;
    }
    
    public OisFileCommand getPhoto() {
        return photo;
    }

    public void setPhoto(OisFileCommand photo) {
        this.photo = photo;
    }

    public Boolean getDeleteCurrentPhoto() {
        return deleteCurrentPhoto;
    }

    public void setDeleteCurrentPhoto(Boolean deleteCurrentPhoto) {
        this.deleteCurrentPhoto = deleteCurrentPhoto;
    }



    public static class StudentPersonForm {

        @Required
        @Size(max = 255)
        private String email;
        @Required
        @Size(max = 100)
        private String phone;
        @Required
        @ClassifierRestriction(MainClassCode.RIIK)
        private String residenceCountry;
        @ClassifierRestriction(MainClassCode.RIIK)
        private String citizenship;
        @ClassifierRestriction(MainClassCode.OPPEKEEL)
        private String language;
        private String bankaccount;
        //@Required // TEMPORARY REMOVED
        @Size(max = 255)
        private String address;
        @Size(max = 20)
        private String postcode;
        @Size(max = 50)
        private String addressAds;
        @Size(max = 50)
        private String addressAdsOid;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getBankaccount() {
            return bankaccount;
        }

        public void setBankaccount(String bankaccount) {
            this.bankaccount = bankaccount;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
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
        
    }



    public Boolean getIsGuestStudent() {
        return isGuestStudent;
    }

    public void setIsGuestStudent(Boolean isGuestStudent) {
        this.isGuestStudent = isGuestStudent;
    }
}
