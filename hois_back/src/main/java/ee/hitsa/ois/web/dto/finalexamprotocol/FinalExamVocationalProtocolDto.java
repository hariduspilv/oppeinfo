package ee.hitsa.ois.web.dto.finalexamprotocol;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.CommitteeDto;
import ee.hitsa.ois.web.dto.ProtocolVdataDto;

public class FinalExamVocationalProtocolDto extends VersionedCommand {
    
    private Long id;
    private String status;
    private String studyLevel;
    private String protocolNr;
    private LocalDate confirmed;
    private String confirmedBy;
    private LocalDateTime inserted;
    private List<FinalExamVocationalProtocolStudentDto> protocolStudents = new ArrayList<>();
    private ProtocolVdataDto protocolVdata;
    private OisFileViewDto oisFile;
    private LocalDate finalExamDate;
    private CommitteeDto committee;
    private String committeeChairman;
    
    private Boolean canBeEdited;
    private Boolean canBeDeleted;
    
    public static FinalExamVocationalProtocolDto of(Protocol protocol) {
        FinalExamVocationalProtocolDto dto = EntityUtil.bindToDto(protocol, new FinalExamVocationalProtocolDto(), "protocolStudents", "protocolVdata", "studyLevel", "committee");
        dto.setProtocolStudents(StreamUtil.toMappedList(FinalExamVocationalProtocolStudentDto::of, protocol.getProtocolStudents()));
        if (protocol.getCommittee() != null) {
            dto.setCommittee(CommitteeDto.of(protocol.getCommittee()));
            dto.setCommitteeChairman(protocol.getCommittee().getMembers().stream()
                    .filter(member -> Boolean.TRUE.equals(member.getIsChairman())).findFirst().get()
                    .getMemberFullname());
        }
        if (protocol.getProtocolVdata() != null) {
            dto.setProtocolVdata(ProtocolVdataDto.of(protocol.getProtocolVdata()));
            dto.setStudyLevel(EntityUtil.getCode(protocol.getProtocolVdata().getCurriculumVersionOccupationModule().getCurriculumModule().getCurriculum().getOrigStudyLevel()));
        }
        if (protocol.getOisFile() != null) {
            dto.setOisFile(EntityUtil.bindToDto(protocol.getOisFile(), new OisFileViewDto()));
        }
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }
    
    public String getProtocolNr() {
        return protocolNr;
    }
    
    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }
    
    public LocalDate getConfirmed() {
        return confirmed;
    }
    
    public void setConfirmed(LocalDate confirmed) {
        this.confirmed = confirmed;
    }
    
    public String getConfirmedBy() {
        return confirmedBy;
    }
    
    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }
    
    public LocalDateTime getInserted() {
        return inserted;
    }
    
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
    
    public List<FinalExamVocationalProtocolStudentDto> getProtocolStudents() {
        return protocolStudents;
    }
    
    public void setProtocolStudents(List<FinalExamVocationalProtocolStudentDto> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

    public ProtocolVdataDto getProtocolVdata() {
        return protocolVdata;
    }

    public void setProtocolVdata(ProtocolVdataDto protocolVdata) {
        this.protocolVdata = protocolVdata;
    }

    public OisFileViewDto getOisFile() {
        return oisFile;
    }

    public void setOisFile(OisFileViewDto oisFile) {
        this.oisFile = oisFile;
    }
    
    public LocalDate getFinalExamDate() {
        return finalExamDate;
    }

    public void setFinalExamDate(LocalDate finalExamDate) {
        this.finalExamDate = finalExamDate;
    }
    
    public CommitteeDto getCommittee() {
        return committee;
    }

    public void setCommittee(CommitteeDto committee) {
        this.committee = committee;
    }

    public String getCommitteeChairman() {
        return committeeChairman;
    }

    public void setCommitteeChairman(String committeeChairman) {
        this.committeeChairman = committeeChairman;
    }

    public Boolean isCanBeEdited() {
        return canBeEdited;
    }

    public void setCanBeEdited(Boolean canBeEdited) {
        this.canBeEdited = canBeEdited;
    }

    public Boolean isCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }
    
}
