package ee.hitsa.ois.domain.timetable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;

@Entity
@Table(name="journal_omodule_theme")
public class JournalOccupationModuleTheme extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, insertable = false)
    private Journal journal;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LessonPlanModule lessonPlanModule;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_theme_id", nullable = false, updatable = false)
    private CurriculumVersionOccupationModuleTheme curriculumVersionOccupationModuleTheme;

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

    public CurriculumVersionOccupationModuleTheme getCurriculumVersionOccupationModuleTheme() {
        return curriculumVersionOccupationModuleTheme;
    }

    public void setCurriculumVersionOccupationModuleTheme(CurriculumVersionOccupationModuleTheme theme) {
        this.curriculumVersionOccupationModuleTheme = theme;
    }
}
