package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.enums.Absence;

public class StudentJournalResultDto {

    private Long id;
    private List<StudentJournalEntryDto> entries = new ArrayList<>();
    private Map<String, Short> absences = new HashMap<>();
    
    public StudentJournalResultDto() {
        
    }
    
    public StudentJournalResultDto(StudentJournalResultDto journalResult) {
        this.id = journalResult.getId();
        for (StudentJournalEntryDto entry : journalResult.getEntries()) {
            this.entries.add(new StudentJournalEntryDto(entry));
        }
        for (Absence absence : Absence.values()) {
            this.absences.put(absence.name(), Short.valueOf((short) 0));
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public List<StudentJournalEntryDto> getEntries() {
        return entries;
    }
    
    public void setEntries(List<StudentJournalEntryDto> entries) {
        this.entries = entries;
    }

    public Map<String, Short> getAbsences() {
        return absences;
    }

    public void setAbsences(Map<String, Short> absences) {
        this.absences = absences;
    }
    
}
