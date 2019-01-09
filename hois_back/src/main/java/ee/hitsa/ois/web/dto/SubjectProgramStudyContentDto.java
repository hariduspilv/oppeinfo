package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramStudyContent;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class SubjectProgramStudyContentDto extends VersionedCommand {

    private Long id;
    private Long subjectProgramId;
    private Short weekNr;
    private LocalDate studyDt;
    @NotBlank
    private String studyInfo;
    
    public static SubjectProgramStudyContentDto of(SubjectProgramStudyContent content) {
        SubjectProgramStudyContentDto dto = new SubjectProgramStudyContentDto();
        dto.setId(content.getId());
        dto.setSubjectProgramId(EntityUtil.getId(content.getSubjectProgram()));
        dto.setWeekNr(content.getWeekNr());
        dto.setStudyDt(content.getStudyDt());
        dto.setStudyInfo(content.getStudyInfo());
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSubjectProgramId() {
        return subjectProgramId;
    }
    public void setSubjectProgramId(Long subjectProgramId) {
        this.subjectProgramId = subjectProgramId;
    }
    public Short getWeekNr() {
        return weekNr;
    }
    public void setWeekNr(Short weekNr) {
        this.weekNr = weekNr;
    }
    public LocalDate getStudyDt() {
        return studyDt;
    }
    public void setStudyDt(LocalDate studyDt) {
        this.studyDt = studyDt;
    }
    public String getStudyInfo() {
        return studyInfo;
    }
    public void setStudyInfo(String studyInfo) {
        this.studyInfo = studyInfo;
    }
}
