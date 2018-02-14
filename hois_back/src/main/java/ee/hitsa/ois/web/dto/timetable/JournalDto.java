package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class JournalDto {

    private Long id;
    private String studyYear;
    private LocalDate studyYearEndDate;
    private String nameEt;
    private Set<String> studentGroups = new HashSet<>();
    private List<String> journalTeachers = new ArrayList<>();
    private Set<AutocompleteResult> curriculumModules = new HashSet<>();
    private List<JournalModuleDescriptionDto> moduleDescriptions = new ArrayList<>();
    private Integer plannedHours;
    private Integer usedHours;
    @ClassifierRestriction(MainClassCode.PAEVIK_STAATUS)
    private String status;
    private LocalDate endDate;
    private Boolean hasJournalStudents;
    private List<AutocompleteResult> journalRooms = new ArrayList<>();
    
    private Boolean canBeConfirmed;
    private Boolean canBeUnconfirmed;
    private Boolean canEdit;

    public static JournalDto of(Journal journal) {
        JournalDto dto = EntityUtil.bindToDto(journal, new JournalDto(), "studyYear", "journalTeachers", "journalStudents", "journalEntries", "journalRooms");
        dto.setStudyYear(EntityUtil.getCode(journal.getStudyYear().getYear()));
        dto.setStudyYearEndDate(journal.getStudyYear().getEndDate());
        for (JournalOccupationModuleTheme theme : journal.getJournalOccupationModuleThemes()) {
            dto.getStudentGroups().add(theme.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode());
            dto.getCurriculumModules().add(AutocompleteResult.of(theme.getCurriculumVersionOccupationModuleTheme().getModule().getCurriculumModule()));

            JournalModuleDescriptionDto moduleDescription = EntityUtil.bindToDto(theme.getCurriculumVersionOccupationModuleTheme(), new JournalModuleDescriptionDto());
            dto.moduleDescriptions.add(moduleDescription);
        }
        for (JournalTeacher teacher : journal.getJournalTeachers()) {
            dto.getJournalTeachers().add(PersonUtil.fullname(teacher.getTeacher().getPerson()));
        }
        dto.setJournalRooms(StreamUtil.toMappedList(r -> new AutocompleteResult(EntityUtil.getId(r.getRoom()),
                r.getRoom().getCode(), r.getRoom().getCode()), journal.getJournalRooms()));

        dto.setHasJournalStudents(Boolean.valueOf(!journal.getJournalStudents().isEmpty()));

        dto.setPlannedHours(Integer.valueOf(journal.getJournalCapacities().stream().mapToInt(it -> it.getHours() == null ? 0 : it.getHours().intValue()).sum()));
        dto.setUsedHours(Integer.valueOf(journal.getJournalEntries().stream().mapToInt(it -> it.getLessons() == null ? 0 : it.getLessons().intValue()).sum()));
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

    public Set<String> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<String> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<String> getJournalTeachers() {
        return journalTeachers;
    }

    public void setJournalTeachers(List<String> journalTeachers) {
        this.journalTeachers = journalTeachers;
    }

    public Set<AutocompleteResult> getCurriculumModules() {
        return curriculumModules;
    }

    public void setCurriculumModules(Set<AutocompleteResult> curriculumModules) {
        this.curriculumModules = curriculumModules;
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

    public Boolean getHasJournalStudents() {
        return hasJournalStudents;
    }

    public void setHasJournalStudents(Boolean hasJournalStudents) {
        this.hasJournalStudents = hasJournalStudents;
    }

    public List<AutocompleteResult> getJournalRooms() {
        return journalRooms;
    }

    public void setJournalRooms(List<AutocompleteResult> journalRooms) {
        this.journalRooms = journalRooms;
    }

    public List<JournalModuleDescriptionDto> getModuleDescriptions() {
        return moduleDescriptions;
    }

    public void setModuleDescriptions(List<JournalModuleDescriptionDto> moduleDescriptions) {
        this.moduleDescriptions = moduleDescriptions;
    }

    public Boolean getCanBeConfirmed() {
        return canBeConfirmed;
    }

    public void setCanBeConfirmed(Boolean canBeConfirmed) {
        this.canBeConfirmed = canBeConfirmed;
    }

    public Boolean getCanBeUnconfirmed() {
        return canBeUnconfirmed;
    }

    public void setCanBeUnconfirmed(Boolean canBeUnconfirmed) {
        this.canBeUnconfirmed = canBeUnconfirmed;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
    
}


