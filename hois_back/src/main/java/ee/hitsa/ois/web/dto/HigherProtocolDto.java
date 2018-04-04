package ee.hitsa.ois.web.dto;

import java.util.Set;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.HigherProtocolSaveForm;

public class HigherProtocolDto extends HigherProtocolSaveForm {
    private Long id;
    private String protocolNr;
    private String protocolType;
    private Boolean canChange;
    private Boolean canConfirm;
    private String status;
    private SubjectStudyPeriodMidtermTaskDto subjectStudyPeriodMidtermTaskDto;
    
    public static HigherProtocolDto ofWithIdOnly(Protocol protocol) {
        HigherProtocolDto dto = new HigherProtocolDto();
        dto.setId(EntityUtil.getId(protocol));
        return dto;
    }
    
    public static HigherProtocolDto ofForMidtermTasksForm(Protocol protocol) {
        HigherProtocolDto dto = new HigherProtocolDto();
        dto.setId(EntityUtil.getId(protocol));
        dto.setProtocolNr(protocol.getProtocolNr());
        dto.setProtocolType(EntityUtil.getCode(protocol.getProtocolHdata().getType()));
        dto.setProtocolStudents(StreamUtil.toMappedSet(HigherProtocolStudentDto::of, 
                protocol.getProtocolStudents()));
        return dto;
    }
    
    public static HigherProtocolDto ofWithUserRights(HoisUserDetails user, Protocol protocol) {
        HigherProtocolDto dto = new HigherProtocolDto();
        dto.setId(EntityUtil.getId(protocol));
        dto.setVersion(protocol.getVersion());
        dto.setProtocolNr(protocol.getProtocolNr());
        dto.setProtocolType(EntityUtil.getCode(protocol.getProtocolHdata().getType()));
        dto.setStatus(EntityUtil.getCode(protocol.getStatus()));
        
        dto.setCanConfirm(Boolean.valueOf(HigherProtocolUtil.canConfirm(user, protocol)));
        dto.setCanChange(Boolean.valueOf(HigherProtocolUtil.canChange(user, protocol)));
        
        dto.setProtocolStudents(StreamUtil.toMappedSet(HigherProtocolStudentDto::of, 
                protocol.getProtocolStudents()));
        
        Set<Long> studetnIds = StreamUtil.toMappedSet(ps -> ps.getStudent().getId(), dto.getProtocolStudents());
        dto.setSubjectStudyPeriodMidtermTaskDto(SubjectStudyPeriodMidtermTaskDto
                .ofForProtocol(studetnIds, protocol.getProtocolHdata().getSubjectStudyPeriod()));
        return dto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubjectStudyPeriodMidtermTaskDto getSubjectStudyPeriodMidtermTaskDto() {
        return subjectStudyPeriodMidtermTaskDto;
    }

    public void setSubjectStudyPeriodMidtermTaskDto(SubjectStudyPeriodMidtermTaskDto subjectStudyPeriodMidtermTaskDto) {
        this.subjectStudyPeriodMidtermTaskDto = subjectStudyPeriodMidtermTaskDto;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCanChange() {
        return canChange;
    }

    public void setCanChange(Boolean canChange) {
        this.canChange = canChange;
    }

    public Boolean getCanConfirm() {
        return canConfirm;
    }

    public void setCanConfirm(Boolean canConfirm) {
        this.canConfirm = canConfirm;
    }

    public String getProtocolNr() {
        return protocolNr;
    }

    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }
}
