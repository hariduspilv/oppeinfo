package ee.hitsa.ois.domain.teacher;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Person person;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;

    private String email;
    private String phone;
    private Boolean isVocational;
    private Boolean isHigher;
    private Short scheduleLoad;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TeacherOccupation teacherOccupation;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherPositionEhis> teacherPositionEhis;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherMobility> teacherMobility;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherQualification> teacherQualification;

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

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean vocational) {
        isVocational = vocational;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean higher) {
        isHigher = higher;
    }

    public Short getScheduleLoad() {
        return scheduleLoad;
    }

    public void setScheduleLoad(Short scheduleLoad) {
        this.scheduleLoad = scheduleLoad;
    }

    public TeacherOccupation getTeacherOccupation() {
        return teacherOccupation;
    }

    public void setTeacherOccupation(TeacherOccupation teacherOccupation) {
        this.teacherOccupation = teacherOccupation;
    }

    public Set<TeacherPositionEhis> getTeacherPositionEhis() {
        return teacherPositionEhis != null ? teacherPositionEhis : (teacherPositionEhis = new HashSet<>()
        );
    }

    public void setTeacherPositionEhis(Set<TeacherPositionEhis> teacherPositionEhis) {
        this.teacherPositionEhis = teacherPositionEhis;
    }

    public Set<TeacherMobility> getTeacherMobility() {
        return teacherMobility != null ? teacherMobility : (teacherMobility = new HashSet<>());
    }

    public void setTeacherMobility(Set<TeacherMobility> teacherMobility) {
        this.teacherMobility = teacherMobility;
    }

    public Set<TeacherQualification> getTeacherQualification() {
        return teacherQualification != null ? teacherQualification : (teacherQualification = new HashSet<>());
    }

    public void setTeacherQualification(Set<TeacherQualification> teacherQualification) {
        this.teacherQualification = teacherQualification;
    }
}
