package ee.hitsa.ois.web.dto;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.school.StudyYearSchedule;
import ee.hitsa.ois.util.EntityUtil;

public class StudyYearScheduleDto {

    private Long id;
    @NotNull
    private Long studentGroup;
    @NotNull
    private Long studyYearScheduleLegend;
    @NotNull
    private Long studyPeriod;
    @NotNull
    private Long weekNr;
    

    public static StudyYearScheduleDto of(StudyYearSchedule studyYearSchedule) {
        StudyYearScheduleDto dto = new StudyYearScheduleDto();
        dto.setId(EntityUtil.getId(studyYearSchedule));
        dto.setStudentGroup(EntityUtil.getId(studyYearSchedule.getStudentGroup()));
        dto.setStudyYearScheduleLegend(EntityUtil.getId(studyYearSchedule.getStudyYearScheduleLegend()));
        dto.setStudyPeriod(EntityUtil.getId(studyYearSchedule.getStudyPeriod()));
        dto.setWeekNr(studyYearSchedule.getWeekNr());
        return dto;
    }
    
    public StudyYearScheduleDto(Long studentGroupId, Long studyYearScheduleLegendId, Long studyPeriodId, Long weekNr) {
        this.studentGroup = studentGroupId;
        this.studyYearScheduleLegend = studyYearScheduleLegendId;
        this.studyPeriod = studyPeriodId;
        this.weekNr = weekNr;
    }
    
    
    public StudyYearScheduleDto() {
        // TODO Auto-generated constructor stub
    }

    /*
     * Equals and hashCode should be computed by studentGroup, studyPeriod, and weekNr.
     * They are generated automatically.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((studentGroup == null) ? 0 : studentGroup.hashCode());
        result = prime * result + ((studyPeriod == null) ? 0 : studyPeriod.hashCode());
        result = prime * result + ((weekNr == null) ? 0 : weekNr.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudyYearScheduleDto other = (StudyYearScheduleDto) obj;
        if (studentGroup == null) {
            if (other.studentGroup != null)
                return false;
        } else if (!studentGroup.equals(other.studentGroup))
            return false;
        if (studyPeriod == null) {
            if (other.studyPeriod != null)
                return false;
        } else if (!studyPeriod.equals(other.studyPeriod))
            return false;
        if (weekNr == null) {
            if (other.weekNr != null)
                return false;
        } else if (!weekNr.equals(other.weekNr))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStudentGroup() {
        return studentGroup;
    }
    public void setStudentGroup(Long studentGroup) {
        this.studentGroup = studentGroup;
    }
    public Long getStudyYearScheduleLegend() {
        return studyYearScheduleLegend;
    }
    public void setStudyYearScheduleLegend(Long studyYearScheduleLegend) {
        this.studyYearScheduleLegend = studyYearScheduleLegend;
    }
    public Long getStudyPeriod() {
        return studyPeriod;
    }
    public void setStudyPeriod(Long studyPeriod) {
        this.studyPeriod = studyPeriod;
    }
    public Long getWeekNr() {
        return weekNr;
    }
    public void setWeekNr(Long weekNr) {
        this.weekNr = weekNr;
    }
}
