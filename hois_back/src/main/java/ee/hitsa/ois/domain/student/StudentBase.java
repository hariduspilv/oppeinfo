package ee.hitsa.ois.domain.student;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;

@MappedSuperclass
public class StudentBase extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyForm;
    @ManyToOne(fetch = FetchType.LAZY)
    private StudentGroup studentGroup;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier language;
    private String email;
    private Boolean isSpecialNeed;
    private Boolean isRepresentativeMandatory;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier specialNeed;
    private String studentCard;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier previousStudyLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier status;
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "ois_file_id")
    private OisFile photo;
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumSpeciality curriculumSpeciality;
    private LocalDate studyStart;
    private LocalDate studyEnd;
    private LocalDate nominalStudyEnd;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studyLoad;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier fin;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier finSpecific;
    private String studyCompany;
    private String boardingSchool;
    private Boolean isStudentCardRepetitive;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier studentCardStatus;
    private LocalDate studentCardValidThru;
    private Boolean isStudentCardGiven;
    private LocalDate studentCardGivenDt;
    private Boolean isStudentCardReturned;
    private LocalDate studentCardReturnedDt;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier dormitory;

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public Classifier getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Classifier getLanguage() {
        return language;
    }

    public void setLanguage(Classifier language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsSpecialNeed() {
        return isSpecialNeed;
    }

    public void setIsSpecialNeed(Boolean isSpecialNeed) {
        this.isSpecialNeed = isSpecialNeed;
    }

    public Boolean getIsRepresentativeMandatory() {
        return isRepresentativeMandatory;
    }

    public void setIsRepresentativeMandatory(Boolean isRepresentativeMandatory) {
        this.isRepresentativeMandatory = isRepresentativeMandatory;
    }

    public Classifier getSpecialNeed() {
        return specialNeed;
    }

    public void setSpecialNeed(Classifier specialNeed) {
        this.specialNeed = specialNeed;
    }

    public String getStudentCard() {
        return studentCard;
    }

    public void setStudentCard(String studentCard) {
        this.studentCard = studentCard;
    }

    public Classifier getPreviousStudyLevel() {
        return previousStudyLevel;
    }

    public void setPreviousStudyLevel(Classifier previousStudyLevel) {
        this.previousStudyLevel = previousStudyLevel;
    }

    public Classifier getStatus() {
        return status;
    }

    public void setStatus(Classifier status) {
        this.status = status;
    }

    public OisFile getPhoto() {
        return photo;
    }

    public void setPhoto(OisFile photo) {
        this.photo = photo;
    }

    public CurriculumSpeciality getCurriculumSpeciality() {
        return curriculumSpeciality;
    }

    public void setCurriculumSpeciality(CurriculumSpeciality curriculumSpeciality) {
        this.curriculumSpeciality = curriculumSpeciality;
    }

    public LocalDate getStudyStart() {
        return studyStart;
    }

    public void setStudyStart(LocalDate studyStart) {
        this.studyStart = studyStart;
    }

    public LocalDate getStudyEnd() {
        return studyEnd;
    }

    public void setStudyEnd(LocalDate studyEnd) {
        this.studyEnd = studyEnd;
    }

    public LocalDate getNominalStudyEnd() {
        return nominalStudyEnd;
    }

    public void setNominalStudyEnd(LocalDate nominalStudyEnd) {
        this.nominalStudyEnd = nominalStudyEnd;
    }

    public Classifier getStudyLoad() {
        return studyLoad;
    }

    public void setStudyLoad(Classifier studyLoad) {
        this.studyLoad = studyLoad;
    }

    public Classifier getFin() {
        return fin;
    }

    public void setFin(Classifier fin) {
        this.fin = fin;
    }

    public Classifier getFinSpecific() {
        return finSpecific;
    }

    public void setFinSpecific(Classifier finSpecific) {
        this.finSpecific = finSpecific;
    }

    public String getStudyCompany() {
        return studyCompany;
    }

    public void setStudyCompany(String studyCompany) {
        this.studyCompany = studyCompany;
    }

    public String getBoardingSchool() {
        return boardingSchool;
    }

    public void setBoardingSchool(String boardingSchool) {
        this.boardingSchool = boardingSchool;
    }

    public Boolean getIsStudentCardRepetitive() {
        return isStudentCardRepetitive;
    }

    public void setIsStudentCardRepetitive(Boolean isStudentCardRepetitive) {
        this.isStudentCardRepetitive = isStudentCardRepetitive;
    }

    public Classifier getStudentCardStatus() {
        return studentCardStatus;
    }

    public void setStudentCardStatus(Classifier studentCardStatus) {
        this.studentCardStatus = studentCardStatus;
    }

    public LocalDate getStudentCardValidThru() {
        return studentCardValidThru;
    }

    public void setStudentCardValidThru(LocalDate studentCardValidThru) {
        this.studentCardValidThru = studentCardValidThru;
    }

    public Boolean getIsStudentCardGiven() {
        return isStudentCardGiven;
    }

    public void setIsStudentCardGiven(Boolean isStudentCardGiven) {
        this.isStudentCardGiven = isStudentCardGiven;
    }

    public LocalDate getStudentCardGivenDt() {
        return studentCardGivenDt;
    }

    public void setStudentCardGivenDt(LocalDate studentCardGivenDt) {
        this.studentCardGivenDt = studentCardGivenDt;
    }

    public Boolean getIsStudentCardReturned() {
        return isStudentCardReturned;
    }

    public void setIsStudentCardReturned(Boolean isStudentCardReturned) {
        this.isStudentCardReturned = isStudentCardReturned;
    }

    public LocalDate getStudentCardReturnedDt() {
        return studentCardReturnedDt;
    }

    public void setStudentCardReturnedDt(LocalDate studentCardReturnedDt) {
        this.studentCardReturnedDt = studentCardReturnedDt;
    }

    public Classifier getDormitory() {
        return dormitory;
    }

    public void setDormitory(Classifier dormitory) {
        this.dormitory = dormitory;
    }

}
