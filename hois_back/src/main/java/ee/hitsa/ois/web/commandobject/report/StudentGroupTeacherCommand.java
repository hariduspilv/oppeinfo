package ee.hitsa.ois.web.commandobject.report;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

public class StudentGroupTeacherCommand {

    private Long studyYear;
    private Long studyPeriod;
    private LocalDate studyPeriodStart;
    private LocalDate studyPeriodEnd;
    @NotNull
    private Long studentGroup;
    private Long student;
    private Long curriculumVersion;
    private LocalDate from;
    private LocalDate thru;
    private List<String> entryTypes;
    private Boolean moduleGrade = Boolean.FALSE;
    private Boolean absencesPerJournals = Boolean.FALSE;
    private Boolean journalsWithEntries = Boolean.FALSE;
    private Boolean negativeResults = Boolean.FALSE;
    private Boolean averageGrade = Boolean.FALSE;
    private Boolean weightedAverageGrade = Boolean.FALSE;
    private Boolean onlyModuleGrades = Boolean.FALSE;

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public Long getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public LocalDate getStudyPeriodStart() {
        return studyPeriodStart;
    }

    public void setStudyPeriodStart(LocalDate studyPeriodStart) {
        this.studyPeriodStart = studyPeriodStart;
    }

    public LocalDate getStudyPeriodEnd() {
        return studyPeriodEnd;
    }

    public void setStudyPeriodEnd(LocalDate studyPeriodEnd) {
        this.studyPeriodEnd = studyPeriodEnd;
    }

    public Long getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public Long getCurriculumVersion() {
        return curriculumVersion;
    }

    public void setCurriculumVersion(Long curriculumVersion) {
        this.curriculumVersion = curriculumVersion;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getThru() {
        return thru;
    }

    public void setThru(LocalDate thru) {
        this.thru = thru;
    }

    public List<String> getEntryTypes() {
        return entryTypes;
    }

    public void setEntryTypes(List<String> entryTypes) {
        this.entryTypes = entryTypes;
    }

    public Boolean getModuleGrade() {
        return moduleGrade;
    }

    public void setModuleGrade(Boolean moduleGrade) {
        this.moduleGrade = moduleGrade;
    }

    public Boolean getAbsencesPerJournals() {
        return absencesPerJournals;
    }

    public void setAbsencesPerJournals(Boolean absencesPerJournals) {
        this.absencesPerJournals = absencesPerJournals;
    }

    public Boolean getJournalsWithEntries() {
        return journalsWithEntries;
    }

    public void setJournalsWithEntries(Boolean journalsWithEntries) {
        this.journalsWithEntries = journalsWithEntries;
    }

    public Boolean getNegativeResults() {
        return negativeResults;
    }

    public void setNegativeResults(Boolean negativeResults) {
        this.negativeResults = negativeResults;
    }

    public Boolean getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Boolean averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Boolean getWeightedAverageGrade() {
        return weightedAverageGrade;
    }

    public void setWeightedAverageGrade(Boolean weightedAverageGrade) {
        this.weightedAverageGrade = weightedAverageGrade;
    }

    public Boolean getOnlyModuleGrades() {
        return onlyModuleGrades;
    }

    public void setOnlyModuleGrades(Boolean onlyModuleGrades) {
        this.onlyModuleGrades = onlyModuleGrades;
    }

}
