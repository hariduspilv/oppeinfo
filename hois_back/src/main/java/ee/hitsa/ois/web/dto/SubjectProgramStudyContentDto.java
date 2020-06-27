package ee.hitsa.ois.web.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.subject.subjectprogram.SubjectProgramStudyContent;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class SubjectProgramStudyContentDto extends VersionedCommand {

    private Long id;
    private Long subjectProgramId;
    private String weekNr;
    private LocalDate studyDt;
    @NotBlank
    private String studyInfo;
    private AutocompleteResult teacher;
    private String capacity;
    private Long orderNr;
    private String capacityType;
    
    
    public static SubjectProgramStudyContentDto of(SubjectProgramStudyContent content) {
        SubjectProgramStudyContentDto dto = new SubjectProgramStudyContentDto();
        dto.setSubjectProgramId(EntityUtil.getId(content.getSubjectProgram()));
        return EntityUtil.bindToDto(content, dto);
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
    public String getWeekNr() {
        return weekNr;
    }
    public void setWeekNr(String weekNr) {
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Long getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
    }

    public String getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(String capacityType) {
        this.capacityType = capacityType;
    }

    public AutocompleteResult getTeacher() {
        return teacher;
    }

    public void setTeacher(AutocompleteResult teacher) {
        this.teacher = teacher;
    }
}
