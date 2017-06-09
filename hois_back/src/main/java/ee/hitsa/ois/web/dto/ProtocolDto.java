package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class ProtocolDto extends VersionedCommand {

    private Long id;
    private Boolean isVocational;
    private String status;
    private String protocolNr;
    private LocalDate confirmDate;
    private String confirmer;
    private LocalDateTime inserted;
    private List<ProtocolStudentDto> protocolStudents = new ArrayList<>();
    private ProtocolVdataDto protocolVdata;

    public static ProtocolDto of(Protocol protocol) {
        ProtocolDto dto = EntityUtil.bindToDto(protocol, new ProtocolDto(), "protocolStudents", "protocolVdata");
        dto.setProtocolStudents(StreamUtil.toMappedList(ProtocolStudentDto::of, protocol.getProtocolStudents()));
        if (protocol.getProtocolVdata() != null) {
            dto.setProtocolVdata(ProtocolVdataDto.of(protocol.getProtocolVdata()));
        }
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsVocational() {
        return isVocational;
    }

    public void setIsVocational(Boolean isVocational) {
        this.isVocational = isVocational;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<ProtocolStudentDto> getProtocolStudents() {
        return protocolStudents;
    }

    public void setProtocolStudents(List<ProtocolStudentDto> protocolStudents) {
        this.protocolStudents = protocolStudents;
    }

    public ProtocolVdataDto getProtocolVdata() {
        return protocolVdata;
    }

    public void setProtocolVdata(ProtocolVdataDto protocolVdata) {
        this.protocolVdata = protocolVdata;
    }

}
