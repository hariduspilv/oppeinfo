package ee.hitsa.ois.web.dto.studymaterial;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class JournalDto {

    private Long id;
    private String nameEt;
    private String nameEn; // TODO: There is no nameEn in DB. Before nameEn appears in DB here will be nameEt as nameEn as well.
    private AutocompleteResult studyYear;
    private Set<String> studentGroups;
    private List<AutocompleteResult> teachers;
    private Boolean canConnectStudyMaterials;

    public static JournalDto of(Journal journal) {
        JournalDto dto = EntityUtil.bindToDto(journal, new JournalDto());
        dto.setTeachers(StreamUtil.toMappedList(jt -> AutocompleteResult.of(jt.getTeacher()), 
                journal.getJournalTeachers()));
        dto.setStudentGroups(journal.getJournalOccupationModuleThemes().stream().map(t -> t.getLessonPlanModule().getLessonPlan().getStudentGroup().getCode()).collect(Collectors.toSet()));
        return dto;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public AutocompleteResult getStudyYear() {
        return studyYear;
    }
    public void setStudyYear(AutocompleteResult studyYear) {
        this.studyYear = studyYear;
    }

    public Set<String> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<String> studentGroups) {
        this.studentGroups = studentGroups;
    }

    public List<AutocompleteResult> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<AutocompleteResult> teachers) {
        this.teachers = teachers;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Boolean getCanConnectStudyMaterials() {
        return canConnectStudyMaterials;
    }

    public void setCanConnectStudyMaterials(Boolean canConnectStudyMaterials) {
        this.canConnectStudyMaterials = canConnectStudyMaterials;
    }

}
