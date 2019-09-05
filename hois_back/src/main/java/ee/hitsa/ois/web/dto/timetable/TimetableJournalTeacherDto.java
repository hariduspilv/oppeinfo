package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TimetableJournalTeacherDto extends AutocompleteResult {

    private Long journalId;
    private Long journalTeacherId;
    private List<TimetableJournalTeacherCapacityDto> capacities = new ArrayList<>();

    public TimetableJournalTeacherDto() {
        super();
    }

    public TimetableJournalTeacherDto(Long id, String nameEt, String nameEn, Long journalId, Long journalTeacherId) {
        super(id, nameEt, nameEn);
        this.journalId = journalId;
        this.journalTeacherId = journalTeacherId;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public Long getJournalTeacherId() {
        return journalTeacherId;
    }

    public void setJournalTeacherId(Long journalTeacherId) {
        this.journalTeacherId = journalTeacherId;
    }

    public List<TimetableJournalTeacherCapacityDto> getCapacities() {
        return capacities;
    }

    public void setCapacities(List<TimetableJournalTeacherCapacityDto> capacities) {
        this.capacities = capacities;
    }

}
