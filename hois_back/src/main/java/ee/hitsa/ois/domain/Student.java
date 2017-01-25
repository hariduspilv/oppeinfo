package ee.hitsa.ois.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.curriculum.CurriculumVersion;

@Entity
public class Student extends BaseEntityWithId {

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Person person;
    @ManyToOne(optional = false)
    private School school;
    @ManyToOne(optional = false)
    private CurriculumVersion curriculumVersion;
    @ManyToOne
    @JoinColumn(name = "study_form_code")
    private Classifier studyForm;
    @ManyToOne
    private StudentGroup studentGroup;
    @ManyToOne
    @JoinColumn(name = "language_code")
    private Classifier language;
    private String email;
    private Boolean isSpecialNeed;
    private Boolean isRepresentativeMandatory;
    @ManyToOne
    @JoinColumn(name = "special_need_code")
    private Classifier specialNeed;
    @ManyToOne
    @JoinColumn(name = "previous_study_level_code")
    private Classifier previousStudyLevel;
    @ManyToOne
    @JoinColumn(name = "status_code")
    private Classifier status;

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
}
