package ee.hitsa.ois.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.teacher.TeacherOccupation;

@Entity
public class UserSchoolRole extends BaseEntityWithId {

    @ManyToOne(fetch = FetchType.LAZY)
    private School school;
    private String nameEt;
    private String nameEn;
    @OneToOne(fetch = FetchType.LAZY)
    private TeacherOccupation teacherOccupation;
    @OneToMany(mappedBy = "userSchoolRole", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserSchoolRoleRights> rights;
    @OneToMany(mappedBy = "userSchoolRole")
    private Set<User> users;

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public TeacherOccupation getTeacherOccupation() {
        return teacherOccupation;
    }

    public void setTeacherOccupation(TeacherOccupation teacherOccupation) {
        this.teacherOccupation = teacherOccupation;
    }

    public Set<UserSchoolRoleRights> getRights() {
        return rights != null ? rights : (rights = new HashSet<>());
    }

    public void setRights(Set<UserSchoolRoleRights> rights) {
        getRights().clear();
        getRights().addAll(rights);
    }

    public Set<User> getUsers() {
        return users != null ? users : (users = new HashSet<>());
    }

    public void setUsers(Set<User> users) {
        getUsers().clear();
        getUsers().addAll(users);
    }
    
}
