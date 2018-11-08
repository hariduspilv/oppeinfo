package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import ee.hitsa.ois.domain.Committee;
import ee.hitsa.ois.domain.CommitteeCurriculum;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.validation.ClassifierRestriction;
import ee.hitsa.ois.validation.DateRange;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

@DateRange
public class CommitteeDto extends VersionedCommand {
    
    private Long id;
    @NotNull
    @ClassifierRestriction(MainClassCode.KOMISJON)
    private String type;
    private String nameEt;
    @Size(max=4000)
    private String addInfo;
    @NotNull
    private LocalDate validFrom;
    @NotNull
    private LocalDate validThru;
    private List<AutocompleteResult> curriculums;
    @NotEmpty
    private List<CommitteeMemberDto> members;
    private Boolean canEdit = Boolean.FALSE;
    private List<CommitteeScholarshipDecisionDto> scholarshipDecisions;
    private List<ApelApplicationDecisionDto> apelApplicationDecisions;
    
    public static CommitteeDto of(Committee committee) {
        CommitteeDto dto = EntityUtil.bindToDto(committee, new CommitteeDto(), "members", "curriculums");
        dto.setMembers(StreamUtil.toMappedList(CommitteeMemberDto::of, committee.getMembers()));
        dto.setCurriculums(StreamUtil.toMappedList(AutocompleteResult::of, committee.getCurriculums()
                .stream().map(CommitteeCurriculum::getCurriculum)));
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
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

    public List<AutocompleteResult> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(List<AutocompleteResult> curriculums) {
        this.curriculums = curriculums;
    }

    public List<CommitteeMemberDto> getMembers() {
        return members;
    }

    public void setMembers(List<CommitteeMemberDto> members) {
        this.members = members;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public List<CommitteeScholarshipDecisionDto> getScholarshipDecisions() {
        return scholarshipDecisions;
    }

    public void setScholarshipDecisions(List<CommitteeScholarshipDecisionDto> scholarshipDecisions) {
        this.scholarshipDecisions = scholarshipDecisions;
    }

    public List<ApelApplicationDecisionDto> getApelApplicationDecisions() {
        return apelApplicationDecisions;
    }

    public void setApelApplicationDecisions(List<ApelApplicationDecisionDto> apelApplicationDecisions) {
        this.apelApplicationDecisions = apelApplicationDecisions;
    }

}
