package ee.hitsa.ois.domain.studentgroupyeartransfer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.Curriculum;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.student.StudentGroup;

@Entity
public class StudentGroupYearTransfer extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudyYear studyYear;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private StudentGroup studentGroup;
    @Column(nullable = false, updatable = false)
    private String oldCode;
    @Column(nullable = false)
    private String newCode;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Curriculum curriculum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false)
    private CurriculumVersion curriculumVersion;
    @Column(nullable = false, updatable = false)
    private Short oldCourse;
    @Column(nullable = false, updatable = false)
    private Short newCourse;
    @Column(nullable = false)
    private Boolean isTransfered;
    
    public StudyYear getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
    
    public String getOldCode() {
        return oldCode;
    }
    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }
    
    public String getNewCode() {
        return newCode;
    }
    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }
    
    public Curriculum getCurriculum() {
        return curriculum;
    }
    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }
    
    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }
    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
    
    public Short getOldCourse() {
        return oldCourse;
    }
    public void setOldCourse(Short oldCourse) {
        this.oldCourse = oldCourse;
    }
    
    public Short getNewCourse() {
        return newCourse;
    }
    public void setNewCourse(Short newCourse) {
        this.newCourse = newCourse;
    }
    
    public Boolean getIsTransfered() {
        return isTransfered;
    }
    public void setIsTransfered(Boolean isTransfered) {
        this.isTransfered = isTransfered;
    }
    
}
