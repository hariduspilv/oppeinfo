package ee.hitsa.ois.web.dto.timetable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.JournalTeacher;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.JournalUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class JournalSearchDto {

    private Long id;
    private Set<String> studentGroups = new HashSet<>();
    private String nameEt;
    private List<String> teachers = new ArrayList<>();
    private Set<AutocompleteResult> modules = new HashSet<>();
    private Integer plannedHours;
    private Integer usedHours;
    private String status;
    private Set<String> curriculums = new HashSet<>();
    private Boolean canEdit;
    
    public static JournalSearchDto of(HoisUserDetails user, Journal journal) {
        JournalSearchDto dto = of(journal);
        dto.setCanEdit(Boolean.valueOf(JournalUtil.hasPermissionToChange(user, journal)));
        return dto;
    }

    public static JournalSearchDto of(Journal journal) {
        JournalSearchDto dto = EntityUtil.bindToDto(journal, new JournalSearchDto());
        for (JournalOccupationModuleTheme theme : journal.getJournalOccupationModuleThemes()) {
            dto.getStudentGroups().add(theme.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode());
            dto.getModules().add(AutocompleteResult.of(theme.getCurriculumVersionOccupationModuleTheme().getModule().getCurriculumModule()));
            dto.getCurriculums().add(theme.getCurriculumVersionOccupationModuleTheme().getModule().getCurriculumModule().getCurriculum().getCode());
        }

        for (JournalTeacher teacher : journal.getJournalTeachers()) {
            dto.getTeachers().add(PersonUtil.fullname(teacher.getTeacher().getPerson()));
        }

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
    public Set<String> getStudentGroups() {
        return studentGroups;
    }
    public void setStudentGroups(Set<String> studentGroups) {
        this.studentGroups = studentGroups;
    }
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    public List<String> getTeachers() {
        return teachers;
    }
    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }
    public Set<AutocompleteResult> getModules() {
        return modules;
    }
    public void setModules(Set<AutocompleteResult> modules) {
        this.modules = modules;
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
    public Set<String> getCurriculums() {
        return curriculums;
    }
    public void setCurriculums(Set<String> curriculums) {
        this.curriculums = curriculums;
    }
    public Boolean getCanEdit() {
        return canEdit;
    }
    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
}
