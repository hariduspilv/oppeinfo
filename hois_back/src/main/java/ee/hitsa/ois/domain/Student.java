package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ee.hitsa.ois.domain.curriculum.CurriculumSpeciality;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;

@Entity
public class Student extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Person person;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_form_code")
    private Classifier studyForm;
    @ManyToOne(fetch = FetchType.LAZY)
    private StudentGroup studentGroup;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_code")
    private Classifier language;
    private String email;
    private Boolean isSpecialNeed;
    private Boolean isRepresentativeMandatory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "special_need_code")
    private Classifier specialNeed;
    private String studentCard;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_study_level_code")
    private Classifier previousStudyLevel;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code")
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
    @JoinColumn(name = "study_load_code")
    private Classifier studyLoad;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fin_code")
    private Classifier fin;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fin_specific_code")
    private Classifier finSpecific;
    private String studyCompany;
    private String boardingSchool;
    @OneToMany(mappedBy = "student")
    private List<StudentRepresentative> representatives;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

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

    public List<StudentRepresentative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<StudentRepresentative> representatives) {
        this.representatives = representatives;
    }
}
