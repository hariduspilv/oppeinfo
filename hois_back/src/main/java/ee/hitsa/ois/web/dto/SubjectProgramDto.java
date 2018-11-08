package ee.hitsa.ois.web.dto;

import java.time.LocalDateTime;

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
    private SubjectStudyPeriodTeacherDto subjectStudyPerioudTeacher;
    @NotNull
    @ClassifierRestriction(MainClassCode.AINEPROGRAMM_STAATUS)
    private String status;
    private LocalDateTime confirmed;
    private String confirmedBy;
    
    public static SubjectProgramDto of(SubjectProgram program) {
        SubjectProgramDto dto = EntityUtil.bindToDto(program, new SubjectProgramDto(), "subjectStudyPerioudTeacher", "studyContents");
        dto.setSubjectStudyPerioudTeacher(SubjectStudyPeriodTeacherDto.of(program.getSubjectStudyPeriodTeacher()));
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
    public SubjectStudyPeriodTeacherDto getSubjectStudyPerioudTeacher() {
        return subjectStudyPerioudTeacher;
    }
    
    /**
     * @param subjectStudyPerioudTeacher the subjectStudyPerioudTeacher to set
     */
    public void setSubjectStudyPerioudTeacher(SubjectStudyPeriodTeacherDto subjectStudyPerioudTeacher) {
        this.subjectStudyPerioudTeacher = subjectStudyPerioudTeacher;
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
}
