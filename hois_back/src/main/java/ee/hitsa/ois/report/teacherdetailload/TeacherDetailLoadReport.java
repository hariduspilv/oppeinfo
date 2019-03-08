package ee.hitsa.ois.report.teacherdetailload;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StudyPeriod;
import ee.hitsa.ois.domain.StudyYear;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TeacherDetailLoadReport {

    private StudyYear studyYear;
    private StudyPeriod studyPeriod;
    private AutocompleteResult teacher;
    private LocalDate from;
    private LocalDate thru;
    private List<Classifier> capacities;
    private List<PeriodDto> periods;
    private List<Map<String, Object>> periodTypesRow;
    private List<LoadTypeDto> loadTypesRow;
    private List<ResultRowDto> rows;
    private Boolean journalSubjectReport;
    private Boolean isHigher;
    private Boolean isHigherSchool;

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public StudyPeriod getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(StudyPeriod studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
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

    public List<Classifier> getCapacities() {
        return capacities;
    }

    public void setCapacities(List<Classifier> capacities) {
        this.capacities = capacities;
    }

    public List<PeriodDto> getPeriods() {
        return periods;
    }

    public void setPeriods(List<PeriodDto> periods) {
        this.periods = periods;
    }

    public List<Map<String, Object>> getPeriodTypesRow() {
        return periodTypesRow;
    }

    public void setPeriodTypesRow(List<Map<String, Object>> periodTypesRow) {
        this.periodTypesRow = periodTypesRow;
    }

    public List<LoadTypeDto> getLoadTypesRow() {
        return loadTypesRow;
    }

    public void setLoadTypesRow(List<LoadTypeDto> loadTypesRow) {
        this.loadTypesRow = loadTypesRow;
    }

    public List<ResultRowDto> getRows() {
        return rows;
    }

    public void setRows(List<ResultRowDto> rows) {
        this.rows = rows;
    }

    public Boolean getJournalSubjectReport() {
        return journalSubjectReport;
    }

    public void setJournalSubjectReport(Boolean journalSubjectReport) {
        this.journalSubjectReport = journalSubjectReport;
    }

    public Boolean getIsHigher() {
        return isHigher;
    }

    public void setIsHigher(Boolean isHigher) {
        this.isHigher = isHigher;
    }

    public Boolean getIsHigherSchool() {
        return isHigherSchool;
    }

    public void setIsHigherSchool(Boolean isHigherSchool) {
        this.isHigherSchool = isHigherSchool;
    }

}
