package ee.hitsa.ois.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.CommitteeMember;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.CommitteeMemberValidator.External;
import ee.hitsa.ois.validation.CommitteeMemberValidator.Internal;
import ee.hitsa.ois.web.commandobject.VersionedCommand;


public class CommitteeMemberDto extends VersionedCommand {
    
    private Long id;
    @NotNull
    private Boolean isChairman;
    @NotNull
    private Boolean isExternal;

    @NotNull(groups = {External.class})
    @Size(max=100)
    private String memberName;

    @NotNull(groups = {Internal.class})
    private Long teacher;
    
    public static CommitteeMemberDto of(CommitteeMember member) {
        CommitteeMemberDto dto = EntityUtil.bindToDto(member, new CommitteeMemberDto(), "teacher");
        dto.setTeacher(EntityUtil.getNullableId(member.getTeacher()));
        dto.setMemberName(member.getMemberFullname());
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getIsChairman() {
        return isChairman;
    }
    public void setIsChairman(Boolean isChairman) {
        this.isChairman = isChairman;
    }
    public Boolean getIsExternal() {
        return isExternal;
    }
    public void setIsExternal(Boolean isExternal) {
        this.isExternal = isExternal;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public Long getTeacher() {
        return teacher;
    }
    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }
}
