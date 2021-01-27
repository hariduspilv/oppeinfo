package ee.hitsa.ois.domain.subject.subjectprogram;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class SubjectProgramStudyContent extends BaseEntityWithId {
    
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    private SubjectProgram subjectProgram;
    private String weekNr;
    private LocalDate studyDt;
    @Column(nullable=false)
    private String studyInfo;
    private String capacity;
    @ManyToOne(fetch=FetchType.LAZY)
    private Teacher teacher;
    @ManyToOne(fetch=FetchType.LAZY)
    private Classifier capacityType;
    private Long orderNr;

    @OneToMany(mappedBy = "studyContent", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SubjectProgramStudyContentTeacher> teachers;

    public SubjectProgram getSubjectProgram() {
        return subjectProgram;
    }

    public void setSubjectProgram(SubjectProgram subjectProgram) {
        this.subjectProgram = subjectProgram;
    }

    public String getWeekNr() {
        return weekNr;
    }

    public void setWeekNr(String weekNr) {
        this.weekNr = weekNr;
    }

    public LocalDate getStudyDt() {
        return studyDt;
    }

    public void setStudyDt(LocalDate studyDt) {
        this.studyDt = studyDt;
    }

    public String getStudyInfo() {
        return studyInfo;
    }

    public void setStudyInfo(String studyInfo) {
        this.studyInfo = studyInfo;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
    }

    public Classifier getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(Classifier capacityType) {
        this.capacityType = capacityType;
    }

    public List<SubjectProgramStudyContentTeacher> getTeachers() {
        return teachers != null ? teachers : (teachers = new ArrayList<>());
    }

    public void setTeachers(List<SubjectProgramStudyContentTeacher> teachers) {
        getTeachers().clear();
        getTeachers().addAll(teachers);
    }
}
