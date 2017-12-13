package ee.hitsa.ois.domain.teacher;

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

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Person;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;

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

    private Boolean isStudyPeriodScheduleLoad;
    private Boolean isActive;

    private String rtipNr;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TeacherOccupation teacherOccupation;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeacherAbsence> teacherAbsence;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherPositionEhis> teacherPositionEhis;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherMobility> teacherMobility;
    
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeacherContinuingEducation> teacherContinuingEducation;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeacherQualification> teacherQualification;
    
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<SubjectStudyPeriodTeacher> subjectStudyPeriods;

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

    public Boolean getIsStudyPeriodScheduleLoad() {
        return isStudyPeriodScheduleLoad;
    }

    public void setIsStudyPeriodScheduleLoad(Boolean studyPeriodScheduleLoad) {
        isStudyPeriodScheduleLoad = studyPeriodScheduleLoad;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getRtipNr() {
        return rtipNr;
    }

    public void setRtipNr(String rtipNr) {
        this.rtipNr = rtipNr;
    }

    public TeacherOccupation getTeacherOccupation() {
        return teacherOccupation;
    }

    public void setTeacherOccupation(TeacherOccupation teacherOccupation) {
        this.teacherOccupation = teacherOccupation;
    }

    public List<TeacherAbsence> getTeacherAbsence() {
        return teacherAbsence != null ? teacherAbsence : (teacherAbsence = new ArrayList<>());
    }

    public void setTeacherAbsence(List<TeacherAbsence> teacherAbsence) {
        this.teacherAbsence = teacherAbsence;
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
    
    public List<TeacherContinuingEducation> getTeacherContinuingEducation() {
        return teacherContinuingEducation != null ? teacherContinuingEducation : (teacherContinuingEducation = new ArrayList<>());
    }

    public void setTeacherContinuingEducation(List<TeacherContinuingEducation> teacherContinuingEducation) {
        this.teacherContinuingEducation = teacherContinuingEducation;
    }
    
    public Set<TeacherQualification> getTeacherQualification() {
        return teacherQualification != null ? teacherQualification : (teacherQualification = new HashSet<>());
    }

    public void setTeacherQualification(Set<TeacherQualification> teacherQualification) {
        this.teacherQualification = teacherQualification;
    }

    public List<SubjectStudyPeriodTeacher> getSubjectStudyPeriods() {
        return subjectStudyPeriods;
    }

    public void setSubjectStudyPeriods(List<SubjectStudyPeriodTeacher> subjectStudyPeriods) {
        this.subjectStudyPeriods = subjectStudyPeriods;
    }
}
