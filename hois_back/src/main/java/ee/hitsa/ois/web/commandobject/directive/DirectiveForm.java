package ee.hitsa.ois.web.commandobject.directive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.validation.DirectiveValidation.Immat;
import ee.hitsa.ois.validation.EstonianIdCode;
import ee.hitsa.ois.validation.Required;
import ee.hitsa.ois.validation.StudyPeriodRange;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class DirectiveForm extends VersionedCommand {

    @Required
    @ClassifierRestriction(MainClassCode.KASKKIRI)
    private String type;
    private Boolean isHigher;
    @Required
    @Size(max = 500)
    private String headline;
    @Size(max = 4000)
    private String addInfo;
    private Long directiveCoordinator;
    private Long canceledDirective;
    @ClassifierRestriction(MainClassCode.KASKKIRI_TYHISTAMISE_VIIS)
    private String cancelType;
    @ClassifierRestriction(MainClassCode.STIPTOETUS)
    private String scholarshipType;
    @Valid
    private List<? extends DirectiveFormStudent> students;
    private List<Long> selectedStudents;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public Long getDirectiveCoordinator() {
        return directiveCoordinator;
    }

    public void setDirectiveCoordinator(Long directiveCoordinator) {
        this.directiveCoordinator = directiveCoordinator;
    }

    public Long getCanceledDirective() {
        return canceledDirective;
    }

    public void setCanceledDirective(Long canceledDirective) {
        this.canceledDirective = canceledDirective;
    }

    public String getCancelType() {
        return cancelType;
    }

    public void setCancelType(String cancelType) {
        this.cancelType = cancelType;
    }

    public String getScholarshipType() {
        return scholarshipType;
    }

    public void setScholarshipType(String scholarshipType) {
        this.scholarshipType = scholarshipType;
    }

    public List<? extends DirectiveFormStudent> getStudents() {
        return students;
    }

    public void setStudents(List<? extends DirectiveFormStudent> students) {
        this.students = students;
    }

    public List<Long> getSelectedStudents() {
        return selectedStudents;
    }

    public void setSelectedStudents(List<Long> selectedStudents) {
        this.selectedStudents = selectedStudents;
    }

    @DateRange(from = "startDate", thru = "endDate")
    @StudyPeriodRange(from = "studyPeriodStart", thru = "studyPeriodEnd")
    public static class DirectiveFormStudent {
        private Long id;
        @EstonianIdCode(groups = Immat.class)
        private String idcode;
        private String foreignIdcode;
        @Required(groups = Immat.class)
        @Size(max = 100, groups = Immat.class, message = "maxlength")
        private String firstname;
        @Required(groups = Immat.class)
        @Size(max = 100, groups = Immat.class, message = "maxlength")
        private String lastname;
        @Required(groups = Immat.class)
        private LocalDate birthdate;
        @ClassifierRestriction(MainClassCode.SUGU)
        private String sex;
        @ClassifierRestriction(MainClassCode.RIIK)
        private String citizenship;
        private LocalDate startDate;
        private LocalDate endDate;
        @ClassifierRestriction({MainClassCode.AKADPUHKUS_POHJUS, MainClassCode.EKSMAT_POHJUS, MainClassCode.KASKKIRI_STIPTOETL_POHJUS})
        private String reason;
        @ClassifierRestriction(MainClassCode.OPPEVORM)
        private String studyForm;
        private Long studentGroup;
        private Long curriculumVersion;
        @ClassifierRestriction(MainClassCode.OPPEKOORMUS)
        private String studyLoad;
        @ClassifierRestriction(MainClassCode.FINALLIKAS)
        private String fin;
        @ClassifierRestriction(MainClassCode.FINTAPSUSTUS)
        private String finSpecific;
        @ClassifierRestriction(MainClassCode.OPPEKEEL)
        private String language;
        @ClassifierRestriction(MainClassCode.OPPEASTE)
        private String previousStudyLevel;
        private String email;
        private Boolean isPeriod;
        private Long studyPeriodStart;
        private Long studyPeriodEnd;
        private LocalDate nominalStudyEnd;
        private Boolean isAbroad;
        @ClassifierRestriction(MainClassCode.EHIS_KOOL)
        private String ehisSchool;
        private String abroadSchool;
        @ClassifierRestriction(MainClassCode.RIIK)
        private String country;
        @ClassifierRestriction(MainClassCode.VALISOPE_EESMARK)
        private String abroadPurpose;
        @ClassifierRestriction(MainClassCode.VALISKOOL_PROGRAMM)
        private String abroadProgramme;
        @Min(0)
        private BigDecimal amountPaid;
        private Long application;
        private Long student;
        private Long saisApplication;
        private Long scholarshipApplication;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getIdcode() {
            return idcode;
        }

        public void setIdcode(String idcode) {
            this.idcode = idcode;
        }

        public String getForeignIdcode() {
            return foreignIdcode;
        }

        public void setForeignIdcode(String foreignIdcode) {
            this.foreignIdcode = foreignIdcode;
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

        public LocalDate getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCitizenship() {
            return citizenship;
        }

        public void setCitizenship(String citizenship) {
            this.citizenship = citizenship;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getStudyForm() {
            return studyForm;
        }

        public void setStudyForm(String studyForm) {
            this.studyForm = studyForm;
        }

        public Long getStudentGroup() {
            return studentGroup;
        }

        public void setStudentGroup(Long studentGroup) {
            this.studentGroup = studentGroup;
        }

        public Long getCurriculumVersion() {
            return curriculumVersion;
        }

        public void setCurriculumVersion(Long curriculumVersion) {
            this.curriculumVersion = curriculumVersion;
        }

        public String getStudyLoad() {
            return studyLoad;
        }

        public void setStudyLoad(String studyLoad) {
            this.studyLoad = studyLoad;
        }

        public String getFin() {
            return fin;
        }

        public void setFin(String fin) {
            this.fin = fin;
        }

        public String getFinSpecific() {
            return finSpecific;
        }

        public void setFinSpecific(String finSpecific) {
            this.finSpecific = finSpecific;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getPreviousStudyLevel() {
            return previousStudyLevel;
        }

        public void setPreviousStudyLevel(String previousStudyLevel) {
            this.previousStudyLevel = previousStudyLevel;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Boolean getIsPeriod() {
            return isPeriod;
        }

        public void setIsPeriod(Boolean isPeriod) {
            this.isPeriod = isPeriod;
        }

        public Long getStudyPeriodStart() {
            return studyPeriodStart;
        }

        public void setStudyPeriodStart(Long studyPeriodStart) {
            this.studyPeriodStart = studyPeriodStart;
        }

        public Long getStudyPeriodEnd() {
            return studyPeriodEnd;
        }

        public void setStudyPeriodEnd(Long studyPeriodEnd) {
            this.studyPeriodEnd = studyPeriodEnd;
        }

        public LocalDate getNominalStudyEnd() {
            return nominalStudyEnd;
        }

        public void setNominalStudyEnd(LocalDate nominalStudyEnd) {
            this.nominalStudyEnd = nominalStudyEnd;
        }

        public Boolean getIsAbroad() {
            return isAbroad;
        }

        public void setIsAbroad(Boolean isAbroad) {
            this.isAbroad = isAbroad;
        }

        public String getEhisSchool() {
            return ehisSchool;
        }

        public void setEhisSchool(String ehisSchool) {
            this.ehisSchool = ehisSchool;
        }

        public String getAbroadSchool() {
            return abroadSchool;
        }

        public void setAbroadSchool(String abroadSchool) {
            this.abroadSchool = abroadSchool;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getAbroadPurpose() {
            return abroadPurpose;
        }

        public void setAbroadPurpose(String abroadPurpose) {
            this.abroadPurpose = abroadPurpose;
        }

        public String getAbroadProgramme() {
            return abroadProgramme;
        }

        public void setAbroadProgramme(String abroadProgramme) {
            this.abroadProgramme = abroadProgramme;
        }

        public BigDecimal getAmountPaid() {
            return amountPaid;
        }

        public void setAmountPaid(BigDecimal amountPaid) {
            this.amountPaid = amountPaid;
        }

        public Long getApplication() {
            return application;
        }

        public void setApplication(Long application) {
            this.application = application;
        }

        public Long getStudent() {
            return student;
        }

        public void setStudent(Long student) {
            this.student = student;
        }

        public Long getSaisApplication() {
            return saisApplication;
        }

        public void setSaisApplication(Long saisApplication) {
            this.saisApplication = saisApplication;
        }

        public Long getScholarshipApplication() {
            return scholarshipApplication;
        }

        public void setScholarshipApplication(Long scholarshipApplication) {
            this.scholarshipApplication = scholarshipApplication;
        }
    }
}
