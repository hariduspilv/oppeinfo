package ee.hitsa.ois.domain.timetable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.teacher.Teacher;

@Entity
public class LessonPlanModule extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private LessonPlan lessonPlan;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_id", nullable = false, updatable = false)
    private CurriculumVersionOccupationModule curriculumVersionOccupationModule;
    @ManyToOne(fetch = FetchType.LAZY)
    private Teacher teacher;
    @OneToMany(mappedBy = "lessonPlanModule")
    private List<JournalOccupationModuleTheme> journalOccupationModuleThemes;

    public LessonPlan getLessonPlan() {
        return lessonPlan;
    }

    public void setLessonPlan(LessonPlan lessonPlan) {
        this.lessonPlan = lessonPlan;
    }

    public CurriculumVersionOccupationModule getCurriculumVersionOccupationModule() {
        return curriculumVersionOccupationModule;
    }

    public void setCurriculumVersionOccupationModule(CurriculumVersionOccupationModule module) {
        this.curriculumVersionOccupationModule = module;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<JournalOccupationModuleTheme> getJournalOccupationModuleThemes() {
        return journalOccupationModuleThemes != null ? journalOccupationModuleThemes : (journalOccupationModuleThemes = new ArrayList<>());
    }

    public void setJournalOccupationModuleThemes(List<JournalOccupationModuleTheme> journalOccupationModuleThemes) {
        this.journalOccupationModuleThemes = journalOccupationModuleThemes;
    }
}
