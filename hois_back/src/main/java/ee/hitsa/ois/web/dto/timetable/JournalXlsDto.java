package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.util.EntityUtil;

public class JournalXlsDto extends JournalDto {

    private List<JournalEntryDto> journalEntries = new ArrayList<>();
    private List<JournalStudentDto> journalStudents = new ArrayList<>();
    private List<JournalEntryByDateXlsDto> journalEntriesByDate = new ArrayList<>();

    public static JournalXlsDto of(Journal journal) {
        JournalDto journalDto = JournalDto.of(journal);
        JournalXlsDto dto = new JournalXlsDto();
        BeanUtils.copyProperties(journalDto, dto, "journalEntries", "journalStudents");

        for (JournalEntry entry : journal.getJournalEntries()) {
            dto.getJournalEntries().add(EntityUtil.bindToDto(entry, new JournalEntryDto()));

            JournalEntryByDateXlsDto journalEntryByDateDto = EntityUtil.bindToDto(entry,
                    new JournalEntryByDateXlsDto());

            for (JournalEntryStudent journalEntryStudent : entry.getJournalEntryStudents()) {
                if (journalEntryStudent.getGrade() != null) {
                    journalEntryByDateDto.getJournalStudentGrade().put(EntityUtil.getId(journalEntryStudent.getJournalStudent()),
                            journalEntryStudent.getGrade().getValue());
                }

                if (journalEntryStudent.getAbsence() != null) {
                    journalEntryByDateDto.getJournalStudentAbsence().put(EntityUtil.getId(journalEntryStudent.getJournalStudent()),
                            journalEntryStudent.getAbsence().getValue());
                }
                
                if(journalEntryStudent.getAddInfo() != null) {
                    journalEntryByDateDto.getJournalStudentAddInfo().put(EntityUtil.getId(journalEntryStudent.getJournalStudent()),
                            journalEntryStudent.getAddInfo());
                }
            }
            dto.getJournalEntriesByDate().add(journalEntryByDateDto);
        }

        Collections.sort(dto.getJournalEntries(),
                Comparator.comparing(JournalEntryDto::getEntryDate, Comparator.nullsLast(Comparator.reverseOrder())));
        Collections.sort(dto.getJournalEntriesByDate(), Comparator.comparing(JournalEntryByDateXlsDto::getEntryDate,
                Comparator.nullsLast(Comparator.naturalOrder())));

        for (JournalStudent journalStudent : journal.getJournalStudents()) {
            dto.getJournalStudents().add(JournalStudentDto.of(journalStudent));
        }
        Collections.sort(dto.getJournalStudents(),
                Comparator.comparing(JournalStudentDto::getFullname, Comparator.nullsLast(Comparator.naturalOrder())));

        if (dto.getEndDate() == null) {
            dto.setEndDate(journalDto.getStudyYearEndDate());
        }
        return dto;
    }

    public List<JournalEntryDto> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(List<JournalEntryDto> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public List<JournalStudentDto> getJournalStudents() {
        return journalStudents;
    }

    public void setJournalStudents(List<JournalStudentDto> journalStudents) {
        this.journalStudents = journalStudents;
    }

    public List<JournalEntryByDateXlsDto> getJournalEntriesByDate() {
        return journalEntriesByDate;
    }

    public void setJournalEntriesByDate(List<JournalEntryByDateXlsDto> journalEntriesByDate) {
        this.journalEntriesByDate = journalEntriesByDate;
    }

}
