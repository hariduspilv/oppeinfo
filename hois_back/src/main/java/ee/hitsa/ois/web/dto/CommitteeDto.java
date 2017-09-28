package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

@DateRange
public class CommitteeDto extends VersionedCommand {
    
    private Long id;
    @Size(max=4000)
    private String addInfo;
    @NotNull
    private LocalDate validFrom;
    @NotNull
    private LocalDate validThru;
    @NotEmpty
    private List<CommitteeMemberDto> members;
    
    public static CommitteeDto of(Committee committee) {
        CommitteeDto dto = EntityUtil.bindToDto(committee, new CommitteeDto(), "members");
        dto.setMembers(StreamUtil.toMappedList(CommitteeMemberDto::of, committee.getMembers()));
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
        this.validThru = validThru;
    }

    public List<CommitteeMemberDto> getMembers() {
        return members;
    }

    public void setMembers(List<CommitteeMemberDto> members) {
        this.members = members;
    }
}
