package ee.hitsa.ois.web.dto;

import java.util.Set;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.HigherProtocolSaveForm;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;

public class HigherProtocolDto extends HigherProtocolSaveForm {
    private Long id;
    private String protocolNr;
    private String protocolType;
    private String status;
    private SubjectStudyPeriodMidtermTaskDto subjectStudyPeriodMidtermTaskDto;
    private OisFileViewDto oisFile;
    private Boolean canBeEdited;
    private Boolean canBeConfirmed;

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
        dto.setProtocolStudents(StreamUtil.toMappedSet(HigherProtocolStudentDto::of, protocol.getProtocolStudents()));
        return dto;
    }

    public static HigherProtocolDto ofWithUserRights(HoisUserDetails user, Protocol protocol) {
        HigherProtocolDto dto = new HigherProtocolDto();
        dto.setId(EntityUtil.getId(protocol));
        dto.setVersion(protocol.getVersion());
        dto.setProtocolNr(protocol.getProtocolNr());
        dto.setProtocolType(EntityUtil.getCode(protocol.getProtocolHdata().getType()));
        dto.setStatus(EntityUtil.getCode(protocol.getStatus()));

        dto.setProtocolStudents(StreamUtil.toMappedSet(HigherProtocolStudentDto::of, protocol.getProtocolStudents()));

        Set<Long> studetnIds = StreamUtil.toMappedSet(ps -> ps.getStudent().getId(), dto.getProtocolStudents());
        dto.setSubjectStudyPeriodMidtermTaskDto(SubjectStudyPeriodMidtermTaskDto.ofForProtocol(studetnIds,
                protocol.getProtocolHdata().getSubjectStudyPeriod()));

        if (protocol.getOisFile() != null) {
            dto.setOisFile(EntityUtil.bindToDto(protocol.getOisFile(), new OisFileViewDto()));
        }

        dto.setCanBeEdited(Boolean.valueOf(HigherProtocolUtil.canChange(user, protocol)));
        dto.setCanBeConfirmed(Boolean.valueOf(HigherProtocolUtil.canConfirm(user, protocol)));

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProtocolNr() {
        return protocolNr;
    }

    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
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

    public OisFileViewDto getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileViewDto oisFile) {
        this.oisFile = oisFile;
    }

    public Boolean getCanBeEdited() {
        return canBeEdited;
    }

    public void setCanBeEdited(Boolean canBeEdited) {
        this.canBeEdited = canBeEdited;
    }

    public Boolean getCanBeConfirmed() {
        return canBeConfirmed;
    }

    public void setCanBeConfirmed(Boolean canBeConfirmed) {
        this.canBeConfirmed = canBeConfirmed;
    }

}
