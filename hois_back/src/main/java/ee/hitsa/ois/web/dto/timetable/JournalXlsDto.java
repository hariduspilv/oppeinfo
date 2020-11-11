package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.enums.Language;
import org.springframework.beans.BeanUtils;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalEntryStudent;
import ee.hitsa.ois.domain.timetable.JournalEntryStudentLessonAbsence;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.enums.JournalEntryType;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JournalUtil;

public class JournalXlsDto extends JournalDto {

    private Boolean isHigherSchool;
    private List<JournalEntryDto> journalEntries = new ArrayList<>();
    private List<JournalStudentDto> journalStudents = new ArrayList<>();
    private List<JournalEntryByDateXlsDto> journalEntriesByDate = new ArrayList<>();
    private List<JournalEntryByDateXlsDto> outcomeEntries = new ArrayList<>();

    private Boolean includesFinalResult = Boolean.FALSE;
    private LocalDate journalFinalResultDate;
    private Map<Long, String> journalFinalResults = new HashMap<>();

    public static JournalXlsDto of(Journal journal, Language lang) {
        JournalDto journalDto = JournalDto.of(journal);
        JournalXlsDto dto = new JournalXlsDto();
        BeanUtils.copyProperties(journalDto, dto, "journalEntries", "journalStudents");

        for (JournalEntry entry : journal.getJournalEntries()) {
            dto.getJournalEntries().add(EntityUtil.bindToDto(entry, new JournalEntryDto()));
            
            if (!entry.getEntryType().getCode().equals(JournalEntryType.SISSEKANNE_L.name())) {
                JournalEntryByDateXlsDto journalEntryByDateDto = EntityUtil.bindToDto(entry,
                        new JournalEntryByDateXlsDto());
                journalEntryByDateDto.setStartLessonNr(entry.getStartLessonNr());
                journalEntryByDateDto.setLessons(entry.getLessons());

                for (JournalEntryStudent journalEntryStudent : entry.getJournalEntryStudents()) {
                    Long journalStudentId = EntityUtil.getId(journalEntryStudent.getJournalStudent());
                    if (journalEntryStudent.getGradingSchemaRow() != null) {
                        String gradingSchemaGrade = Language.EN.equals(lang) ? journalEntryStudent.getGradingSchemaRow().getGradeEn()
                                : journalEntryStudent.getGradingSchemaRow().getGrade();
                        journalEntryByDateDto.getJournalStudentGrade().put(journalStudentId, gradingSchemaGrade);
                    } else if (journalEntryStudent.getGrade() != null) {
                        journalEntryByDateDto.getJournalStudentGrade().put(journalStudentId,
                                journalEntryStudent.getGrade().getValue());
                    }
                    if (journalEntryStudent.getVerbalGrade() != null) {
                        String studentGrade = journalEntryByDateDto.getJournalStudentGrade().get(journalStudentId);
                        studentGrade = (studentGrade != null ? studentGrade + ", " : "") + journalEntryStudent.getVerbalGrade();
                        journalEntryByDateDto.getJournalStudentGrade().put(journalStudentId, studentGrade);
                    }
    
                    if (Boolean.TRUE.equals(journalEntryStudent.getIsLessonAbsence())) {
                        String absences = "";
                        List<JournalEntryStudentLessonAbsence> lessonAbsences = new ArrayList<>();
                        lessonAbsences.addAll(journalEntryStudent.getJournalEntryStudentLessonAbsences());
                        lessonAbsences.sort(Comparator.comparing(JournalEntryStudentLessonAbsence::getLessonNr));
                        
                        for (JournalEntryStudentLessonAbsence absence : lessonAbsences) {
                            absences += absence.getAbsence().getValue() + "(" + absence.getLessonNr().toString() + ") ";
                        }
                        journalEntryByDateDto.getJournalStudentAbsence().put(journalStudentId, absences);
                    } else {
                        if (journalEntryStudent.getAbsence() != null) {
                            journalEntryByDateDto.getJournalStudentAbsence().put(journalStudentId,
                                    journalEntryStudent.getAbsence().getValue());
                        }
                    }
                    
                    if(journalEntryStudent.getAddInfo() != null) {
                        journalEntryByDateDto.getJournalStudentAddInfo().put(journalStudentId, journalEntryStudent.getAddInfo());
                    }
                }
                dto.getJournalEntriesByDate().add(journalEntryByDateDto);
            } else {
                for (JournalEntryStudent journalEntryStudent : entry.getJournalEntryStudents()) {
                    Long journalStudentId = EntityUtil.getId(journalEntryStudent.getJournalStudent());
                    if (journalEntryStudent.getGradingSchemaRow() != null) {
                        String gradingSchemaGrade = Language.EN.equals(lang) ? journalEntryStudent.getGradingSchemaRow().getGradeEn()
                                : journalEntryStudent.getGradingSchemaRow().getGrade();
                        dto.getJournalFinalResults().put(journalStudentId, gradingSchemaGrade);
                    } else if (journalEntryStudent.getGrade() != null) {
                        dto.getJournalFinalResults().put(journalStudentId, journalEntryStudent.getGrade().getValue());
                    }
                }

                if (Boolean.FALSE.equals(dto.getIncludesFinalResult())) {
                    dto.setIncludesFinalResult(Boolean.TRUE);
                    dto.setJournalFinalResultDate(entry.getEntryDate());
                }
            }
        }
        JournalUtil.orderJournalEntriesByDate(dto.getJournalEntriesByDate());

        for (JournalStudent journalStudent : journal.getJournalStudents()) {
            dto.getJournalStudents().add(JournalStudentDto.of(journalStudent));
        }
        Collections.sort(dto.getJournalStudents(),
                Comparator.comparing(JournalStudentDto::getStudentGroup, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(JournalStudentDto::getLastname, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(JournalStudentDto::getFirstname, String.CASE_INSENSITIVE_ORDER));

        if (dto.getEndDate() == null) {
            dto.setEndDate(journalDto.getStudyYearEndDate());
        }
        return dto;
    }

    public Boolean getIsHigherSchool() {
        return isHigherSchool;
    }

    public void setIsHigherSchool(Boolean isHigherSchool) {
        this.isHigherSchool = isHigherSchool;
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

    public List<JournalEntryByDateXlsDto> getOutcomeEntries() {
        return outcomeEntries;
    }

    public void setOutcomeEntries(List<JournalEntryByDateXlsDto> outcomeEntries) {
        this.outcomeEntries = outcomeEntries;
    }

    public Boolean getIncludesFinalResult() {
        return includesFinalResult;
    }

    public void setIncludesFinalResult(Boolean includesFinalResult) {
        this.includesFinalResult = includesFinalResult;
    }

    public LocalDate getJournalFinalResultDate() {
        return journalFinalResultDate;
    }

    public void setJournalFinalResultDate(LocalDate journalFinalResultDate) {
        this.journalFinalResultDate = journalFinalResultDate;
    }

    public Map<Long, String> getJournalFinalResults() {
        return journalFinalResults;
    }

    public void setJournalFinalResults(Map<Long, String> journalFinalResults) {
        this.journalFinalResults = journalFinalResults;
    }
}
