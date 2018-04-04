package ee.hitsa.ois.web.dto.timetable;

public class LessonPlanCreatedJournalDto {
    
    private final Long id;
    private final Long lessonPlanModuleId;
    
    public LessonPlanCreatedJournalDto(Long id, Long lessonPlanModuleId) {
        this.id = id;
        this.lessonPlanModuleId = lessonPlanModuleId;
    }
    
    public Long getId() {
        return id;
    }
    
    public Long getLessonPlanModuleId() {
        return lessonPlanModuleId;
    }

}
