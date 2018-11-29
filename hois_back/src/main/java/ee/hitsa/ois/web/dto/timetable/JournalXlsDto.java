package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private List<JournalEntryByDateXlsDto> journalEndResults = new ArrayList<>();

    public static JournalXlsDto of(Journal journal) {
        JournalDto journalDto = JournalDto.of(journal);
        JournalXlsDto dto = new JournalXlsDto();
        BeanUtils.copyProperties(journalDto, dto, "journalEntries", "journalStudents");

        int outcomeWithoutOrderNr = 0;
        for (JournalEntry entry : journal.getJournalEntries()) {
            dto.getJournalEntries().add(EntityUtil.bindToDto(entry, new JournalEntryDto()));
            
            if (!entry.getEntryType().getCode().equals(JournalEntryType.SISSEKANNE_L.name())) {
                JournalEntryByDateXlsDto journalEntryByDateDto = EntityUtil.bindToDto(entry,
                        new JournalEntryByDateXlsDto());
                journalEntryByDateDto.setStartLessonNr(entry.getStartLessonNr());
                journalEntryByDateDto.setLessons(entry.getLessons());

                if (entry.getCurriculumModuleOutcomes() != null) {
                    if (entry.getCurriculumModuleOutcomes().getOrderNr() != null) {
                        journalEntryByDateDto.setOutcomeOrderNr(entry.getCurriculumModuleOutcomes().getOrderNr());
                    } else {
                        journalEntryByDateDto.setOutcomeOrderNr(Long.valueOf(outcomeWithoutOrderNr++));
                    }
                    journalEntryByDateDto.setCurriculumModule(EntityUtil.getId(entry.getCurriculumModuleOutcomes().getCurriculumModule()));
                }

                for (JournalEntryStudent journalEntryStudent : entry.getJournalEntryStudents()) {
                    if (journalEntryStudent.getGrade() != null) {
                        journalEntryByDateDto.getJournalStudentGrade().put(EntityUtil.getId(journalEntryStudent.getJournalStudent()),
                                journalEntryStudent.getGrade().getValue());
                    }
    
                    if (Boolean.TRUE.equals(journalEntryStudent.getIsLessonAbsence())) {
                        String absences = "";
                        List<JournalEntryStudentLessonAbsence> lessonAbsences = new ArrayList<>();
                        lessonAbsences.addAll(journalEntryStudent.getJournalEntryStudentLessonAbsences());
                        lessonAbsences.sort(Comparator.comparing(JournalEntryStudentLessonAbsence::getLessonNr));
                        
                        for (JournalEntryStudentLessonAbsence absence : lessonAbsences) {
                            absences += absence.getAbsence().getValue() + "(" + absence.getLessonNr().toString() + ") ";
                        }
                        journalEntryByDateDto.getJournalStudentAbsence()
                                .put(EntityUtil.getId(journalEntryStudent.getJournalStudent()), absences);
                    } else {
                        if (journalEntryStudent.getAbsence() != null) {
                            journalEntryByDateDto.getJournalStudentAbsence().put(EntityUtil.getId(journalEntryStudent.getJournalStudent()),
                                    journalEntryStudent.getAbsence().getValue());
                        }
                    }
                    
                    if(journalEntryStudent.getAddInfo() != null) {
                        journalEntryByDateDto.getJournalStudentAddInfo().put(EntityUtil.getId(journalEntryStudent.getJournalStudent()),
                                journalEntryStudent.getAddInfo());
                    }
                }
                dto.getJournalEntriesByDate().add(journalEntryByDateDto);
            } else {
                JournalEntryByDateXlsDto journalEndResult = EntityUtil.bindToDto(entry,
                        new JournalEntryByDateXlsDto());
                
                for (JournalEntryStudent journalEntryStudent : entry.getJournalEntryStudents()) {
                    if (journalEntryStudent.getGrade() != null) {
                        journalEndResult.getJournalStudentGrade().put(EntityUtil.getId(journalEntryStudent.getJournalStudent()),
                                journalEntryStudent.getGrade().getValue());
                    }
                }
                dto.getJournalEndResults().add(journalEndResult);
            }
        }

        Collections.sort(dto.getJournalEntries(),
                Comparator.comparing(JournalEntryDto::getEntryDate, Comparator.nullsLast(Comparator.reverseOrder())));
        
        setOutcomeEntriesUnqiueOrderNrs(dto.getJournalEntriesByDate());
        orderJournalEntriesByDate(dto.getJournalEntriesByDate());

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

    public List<JournalEntryByDateXlsDto> getJournalEndResults() {
        return journalEndResults;
    }

    public void setJournalEndResults(List<JournalEntryByDateXlsDto> journalEndResults) {
        this.journalEndResults = journalEndResults;
    }
    
    private static void setOutcomeEntriesUnqiueOrderNrs(List<JournalEntryByDateXlsDto> entries) {
        // order outcomes by curriculum module id and their order nr and then give outcomes from different modules a unique outcome order nr
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateXlsDto::getCurriculumModule, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(JournalEntryByDateXlsDto::getOutcomeOrderNr, Comparator.nullsFirst(Comparator.naturalOrder())));
        List<Long> orderNrs = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            Long entryOrderNr = entries.get(i) != null && entries.get(i).getOutcomeOrderNr() != null ? entries.get(i).getOutcomeOrderNr() : null;
            if (entryOrderNr != null) {
                if (!orderNrs.contains(entryOrderNr)) {
                    orderNrs.add(entryOrderNr);
                } else {
                    Long newOrderNr = Long.valueOf(orderNrs.stream().max(Comparator.comparing(nr -> nr)).get().longValue() + 1);
                    entries.get(i).setOutcomeOrderNr(newOrderNr);
                    orderNrs.add(newOrderNr);
                }
            }
        }
    }

    private static void orderJournalEntriesByDate(List<JournalEntryByDateXlsDto> entries) {
        // order day entries by lesson nr
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateXlsDto::getEntryDate, Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(JournalEntryByDateXlsDto::getStartLessonNr, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(JournalEntryByDateXlsDto::getLessons, Comparator.nullsFirst(Comparator.naturalOrder())));
        
        // outcome entries that don't have a date are ordered last among entries without date, all other entries are ordered by date
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateXlsDto::getOutcomeOrderNr, Comparator.nullsFirst(Comparator.naturalOrder())));
        Collections.sort(entries, Comparator.comparing(JournalEntryByDateXlsDto::getEntryDate, Comparator.nullsFirst(Comparator.naturalOrder())));
        
        Collections.sort(entries, (JournalEntryByDateXlsDto o1, JournalEntryByDateXlsDto o2) -> {
            if (JournalUtil.isOutcomeEntryWithoutDate(o1) && !JournalUtil.isOutcomeEntryWithoutDate(o2)) {
                return 1;
            } else if (!JournalUtil.isOutcomeEntryWithoutDate(o1) && JournalUtil.isOutcomeEntryWithoutDate(o2)) {
                return -1;
            }
            return 0;
        });
    }

}
