package ee.hitsa.ois.domain.student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.Teacher;
import ee.hitsa.ois.domain.timetable.SubjectStudyPeriodStudentGroup;

@Entity
public class StudentGroup extends BaseEntityWithId {

    private String code;
    private Short course;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Curriculum curriculum;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Classifier studyForm;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier language;
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classifier speciality;
    private Integer places;
    @OneToMany(mappedBy = "studentGroup")
    private List<Student> students;
    private LocalDate validFrom;
    private LocalDate validThru;
    private Boolean isGuest;
    @OneToMany(mappedBy = "studentGroup", fetch = FetchType.LAZY)
    private List<SubjectStudyPeriodStudentGroup> subjectStudyPeriods;

    public List<SubjectStudyPeriodStudentGroup> getSubjectStudyPeriods() {
        return subjectStudyPeriods;
    }

    public void setSubjectStudyPeriods(List<SubjectStudyPeriodStudentGroup> subjectStudyPeriods) {
        this.subjectStudyPeriods = subjectStudyPeriods;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Short getCourse() {
        return course;
    }

    public void setCourse(Short course) {
        this.course = course;
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

    public Curriculum getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public Classifier getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(Classifier studyForm) {
        this.studyForm = studyForm;
    }

    public Classifier getLanguage() {
        return language;
    }

    public void setLanguage(Classifier language) {
        this.language = language;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Classifier getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Classifier speciality) {
        this.speciality = speciality;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public List<Student> getStudents() {
        return students != null ? students : (students = new ArrayList<>());
    }

    public void setStudents(List<Student> students) {
        getStudents().clear();
        getStudents().addAll(students);
    }

    public Boolean getIsGuest() {
        return isGuest;
    }

    public void setIsGuest(Boolean isGuest) {
        this.isGuest = isGuest;
    }
}
