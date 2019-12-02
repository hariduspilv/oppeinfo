package ee.hitsa.ois.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
@Table(name = "user_")
public class User extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Classifier role;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
    @OneToOne(fetch = FetchType.LAZY)
    private Teacher teacher;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserSchoolRole userSchoolRole;

    private LocalDate validFrom;
    private LocalDate validThru;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRights> userRights;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCurriculum> userCurriculums;

    public Classifier getRole() {
        return role;
    }

    public void setRole(Classifier role) {
        this.role = role;
    }

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public UserSchoolRole getUserSchoolRole() {
        return userSchoolRole;
    }

    public void setUserSchoolRole(UserSchoolRole userSchoolRole) {
        this.userSchoolRole = userSchoolRole;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public List<UserRights> getUserRights() {
        return userRights == null ? (userRights = new ArrayList<>()) : userRights;
    }

    public void setUserRights(List<UserRights> userRights) {
        this.userRights = userRights;
    }

    public Set<UserCurriculum> getUserCurriculums() {
        return userCurriculums == null ? (userCurriculums = new HashSet<>()) : userCurriculums;
    }

    public void setUserCurriculums(Set<UserCurriculum> userCurriculums) {
        getUserCurriculums().clear();
        getUserCurriculums().addAll(userCurriculums);
    }
}
