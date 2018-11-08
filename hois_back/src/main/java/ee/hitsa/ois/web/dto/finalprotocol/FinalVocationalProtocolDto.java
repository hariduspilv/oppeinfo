package ee.hitsa.ois.web.dto.finalprotocol;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.OisFileViewDto;
import ee.hitsa.ois.web.commandobject.VersionedCommand;
import ee.hitsa.ois.web.dto.CommitteeDto;
import ee.hitsa.ois.web.dto.ProtocolVdataDto;

public class FinalVocationalProtocolDto extends VersionedCommand {
    
    private Long id;
    private String status;
    private String studyLevel;
    private String protocolNr;
    private LocalDate confirmDate;
    private String confirmer;
    private LocalDateTime inserted;
    private List<FinalVocationalProtocolStudentDto> protocolStudents = new ArrayList<>();
    private ProtocolVdataDto protocolVdata;
    private OisFileViewDto oisFile;
    private LocalDate finalDate;
    private CommitteeDto committee;
    private List<Long> presentCommitteeMembers;
    private List<FinalProtocolOccupationDto> occupations = new ArrayList<>();
    private Boolean isFinalThesis;
    
    private Boolean canBeEdited;
    private Boolean canBeConfirmed;
    private Boolean canBeDeleted;
    
    public static FinalVocationalProtocolDto of(Protocol protocol) {
        FinalVocationalProtocolDto dto = EntityUtil.bindToDto(protocol, new FinalVocationalProtocolDto(),
                "protocolStudents", "protocolVdata", "studyLevel", "committee", "confirmer");
        dto.setProtocolStudents(StreamUtil.toMappedList(FinalVocationalProtocolStudentDto::of, protocol.getProtocolStudents()));
        if (protocol.getCommittee() != null) {
            dto.setCommittee(CommitteeDto.of(protocol.getCommittee()));
        }
        if (protocol.getProtocolCommitteeMembers() != null) {
            dto.setPresentCommitteeMembers(protocol.getProtocolCommitteeMembers().stream()
                    .map(cm -> cm.getCommitteeMember().getId()).collect(Collectors.toList()));
        }
        if (protocol.getProtocolVdata() != null) {
            dto.setProtocolVdata(ProtocolVdataDto.of(protocol.getProtocolVdata()));
            dto.setStudyLevel(EntityUtil.getCode(protocol.getProtocolVdata().getCurriculumVersionOccupationModule()
                    .getCurriculumModule().getCurriculum().getOrigStudyLevel()));

            protocol.getProtocolVdata().getCurriculumVersion().getCurriculum().getOccupations().forEach(oc -> {
                dto.getOccupations()
                        .add(new FinalProtocolOccupationDto(oc.getId(), EntityUtil.getCode(oc.getOccupation()),
                                oc.getOccupation().getNameEt(), oc.getOccupation().getNameEn(),
                                oc.getOccupationGrant()));
            });
        }
        if (protocol.getOisFile() != null) {
        	dto.setOisFile(OisFileViewDto.of(protocol.getOisFile()));
        }
        dto.setConfirmer(PersonUtil.stripIdcodeFromFullnameAndIdcode(protocol.getConfirmer()));
        
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
    
    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public LocalDateTime getInserted() {
        return inserted;
    }
    
    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
    }
    
    public List<FinalVocationalProtocolStudentDto> getProtocolStudents() {
        return protocolStudents;
    }
    
    public void setProtocolStudents(List<FinalVocationalProtocolStudentDto> protocolStudents) {
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
    
    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public CommitteeDto getCommittee() {
        return committee;
    }

    public void setCommittee(CommitteeDto committee) {
        this.committee = committee;
    }
    
    public List<Long> getPresentCommitteeMembers() {
        return presentCommitteeMembers;
    }

    public void setPresentCommitteeMembers(List<Long> presentCommitteeMembers) {
        this.presentCommitteeMembers = presentCommitteeMembers;
    }
    
    public List<FinalProtocolOccupationDto> getOccupations() {
        return occupations;
    }

    public void setOccupations(List<FinalProtocolOccupationDto> occupations) {
        this.occupations = occupations;
    }
    
    public Boolean getIsFinalThesis() {
        return isFinalThesis;
    }

    public void setIsFinalThesis(Boolean isFinalThesis) {
        this.isFinalThesis = isFinalThesis;
    }

    public Boolean isCanBeEdited() {
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

}
