package ee.hitsa.ois.domain.student;

import ee.hitsa.ois.domain.BaseEntityWithId;
import ee.hitsa.ois.domain.PracticeJournal;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModule;
import ee.hitsa.ois.domain.curriculum.CurriculumVersionOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.Journal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student_vocational_omodule_theme")
public class StudentVocationalOccupationModuleTheme extends BaseEntityWithId {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_id")
    private CurriculumVersionOccupationModule module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_curriculum_version_omodule_id")
    private CurriculumVersionOccupationModule oldModule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_version_omodule_theme_id")
    private CurriculumVersionOccupationModuleTheme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_curriculum_version_omodule_theme_id")
    private CurriculumVersionOccupationModuleTheme oldTheme;

    @ManyToOne(fetch = FetchType.LAZY)
    private Journal journal;

    @ManyToOne(fetch = FetchType.LAZY)
    private PracticeJournal practiceJournal;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CurriculumVersionOccupationModule getModule() {
        return module;
    }

    public void setModule(CurriculumVersionOccupationModule module) {
        this.module = module;
    }

    public CurriculumVersionOccupationModule getOldModule() {
        return oldModule;
    }

    public void setOldModule(CurriculumVersionOccupationModule oldModule) {
        this.oldModule = oldModule;
    }

    public CurriculumVersionOccupationModuleTheme getTheme() {
        return theme;
    }

    public void setTheme(CurriculumVersionOccupationModuleTheme theme) {
        this.theme = theme;
    }

    public CurriculumVersionOccupationModuleTheme getOldTheme() {
        return oldTheme;
    }

    public void setOldTheme(CurriculumVersionOccupationModuleTheme oldTheme) {
        this.oldTheme = oldTheme;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public PracticeJournal getPracticeJournal() {
        return practiceJournal;
    }

    public void setPracticeJournal(PracticeJournal practiceJournal) {
        this.practiceJournal = practiceJournal;
    }
}
