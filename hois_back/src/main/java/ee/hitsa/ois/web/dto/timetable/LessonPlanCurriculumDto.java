package ee.hitsa.ois.web.dto.timetable;

import java.time.LocalDate;

public class LessonPlanCurriculumDto {

    private Long curriculum;
    private String merCode;
    private Long studyYear;
    private LocalDate studyYearEndDate;
    private Long lessonplan;
    private String name;

    public Long getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Long curriculum) {
        this.curriculum = curriculum;
    }

    public String getMerCode() {
        return merCode;
    }

    public void setMerCode(String merCode) {
        this.merCode = merCode;
    }

    public Long getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(Long studyYear) {
        this.studyYear = studyYear;
    }

    public LocalDate getStudyYearEndDate() {
        return studyYearEndDate;
    }

    public void setStudyYearEndDate(LocalDate studyYearEndDate) {
        this.studyYearEndDate = studyYearEndDate;
    }

    public Long getLessonplan() {
        return lessonplan;
    }

    public void setLessonplan(Long lessonplan) {
        this.lessonplan = lessonplan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
