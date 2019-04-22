package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.enums.Absence;

public class StudentJournalResultDto {

    private Long id;
    private Boolean existsInJournal;
    private List<StudentJournalEntryResultDto> results = new ArrayList<>();
    private List<StudentJournalEntryAbsenceDto> absences = new ArrayList<>();
    private Map<String, Map<Object, Long>> absencesByDate = new HashMap<>();
    private Map<String, Long> absenceTotals = new HashMap<>();

    public StudentJournalResultDto() {

    }

    public StudentJournalResultDto(StudentJournalResultDto journalResult) {
        this.id = journalResult.getId();
        this.existsInJournal = journalResult.getExistsInJournal();
        for (StudentJournalEntryResultDto result : journalResult.getResults()) {
            this.results.add(new StudentJournalEntryResultDto(result));
        }
        for (StudentJournalEntryAbsenceDto absence : journalResult.getAbsences()) {
            this.absences.add(new StudentJournalEntryAbsenceDto(absence));
        }
        for (Absence absence : Absence.values()) {
            this.absencesByDate.put(absence.name(), new LinkedHashMap<>());
        }
        for (Absence absence : Absence.values()) {
            this.absenceTotals.put(absence.name(), Long.valueOf(0));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExistsInJournal() {
        return existsInJournal;
    }

    public void setExistsInJournal(Boolean existsInJournal) {
        this.existsInJournal = existsInJournal;
    }

    public List<StudentJournalEntryResultDto> getResults() {
        return results;
    }

    public void setResults(List<StudentJournalEntryResultDto> results) {
        this.results = results;
    }

    public List<StudentJournalEntryAbsenceDto> getAbsences() {
        return absences;
    }

    public void setAbsences(List<StudentJournalEntryAbsenceDto> absences) {
        this.absences = absences;
    }

    public Map<String, Map<Object, Long>> getAbsencesByDate() {
        return absencesByDate;
    }

    public void setAbsencesByDate(Map<String, Map<Object, Long>> absencesByDate) {
        this.absencesByDate = absencesByDate;
    }

    public Map<String, Long> getAbsenceTotals() {
        return absenceTotals;
    }

    public void setAbsenceTotals(Map<String, Long> absenceTotals) {
        this.absenceTotals = absenceTotals;
    }

}
