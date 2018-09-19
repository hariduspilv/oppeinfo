package ee.hitsa.ois.web.dto.timetable;

import java.math.BigDecimal;

public class LessonPlanSearchDto {

    private final Long id;
    private final String studentGroup;
    private final String curriculumVersion;
    private final BigDecimal plannedHours;

    public LessonPlanSearchDto(Long id, String studentGroup, String curriculumVersion, BigDecimal plannedHours) {
        this.id = id;
        this.studentGroup = studentGroup;
        this.curriculumVersion = curriculumVersion;
        this.plannedHours = plannedHours;
    }

    public Long getId() {
        return id;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public String getCurriculumVersion() {
        return curriculumVersion;
    }

    public BigDecimal getPlannedHours() {
        return plannedHours;
    }
    
}
