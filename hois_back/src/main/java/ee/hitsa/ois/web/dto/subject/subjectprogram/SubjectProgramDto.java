package ee.hitsa.ois.web.dto.subject.subjectprogram;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.domain.subject.Subject;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriodTeacher;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramTeacher;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.subject.subjectprogram.SubjectProgramForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class SubjectProgramDto extends SubjectProgramForm {

    private Long id;
    @NotNull
    private List<SubjectProgramTeacherDto> teachers;
    @NotNull
    @ClassifierRestriction(MainClassCode.AINEPROGRAMM_STAATUS)
    private String status;
    private LocalDateTime confirmed;
    private String confirmedBy;
    private Set<Long> supervisorIds;
    private Boolean isLetterGrade;

    private Boolean canEdit;
    private String lockedBy;
    private LocalDateTime locked;

    private AutocompleteResult period;
    
    public static SubjectProgramDto of(SubjectProgram program) {
        SubjectProgramDto dto = EntityUtil.bindToDto(program, new SubjectProgramDto(), "teachers", "studyContents");
        dto.setSubjectStudyPeriodId(EntityUtil.getId(program.getSubjectStudyPeriod()));
        dto.setPeriod(AutocompleteResult.ofWithYear(program.getSubjectStudyPeriod().getStudyPeriod()));
        dto.setTeachers(program.getTeachers().stream()
                .map(SubjectProgramTeacherDto::of)
                .collect(Collectors.toList()));
        dto.setStudyContents(StreamUtil.toMappedList(SubjectProgramStudyContentDto::of, program.getStudyContents()));
        Collections.sort(dto.getStudyContents(), (s1, s2) -> {
            Long orderNr1 = s1.getOrderNr();
            Long orderNr2 = s2.getOrderNr();
            if (orderNr1 != null && orderNr2 != null) {
                return orderNr1.compareTo(orderNr2);
            }
            String weekNr1 = s1.getWeekNr();
            String weekNr2 = s2.getWeekNr();
            if (weekNr1 != null && weekNr2 != null) {
                return weekNr1.compareTo(weekNr2);
            }
            return 0;
        });
        dto.setIsLetterGrade(program.getSubjectStudyPeriod().getSubject().getSchool().getIsLetterGrade());
        return dto;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    public List<SubjectProgramTeacherDto> getTeachers() {
        return teachers != null ? teachers : (teachers = new ArrayList<>());
    }

    public void setTeachers(List<SubjectProgramTeacherDto> teachers) {
        getTeachers().clear();
        getTeachers().addAll(teachers);
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * @return the confirmed
     */
    public LocalDateTime getConfirmed() {
        return confirmed;
    }
    
    /**
     * @param confirmed the confirmed to set
     */
    public void setConfirmed(LocalDateTime confirmed) {
        this.confirmed = confirmed;
    }
    
    /**
     * @return the confirmedBy
     */
    public String getConfirmedBy() {
        return confirmedBy;
    }
    
    /**
     * @param confirmedBy the confirmedBy to set
     */
    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public Set<Long> getSupervisorIds() {
        return supervisorIds;
    }

    public void setSupervisorIds(Set<Long> supervisorIds) {
        this.supervisorIds = supervisorIds;
    }

    public Boolean getIsLetterGrade() {
        return isLetterGrade;
    }

    public void setIsLetterGrade(Boolean isLetterGrade) {
        this.isLetterGrade = isLetterGrade;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    public LocalDateTime getLocked() {
        return locked;
    }

    public void setLocked(LocalDateTime locked) {
        this.locked = locked;
    }

    public AutocompleteResult getPeriod() {
        return period;
    }

    public void setPeriod(AutocompleteResult period) {
        this.period = period;
    }
}
