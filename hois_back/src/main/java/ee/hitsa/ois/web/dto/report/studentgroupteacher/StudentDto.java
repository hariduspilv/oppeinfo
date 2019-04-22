package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.web.dto.student.StudentRemarkDto;

public class StudentDto {

    private Long id;
    private String fullname;
    private String status;
    private Boolean isIndividualCurriculum;
    private List<StudentResultColumnDto> resultColumns = new ArrayList<>();
    private List<StudentJournalEntryAbsenceDto> absenceEntries = new ArrayList<>();
    private Map<String, Long> absenceTypeTotals = new HashMap<>();
    private Long totalAbsences;
    private Long withoutReasonAbsences;
    private Long withReasonAbsences;
    private Long beingLate;
    private Long journalEntryLessons;
    private BigDecimal lessonAbsencePercentage;
    private List<StudentRemarkDto> remarks = new ArrayList<>();
    private BigDecimal averageGrade;
    private BigDecimal weightedAverageGrade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsIndividualCurriculum() {
        return isIndividualCurriculum;
    }

    public void setIsIndividualCurriculum(Boolean isIndividualCurriculum) {
        this.isIndividualCurriculum = isIndividualCurriculum;
    }

    public List<StudentResultColumnDto> getResultColumns() {
        return resultColumns;
    }

    public void setResultColumns(List<StudentResultColumnDto> resultColumns) {
        this.resultColumns = resultColumns;
    }

    public List<StudentJournalEntryAbsenceDto> getAbsenceEntries() {
        return absenceEntries;
    }

    public void setAbsenceEntries(List<StudentJournalEntryAbsenceDto> absenceEntries) {
        this.absenceEntries = absenceEntries;
    }

    public Map<String, Long> getAbsenceTypeTotals() {
        return absenceTypeTotals;
    }

    public void setAbsenceTypeTotals(Map<String, Long> absenceTypeTotals) {
        this.absenceTypeTotals = absenceTypeTotals;
    }

    public Long getTotalAbsences() {
        return totalAbsences;
    }

    public void setTotalAbsences(Long totalAbsences) {
        this.totalAbsences = totalAbsences;
    }

    public Long getWithoutReasonAbsences() {
        return withoutReasonAbsences;
    }

    public void setWithoutReasonAbsences(Long withoutReasonAbsences) {
        this.withoutReasonAbsences = withoutReasonAbsences;
    }

    public Long getWithReasonAbsences() {
        return withReasonAbsences;
    }

    public void setWithReasonAbsences(Long withReasonAbsences) {
        this.withReasonAbsences = withReasonAbsences;
    }

    public Long getBeingLate() {
        return beingLate;
    }

    public void setBeingLate(Long beingLate) {
        this.beingLate = beingLate;
    }

    public Long getJournalEntryLessons() {
        return journalEntryLessons;
    }

    public void setJournalEntryLessons(Long journalEntryLessons) {
        this.journalEntryLessons = journalEntryLessons;
    }

    public BigDecimal getLessonAbsencePercentage() {
        return lessonAbsencePercentage;
    }

    public void setLessonAbsencePercentage(BigDecimal lessonAbsencePercentage) {
        this.lessonAbsencePercentage = lessonAbsencePercentage;
    }

    public List<StudentRemarkDto> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<StudentRemarkDto> remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
    }

    public BigDecimal getWeightedAverageGrade() {
        return weightedAverageGrade;
    }

    public void setWeightedAverageGrade(BigDecimal weightedAverageGrade) {
        this.weightedAverageGrade = weightedAverageGrade;
    }

}
