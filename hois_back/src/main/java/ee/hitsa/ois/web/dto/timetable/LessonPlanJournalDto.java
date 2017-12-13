package ee.hitsa.ois.web.dto.timetable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.student.StudentGroup;
import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.JournalOccupationModuleTheme;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionOccupationModuleThemeResult;

public class LessonPlanJournalDto extends LessonPlanJournalForm {

    private Long id;
    private Long studentGroupId;
    private String studentGroupCode;
    private Long lessonPlan;
    private List<CurriculumVersionOccupationModuleThemeResult> themes;

    public static LessonPlanJournalDto of(Journal journal, LessonPlanModule lessonPlanModule) {
        LessonPlanJournalDto dto = EntityUtil.bindToDto(journal, new LessonPlanJournalDto(), "journalRooms");
        dto.setStudentGroupCode(lessonPlanModule.getLessonPlan().getStudentGroup().getCode());
        dto.setStudentGroupId(EntityUtil.getId(lessonPlanModule.getLessonPlan().getStudentGroup()));
        Long lessonPlanModuleId = EntityUtil.getId(lessonPlanModule);
        dto.setLessonPlanModuleId(lessonPlanModuleId);
        dto.setJournalCapacityTypes(StreamUtil.toMappedList(r -> EntityUtil.getCode(r.getCapacityType()), journal.getJournalCapacityTypes()));
        
        if(journal.getJournalOccupationModuleThemes() != null) {
            //journalOccupationModuleThemes is only for the themes of the journal and not for the groups which are stored in the same table
            dto.setJournalOccupationModuleThemes(
                    StreamUtil.toMappedList(r -> EntityUtil.getId(r.getCurriculumVersionOccupationModuleTheme()), 
                            journal.getJournalOccupationModuleThemes().stream().filter(module -> EntityUtil.getId(module.getLessonPlanModule()).equals(EntityUtil.getId(lessonPlanModule)))));
            //all other themes will be used for groups
            List<JournalOccupationModuleTheme> journalModuleThemes = journal.getJournalOccupationModuleThemes().stream().filter(it -> !lessonPlanModuleId.equals(EntityUtil.getId(it.getLessonPlanModule()))).collect(Collectors.toList());
            Map<StudentGroup, List<JournalOccupationModuleTheme>> studentGroupMap = journalModuleThemes.stream().collect(Collectors.groupingBy(module -> module.getLessonPlanModule().getLessonPlan().getStudentGroup()));
            dto.setGroups(StreamUtil.toMappedList(LessonPlanJournalForm.LessonPlanGroupForm::of, studentGroupMap.entrySet()));
        }
        dto.setJournalTeachers(StreamUtil.nullSafeList(journal.getJournalTeachers()).stream().map(LessonPlanJournalForm.LessonPlanJournalTeacherForm::of).sorted(Comparator.comparing(r -> ((AutocompleteResult)r.getTeacher()).getNameEt(), String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList()));
        dto.setLessonPlan(EntityUtil.getId(lessonPlanModule.getLessonPlan()));
        dto.setThemes(lessonPlanModule.getCurriculumVersionOccupationModule().getThemes().stream().map(it -> new CurriculumVersionOccupationModuleThemeResult(it)).sorted(Comparator.comparing(r -> r.getNameEt(), String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList()));
        dto.setJournalRooms(StreamUtil.toMappedList(r -> new AutocompleteResult(EntityUtil.getId(r.getRoom()), r.getRoom().getCode(), r.getRoom().getCode()), journal.getJournalRooms()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentGroupId() {
        return studentGroupId;
    }

    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }

    public String getStudentGroupCode() {
        return studentGroupCode;
    }

    public void setStudentGroupCode(String studentGroupCode) {
        this.studentGroupCode = studentGroupCode;
    }

    public Long getLessonPlan() {
        return lessonPlan;
    }

    public void setLessonPlan(Long lessonPlan) {
        this.lessonPlan = lessonPlan;
    }

    public List<CurriculumVersionOccupationModuleThemeResult> getThemes() {
        return themes;
    }

    public void setThemes(List<CurriculumVersionOccupationModuleThemeResult> themes) {
        this.themes = themes;
    }
}
