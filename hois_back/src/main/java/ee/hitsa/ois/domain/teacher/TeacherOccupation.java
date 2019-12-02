package ee.hitsa.ois.domain.teacher;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.UserSchoolRole;
import ee.hitsa.ois.domain.school.School;

@Entity
public class TeacherOccupation extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    private String occupationEt;
    private String occupationEn;
    private Boolean isValid;
    
    @OneToOne(mappedBy = "teacherOccupation", fetch = FetchType.LAZY, orphanRemoval = true)
    private UserSchoolRole userSchoolRole;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacherOccupation")
    private Set<Teacher> teachers;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getOccupationEt() {
        return occupationEt;
    }

    public void setOccupationEt(String occupationEt) {
        this.occupationEt = occupationEt;
    }

    public String getOccupationEn() {
        return occupationEn;
    }

    public void setOccupationEn(String occupationEn) {
        this.occupationEn = occupationEn;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public UserSchoolRole getUserSchoolRole() {
        return userSchoolRole;
    }

    public void setUserSchoolRole(UserSchoolRole userSchoolRole) {
        this.userSchoolRole = userSchoolRole;
    }

    public Set<Teacher> getTeachers() {
        return teachers != null ? teachers : (teachers = new HashSet<>());
    }

    public void setTeachers(Set<Teacher> teachers) {
        getTeachers().clear();
        getTeachers().addAll(teachers);
    }
}
