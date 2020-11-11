package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.validation.ClassifierRestriction;

import java.time.LocalDate;

public class JournalEntryByDateBaseDto {

    private LocalDate entryDate;
    private String nameEt;
    private String nameEn;

    @ClassifierRestriction(MainClassCode.SISSEKANNE)
    private String entryType;
    private Long journalStudent;
    private Long startLessonNr;
    private Long lessons;
    private Long curriculumModuleOutcomes;
    private Long outcomeOrderNr;
    private Long curriculumModule;

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
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

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public Long getJournalStudent() {
        return journalStudent;
    }

    public void setJournalStudent(Long journalStudent) {
        this.journalStudent = journalStudent;
    }

    public Long getStartLessonNr() {
        return startLessonNr;
    }

    public void setStartLessonNr(Long startLessonNr) {
        this.startLessonNr = startLessonNr;
    }

    public Long getLessons() {
        return lessons;
    }

    public void setLessons(Long lessons) {
        this.lessons = lessons;
    }

    public Long getCurriculumModuleOutcomes() {
        return curriculumModuleOutcomes;
    }

    public void setCurriculumModuleOutcomes(Long curriculumModuleOutcomes) {
        this.curriculumModuleOutcomes = curriculumModuleOutcomes;
    }

    public Long getOutcomeOrderNr() {
        return outcomeOrderNr;
    }

    public void setOutcomeOrderNr(Long outcomeOrderNr) {
        this.outcomeOrderNr = outcomeOrderNr;
    }

    public Long getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(Long curriculumModule) {
        this.curriculumModule = curriculumModule;
    }
}
