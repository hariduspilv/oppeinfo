package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;

@Entity
public class JournalOmoduleTheme extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Journal journal;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LessonPlanModule lessonPlanModule;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CurriculumVersionOccupationModuleTheme curriculumVersionOmoduleTheme;

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public LessonPlanModule getLessonPlanModule() {
        return lessonPlanModule;
    }

    public void setLessonPlanModule(LessonPlanModule lessonPlanModule) {
        this.lessonPlanModule = lessonPlanModule;
    }

    public CurriculumVersionOccupationModuleTheme getCurriculumVersionOmoduleTheme() {
        return curriculumVersionOmoduleTheme;
    }

    public void setCurriculumVersionOmoduleTheme(CurriculumVersionOccupationModuleTheme curriculumVersionOmoduleTheme) {
        this.curriculumVersionOmoduleTheme = curriculumVersionOmoduleTheme;
    }
}
