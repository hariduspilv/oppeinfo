package ee.hitsa.ois.web.dto;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.DeclarationSubject;
import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
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
    private HigherProtocolModuleDto moduleDto;
    private OisFileViewDto oisFile;
    private Boolean canBeEdited;
    private Boolean canBeConfirmed;
    private Boolean canBeDeleted;
    private String assessmentCode;

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
        dto.setFinalDate(protocol.getFinalDate());

        SubjectStudyPeriod subjectStudyPeriod = protocol.getProtocolHdata().getSubjectStudyPeriod();
        if (subjectStudyPeriod != null) {
            Map<Long, DeclarationSubject> dsByStudentId = protocol.getProtocolHdata().getSubjectStudyPeriod()
                    .getDeclarationSubjects().stream()
                    .collect(Collectors.toMap(ds -> ds.getDeclaration().getStudent().getId(), ds -> ds, (o, n) -> o));
            dto.setProtocolStudents(StreamUtil.toMappedSet(ps -> HigherProtocolStudentDto.ofWithSubgroups(ps,
                    dsByStudentId.get(ps.getStudent().getId())), protocol.getProtocolStudents()));

            Set<Long> studentIds = StreamUtil.toMappedSet(ps -> ps.getStudent().getId(), dto.getProtocolStudents());
            dto.setSubjectStudyPeriodMidtermTaskDto(SubjectStudyPeriodMidtermTaskDto.ofForProtocol(studentIds,
                    subjectStudyPeriod));
        } else {
            dto.setModuleDto(HigherProtocolModuleDto.of(protocol.getProtocolHdata()));
            dto.setProtocolStudents(StreamUtil.toMappedSet(HigherProtocolStudentDto::of, protocol.getProtocolStudents()));
        }

        if (protocol.getOisFile() != null) {
            dto.setOisFile(OisFileViewDto.of(protocol.getOisFile()));
        }

        dto.setCanBeEdited(Boolean.valueOf(HigherProtocolUtil.canChange(user, protocol)));
        dto.setCanBeConfirmed(Boolean.valueOf(HigherProtocolUtil.canConfirm(user, protocol)));
        dto.setCanBeDeleted(Boolean.valueOf(HigherProtocolUtil.canDelete(user, protocol)));

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

    public HigherProtocolModuleDto getModuleDto() {
        return moduleDto;
    }

    public void setModuleDto(HigherProtocolModuleDto moduleDto) {
        this.moduleDto = moduleDto;
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

    public Boolean isCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

}
