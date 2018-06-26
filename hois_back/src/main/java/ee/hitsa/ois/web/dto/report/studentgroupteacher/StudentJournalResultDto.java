package ee.hitsa.ois.web.dto.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.List;

public class StudentJournalResultDto {

    private Long id;
    private List<StudentJournalEntryDto> entries = new ArrayList<>();
    private Short absenceH;
    private Short absenceP;
    private Short absenceV;
    
    public StudentJournalResultDto() {
        
    }
    
    public StudentJournalResultDto(StudentJournalResultDto journalResult) {
        this.id = journalResult.getId();
        for (StudentJournalEntryDto entry : journalResult.getEntries()) {
            this.entries.add(new StudentJournalEntryDto(entry));
        }
        this.absenceH = Short.valueOf((short) 0);
        this.absenceP = Short.valueOf((short) 0);
        this.absenceV = Short.valueOf((short) 0);
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
    
    public Short getAbsenceH() {
        return absenceH;
    }
    
    public void setAbsenceH(Short absenceH) {
        this.absenceH = absenceH;
    }
    
    public Short getAbsenceP() {
        return absenceP;
    }
    
    public void setAbsenceP(Short absenceP) {
        this.absenceP = absenceP;
    }
    
    public Short getAbsenceV() {
        return absenceV;
    }
    
    public void setAbsenceV(Short absenceV) {
        this.absenceV = absenceV;
    }
    
}
