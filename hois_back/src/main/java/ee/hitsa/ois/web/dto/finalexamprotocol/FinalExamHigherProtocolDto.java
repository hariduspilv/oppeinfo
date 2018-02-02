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
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.CommitteeDto;
import ee.hitsa.ois.web.dto.SubjectDto;

public class FinalExamHigherProtocolDto extends VersionedCommand {
    
    private Long id;
    private AutocompleteResult studyPeriod;
    private SubjectDto subject;
    private List<String> teachers;
    private String status;
    private AutocompleteResult studyLevel;
    private String protocolNr;
    private LocalDate confirmed;
    private String confirmedBy;
    private LocalDateTime inserted;
    private List<FinalExamVocationalProtocolStudentDto> protocolStudents = new ArrayList<>();
    private OisFileViewDto oisFile;
    private LocalDate finalExamDate;
    private CommitteeDto committee;
    private String committeeChairman;
    
    private Boolean canBeEdited;
    private Boolean canBeDeleted;
    
    public static FinalExamHigherProtocolDto of(Protocol protocol) {
        FinalExamHigherProtocolDto dto = EntityUtil.bindToDto(protocol, new FinalExamHigherProtocolDto());
        dto.setProtocolStudents(StreamUtil.toMappedList(FinalExamVocationalProtocolStudentDto::of, protocol.getProtocolStudents()));
        if (protocol.getCommittee() != null) {
            dto.setCommittee(CommitteeDto.of(protocol.getCommittee()));
            dto.setCommitteeChairman(protocol.getCommittee().getMembers().stream()
                    .filter(member -> Boolean.TRUE.equals(member.getIsChairman())).findFirst().get()
                    .getMemberFullname());
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

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeacher(List<String> teachers) {
        this.teachers = teachers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AutocompleteResult getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(AutocompleteResult studyLevel) {
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

    public Boolean getCanBeEdited() {
        return canBeEdited;
    }

    public void setCanBeEdited(Boolean canBeEdited) {
        this.canBeEdited = canBeEdited;
    }

    public Boolean getCanBeDeleted() {
        return canBeDeleted;
    }

    public void setCanBeDeleted(Boolean canBeDeleted) {
        this.canBeDeleted = canBeDeleted;
    }
    
}
