package ee.hitsa.ois.web.dto.finalexamprotocol;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private LocalDate confirmDate;
    private String confirmer;
    private LocalDateTime inserted;
    private List<FinalExamVocationalProtocolStudentDto> protocolStudents = new ArrayList<>();
    private ProtocolVdataDto protocolVdata;
    private OisFileViewDto oisFile;
    private LocalDate finalDate;
    private CommitteeDto committee;
    private List<Long> presentCommitteeMembers;
    private List<FinalExamVocationalProtocolOccupationDto> occupations = new ArrayList<>();
    private List<String> occupationGrantOccupationCodes = new ArrayList<>();
    
    private Boolean canBeEdited;
    private Boolean canBeDeleted;
    
    public static FinalExamVocationalProtocolDto of(Protocol protocol) {
        FinalExamVocationalProtocolDto dto = EntityUtil.bindToDto(protocol, new FinalExamVocationalProtocolDto(), "protocolStudents", "protocolVdata", "studyLevel", "committee");
        dto.setProtocolStudents(StreamUtil.toMappedList(FinalExamVocationalProtocolStudentDto::of, protocol.getProtocolStudents()));
        if (protocol.getCommittee() != null) {
            dto.setCommittee(CommitteeDto.of(protocol.getCommittee()));
        }
        if (protocol.getProtocolCommitteeMembers() != null) {
            dto.setPresentCommitteeMembers(protocol.getProtocolCommitteeMembers().stream().map(cm -> cm.getCommitteeMember().getId()).collect(Collectors.toList()));
        }
        if (protocol.getProtocolVdata() != null) {
            dto.setProtocolVdata(ProtocolVdataDto.of(protocol.getProtocolVdata()));
            dto.setStudyLevel(EntityUtil.getCode(protocol.getProtocolVdata().getCurriculumVersionOccupationModule().getCurriculumModule().getCurriculum().getOrigStudyLevel()));
            
            protocol.getProtocolVdata().getCurriculumVersionOccupationModule().getCurriculumModule().getOccupations().forEach(oc -> {
                dto.getOccupations().add(new FinalExamVocationalProtocolOccupationDto(oc.getId(),
                        EntityUtil.getCode(oc.getOccupation()), oc.getOccupation().getNameEt(), oc.getOccupation().getNameEn()));
            });
            
            protocol.getProtocolVdata().getCurriculumVersion().getCurriculum().getOccupations().forEach(oc -> {
               if (Boolean.TRUE.equals(oc.getOccupationGrant())) {
                   dto.getOccupationGrantOccupationCodes().add(EntityUtil.getCode(oc.getOccupation()));
                   
                   if (!oc.getOccupation().getClassifierConnects().isEmpty()) {
                       oc.getOccupation().getChildConnects().forEach(occ -> {
                           if (EntityUtil.getCode(occ.getClassifier()).contains("KUTSE")) {
                               dto.getOccupationGrantOccupationCodes().add(EntityUtil.getCode(occ.getClassifier()));
                           }
                       });
                   }
               }
            });
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
    
    public List<String> getOccupationGrantOccupationCodes() {
        return occupationGrantOccupationCodes;
    }

    public void setOccupationGrantOccupationCodes(List<String> occupationGrantOccupationCodes) {
        this.occupationGrantOccupationCodes = occupationGrantOccupationCodes;
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

    public List<FinalExamVocationalProtocolOccupationDto> getOccupations() {
        return occupations;
    }

    public void setOccupations(List<FinalExamVocationalProtocolOccupationDto> occupations) {
        this.occupations = occupations;
    }
}
