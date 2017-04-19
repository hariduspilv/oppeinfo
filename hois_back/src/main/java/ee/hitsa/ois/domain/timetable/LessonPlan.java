package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.domain.curriculum.CurriculumVersion;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.student.StudentGroup;

@Entity
public class LessonPlan extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudyYear studyYear;
    private boolean isUsable;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private School school;
    private Boolean showWeeks;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StudentGroup studentGroup;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersion curriculumVersion;

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean isUsable) {
        this.isUsable = isUsable;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Boolean getShowWeeks() {
        return showWeeks;
    }

    public void setShowWeeks(Boolean showWeeks) {
        this.showWeeks = showWeeks;
    }

    public StudentGroup getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public CurriculumVersion getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(CurriculumVersion curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }
}
