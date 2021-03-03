package ee.hitsa.ois.report.studentgroupteacher;

import ee.hitsa.ois.web.dto.AutocompleteResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentGroupTeacherReportCard {

    public static final String TEMPLATE_NAME = "student.group.teacher.report.card.xhtml";

    private AutocompleteResult school;
    private AutocompleteResult studyYear;
    private String studentGroup;
    private LocalDate entriesFrom;
    private LocalDate entriesThru;

    private Boolean showLesson;
    private Boolean showPracticalWork;
    private Boolean showELearning;
    private Boolean showIndividualWork;

    private Boolean showOtherEntries;
    private Boolean showEvaluation;
    private Boolean showPeriodGrade;
    private Boolean showOutcome;
    private Boolean showFinalResult;
    private Boolean showModuleGrade;
    private Long totalGradeColumns;

    private Boolean showAbsences;

    private List<StudentGroupTeacherReportCardStudent> students;

    public AutocompleteResult getSchool() {
        return school;
    }

    public void setSchool(AutocompleteResult school) {
        this.school = school;
    }

    public AutocompleteResult getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(AutocompleteResult studyYear) {
        this.studyYear = studyYear;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public LocalDate getEntriesFrom() {
        return entriesFrom;
    }

    public void setEntriesFrom(LocalDate entriesFrom) {
        this.entriesFrom = entriesFrom;
    }

    public LocalDate getEntriesThru() {
        return entriesThru;
    }

    public void setEntriesThru(LocalDate entriesThru) {
        this.entriesThru = entriesThru;
    }

    public Boolean getShowLesson() {
        return showLesson;
    }

    public void setShowLesson(Boolean showLesson) {
        this.showLesson = showLesson;
    }

    public Boolean getShowPracticalWork() {
        return showPracticalWork;
    }

    public void setShowPracticalWork(Boolean showPracticalWork) {
        this.showPracticalWork = showPracticalWork;
    }

    public Boolean getShowELearning() {
        return showELearning;
    }

    public void setShowELearning(Boolean showELearning) {
        this.showELearning = showELearning;
    }

    public Boolean getShowIndividualWork() {
        return showIndividualWork;
    }

    public void setShowIndividualWork(Boolean showIndividualWork) {
        this.showIndividualWork = showIndividualWork;
    }

    public Boolean getShowOtherEntries() {
        return showOtherEntries;
    }

    public void setShowOtherEntries(Boolean showOtherEntries) {
        this.showOtherEntries = showOtherEntries;
    }

    public Boolean getShowEvaluation() {
        return showEvaluation;
    }

    public void setShowEvaluation(Boolean showEvaluation) {
        this.showEvaluation = showEvaluation;
    }

    public Boolean getShowPeriodGrade() {
        return showPeriodGrade;
    }

    public void setShowPeriodGrade(Boolean showPeriodGrade) {
        this.showPeriodGrade = showPeriodGrade;
    }

    public Boolean getShowOutcome() {
        return showOutcome;
    }

    public void setShowOutcome(Boolean showOutcome) {
        this.showOutcome = showOutcome;
    }

    public Boolean getShowFinalResult() {
        return showFinalResult;
    }

    public void setShowFinalResult(Boolean showFinalResult) {
        this.showFinalResult = showFinalResult;
    }

    public Boolean getShowModuleGrade() {
        return showModuleGrade;
    }

    public void setShowModuleGrade(Boolean showModuleGrade) {
        this.showModuleGrade = showModuleGrade;
    }

    public Long getTotalGradeColumns() {
        return totalGradeColumns;
    }

    public void setTotalGradeColumns(Long totalGradeColumns) {
        this.totalGradeColumns = totalGradeColumns;
    }

    public List<StudentGroupTeacherReportCardStudent> getStudents() {
        return students != null ? students : (students = new ArrayList<>());
    }

    public void setStudents(List<StudentGroupTeacherReportCardStudent> students) {
        this.students = students;
    }

    public Boolean getShowAbsences() {
        return showAbsences;
    }

    public void setShowAbsences(Boolean showAbsences) {
        this.showAbsences = showAbsences;
    }
}
