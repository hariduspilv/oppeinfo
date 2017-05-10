package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalEntry;
import ee.hitsa.ois.domain.timetable.JournalOmoduleTheme;
import ee.hitsa.ois.domain.timetable.JournalStudent;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class JournalDto {

    private Long id;
    private String studyYear;
    private LocalDate studyYearEndDate;
    private String nameEt;
    private List<String> studentGroups = new ArrayList<>();
    private List<String> journalTeachers = new ArrayList<>();
    private List<AutocompleteResult> curriculumModules = new ArrayList<>();
    private List<JournalStudentDto> journalStudents = new ArrayList<>();
    private List<JournalEntryDto> journalEntries = new ArrayList<>();
    private Integer plannedHours;
    private Integer usedHours;
    private String status;
    private LocalDate endDate;

    public static JournalDto of(Journal journal) {
        JournalDto dto = EntityUtil.bindToDto(journal, new JournalDto(), "studyYear", "journalTeachers", "journalStudents", "journalEntries");
        dto.setStudyYear(EntityUtil.getCode(journal.getStudyYear().getYear()));
        dto.setStudyYearEndDate(journal.getStudyYear().getEndDate());
        for (JournalOmoduleTheme theme : journal.getJournalOmoduleThemes()) {
            dto.getStudentGroups().add(theme.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode());
            dto.getCurriculumModules().add(AutocompleteResult.of(theme.getCurriculumVersionOccupationModuleTheme().getModule().getCurriculumModule()));
        }
        for (JournalTeacher teacher : journal.getJournalTeachers()) {
            dto.getJournalTeachers().add(PersonUtil.fullname(teacher.getTeacher().getPerson()));
        }
        for (JournalStudent journalStudent : journal.getJournalStudents()) {
            dto.getJournalStudents().add(JournalStudentDto.of(journalStudent));
        }
        for (JournalEntry JournalEntry : journal.getJournalEntries()) {
            dto.getJournalEntries().add(JournalEntryDto.of(JournalEntry));
        }

        dto.setPlannedHours(Integer.valueOf(journal.getJournalCapacities().stream().mapToInt(it -> it.getHours().intValue()).sum()));
        dto.setUsedHours(Integer.valueOf(journal.getJournalEntries().stream().mapToInt(JournalEntry::getLessons).sum()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }

    public LocalDate getStudyYearEndDate() {
        return studyYearEndDate;
    }

    public void setStudyYearEndDate(LocalDate studyYearEndDate) {
        this.studyYearEndDate = studyYearEndDate;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public List<String> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(List<String> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<String> getJournalTeachers() {
        return journalTeachers;
    }

    public void setJournalTeachers(List<String> journalTeachers) {
        this.journalTeachers = journalTeachers;
    }

    public List<AutocompleteResult> getCurriculumModules() {
        return curriculumModules;
    }

    public void setCurriculumModules(List<AutocompleteResult> curriculumModules) {
        this.curriculumModules = curriculumModules;
    }

    public List<JournalStudentDto> getJournalStudents() {
        return journalStudents;
    }

    public void setJournalStudents(List<JournalStudentDto> journalStudents) {
        this.journalStudents = journalStudents;
    }

    public List<JournalEntryDto> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(List<JournalEntryDto> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public Integer getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(Integer plannedHours) {
        this.plannedHours = plannedHours;
    }

    public Integer getUsedHours() {
        return usedHours;
    }

    public void setUsedHours(Integer usedHours) {
        this.usedHours = usedHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


}
