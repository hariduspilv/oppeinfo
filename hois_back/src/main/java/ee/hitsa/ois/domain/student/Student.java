package ee.hitsa.ois.domain.student;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ee.hitsa.ois.domain.Dormitory;
import ee.hitsa.ois.domain.FinalThesis;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.timetable.JournalStudent;

@Entity
public class Student extends StudentBase {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Person person;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private School school;
    private String previousSchoolName;
    private LocalDate previousSchoolEndDate;
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private StudentHistory studentHistory;
    @OneToMany(mappedBy = "student")
    private List<StudentRepresentative> representatives;
    @OneToMany(mappedBy = "student")
    private List<JournalStudent> journalStudents;
    @OneToMany(mappedBy = "student")
    private List<StudentOccupationCertificate> occupationCertificates;
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private FinalThesis finalThesis;
    @OneToMany(mappedBy = "student")
    private List<PracticeJournal> practiceJournals;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dormitory> boardingSchools;

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

    public StudentHistory getStudentHistory() {
        return studentHistory;
    }

    public void setStudentHistory(StudentHistory studentHistory) {
        this.studentHistory = studentHistory;
    }

    public List<StudentRepresentative> getRepresentatives() {
        return representatives;
    }

    public void setRepresentatives(List<StudentRepresentative> representatives) {
        this.representatives = representatives;
    }

    public List<JournalStudent> getJournalStudents() {
        return journalStudents;
    }

    public void setJournalStudents(List<JournalStudent> journalStudents) {
        this.journalStudents = journalStudents;
    }

    public List<StudentOccupationCertificate> getOccupationCertificates() {
        return occupationCertificates;
    }

    public void setOccupationCertificates(List<StudentOccupationCertificate> occupationCertificates) {
        this.occupationCertificates = occupationCertificates;
    }

    public FinalThesis getFinalThesis() {
        return finalThesis;
    }

    public void setFinalThesis(FinalThesis finalThesis) {
        this.finalThesis = finalThesis;
    }

    public List<PracticeJournal> getPracticeJournals() {
        return practiceJournals;
    }

    public void setPracticeJournals(List<PracticeJournal> practiceJournals) {
        this.practiceJournals = practiceJournals;
    }

    public List<Dormitory> getBoardingSchools() {
        return boardingSchools;
    }

    public void setBoardingSchools(List<Dormitory> boardingSchools) {
        this.boardingSchools = boardingSchools;
    }
}
