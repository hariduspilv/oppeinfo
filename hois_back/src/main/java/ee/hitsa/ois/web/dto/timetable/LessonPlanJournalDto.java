package ee.hitsa.ois.web.dto.timetable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.timetable.Journal;
import ee.hitsa.ois.domain.timetable.LessonPlanModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.timetable.LessonPlanJournalForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class LessonPlanJournalDto extends LessonPlanJournalForm {

    private Long id;
    private Long lessonPlan;
    private List<AutocompleteResult> themes;

    public static LessonPlanJournalDto of(Journal journal, LessonPlanModule lessonPlanModule) {
        LessonPlanJournalDto dto = EntityUtil.bindToDto(journal, new LessonPlanJournalDto());
        dto.setLessonPlanModuleId(lessonPlanModule.getId());
        dto.setJournalCapacityTypes(StreamUtil.toMappedList(r -> EntityUtil.getCode(r.getCapacityType()), journal.getJournalCapacityTypes()));
        dto.setJournalOccupationModuleThemes(StreamUtil.toMappedList(r -> EntityUtil.getId(r.getCurriculumVersionOccupationModuleTheme()), journal.getJournalOccupationModuleThemes()));
        if(journal.getJournalTeachers() != null) {
            dto.setJournalTeachers(journal.getJournalTeachers().stream().map(LessonPlanJournalForm.LessonPlanJournalTeacherForm::of).sorted(Comparator.comparing(r -> ((AutocompleteResult)r.getTeacher()).getNameEt().toLowerCase())).collect(Collectors.toList()));
        }
        dto.setLessonPlan(EntityUtil.getId(lessonPlanModule.getLessonPlan()));
        dto.setThemes(lessonPlanModule.getCurriculumVersionOccupationModule().getThemes().stream().map(AutocompleteResult::of).sorted(Comparator.comparing(r -> r.getNameEt().toLowerCase())).collect(Collectors.toList()));
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLessonPlan() {
        return lessonPlan;
    }

    public void setLessonPlan(Long lessonPlan) {
        this.lessonPlan = lessonPlan;
    }

    public List<AutocompleteResult> getThemes() {
        return themes;
    }

    public void setThemes(List<AutocompleteResult> themes) {
        this.themes = themes;
    }
}
