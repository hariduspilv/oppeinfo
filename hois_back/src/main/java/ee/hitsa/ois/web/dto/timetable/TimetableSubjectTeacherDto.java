package ee.hitsa.ois.web.dto.timetable;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class TimetableSubjectTeacherDto extends AutocompleteResult {

    private Long subjectStudyPeriod;
    private Long subjectStudyPeriodTeacher;
    private TimetableHigherCapacityDto capacity;

    public TimetableSubjectTeacherDto(Long id, String nameEt, String nameEn, Long subjectStudyPeriod,
            Long subjectStudyPeriodTeacher) {
        super(id, nameEt, nameEn);
        this.subjectStudyPeriod = subjectStudyPeriod;
        this.subjectStudyPeriodTeacher = subjectStudyPeriodTeacher;
    }

    public TimetableSubjectTeacherDto(TimetableSubjectTeacherDto dto) {
        super(dto.getId(), dto.getNameEt(), dto.getNameEn());
        this.subjectStudyPeriod = dto.getSubjectStudyPeriod();
        this.subjectStudyPeriodTeacher = dto.getSubjectStudyPeriodTeacher();
    }

    public Long getSubjectStudyPeriod() {
        return subjectStudyPeriod;
    }

    public void setSubjectStudyPeriod(Long subjectStudyPeriod) {
        this.subjectStudyPeriod = subjectStudyPeriod;
    }

    public Long getSubjectStudyPeriodTeacher() {
        return subjectStudyPeriodTeacher;
    }

    public void setSubjectStudyPeriodTeacher(Long subjectStudyPeriodTeacher) {
        this.subjectStudyPeriodTeacher = subjectStudyPeriodTeacher;
    }

    public TimetableHigherCapacityDto getCapacity() {
        return capacity;
    }

    public void setCapacity(TimetableHigherCapacityDto capacity) {
        this.capacity = capacity;
    }

}
