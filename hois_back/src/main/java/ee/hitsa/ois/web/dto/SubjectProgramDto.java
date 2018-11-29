package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotNull;

import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgram;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.web.commandobject.SubjectProgramForm;

public class SubjectProgramDto extends SubjectProgramForm {

    private Long id;
    @NotNull
    private SubjectStudyPeriodTeacherDto subjectStudyPeriodTeacher;
    @NotNull
    @ClassifierRestriction(MainClassCode.AINEPROGRAMM_STAATUS)
    private String status;
    private LocalDateTime confirmed;
    private String confirmedBy;
    private Set<Long> supervisorIds;
    
    public static SubjectProgramDto of(SubjectProgram program) {
        SubjectProgramDto dto = EntityUtil.bindToDto(program, new SubjectProgramDto(), "subjectStudyPerioudTeacher", "studyContents");
        dto.setSubjectStudyPeriodTeacher(SubjectStudyPeriodTeacherDto.of(program.getSubjectStudyPeriodTeacher()));
        dto.setStudyContents(StreamUtil.toMappedSet(SubjectProgramStudyContentDto::of, program.getStudyContents()));
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
    
    /**
     * @return the subjectStudyPerioudTeacher
     */
    public SubjectStudyPeriodTeacherDto getSubjectStudyPeriodTeacher() {
        return subjectStudyPeriodTeacher;
    }
    
    /**
     * @param subjectStudyPerioudTeacher the subjectStudyPerioudTeacher to set
     */
    public void setSubjectStudyPeriodTeacher(SubjectStudyPeriodTeacherDto subjectStudyPeriodTeacher) {
        this.subjectStudyPeriodTeacher = subjectStudyPeriodTeacher;
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
    
}
