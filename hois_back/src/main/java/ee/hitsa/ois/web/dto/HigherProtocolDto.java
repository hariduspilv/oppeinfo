package ee.hitsa.ois.web.dto;

import java.util.Set;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class HigherProtocolDto extends VersionedCommand {
    private Long id;
    private String protocolNr;
    private AutocompleteResult subject;
    private AutocompleteResult studyPeriod;
    private Set<String> teachers;
    
    private Boolean canChange;
    private Boolean canConfirm;
    
    public static HigherProtocolDto ofWithUserRights(HoisUserDetails user, Protocol protocol) {
        HigherProtocolDto dto = new HigherProtocolDto();
        dto.setId(EntityUtil.getId(protocol));
        dto.setVersion(protocol.getVersion());
        dto.setProtocolNr(protocol.getProtocolNr());
        
        SubjectStudyPeriod subjectStudyPeriod = protocol.getProtocolHdata().getSubjectStudyPeriod();
        dto.setSubject(AutocompleteResult.of(subjectStudyPeriod.getSubject()));
        dto.setStudyPeriod(AutocompleteResult.of(subjectStudyPeriod.getStudyPeriod()));
        dto.setTeachers(StreamUtil.toMappedSet(t -> PersonUtil.fullname(t.getTeacher().getPerson()), 
                subjectStudyPeriod.getTeachers()));
        
        dto.setCanConfirm(HigherProtocolUtil.canConfirm(user, protocol));
        dto.setCanChange(HigherProtocolUtil.canChange(user, protocol));
        return dto;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCanChange() {
        return canChange;
    }

    public void setCanChange(Boolean canChange) {
        this.canChange = canChange;
    }

    public Boolean getCanConfirm() {
        return canConfirm;
    }

    public void setCanConfirm(Boolean canConfirm) {
        this.canConfirm = canConfirm;
    }

    public String getProtocolNr() {
        return protocolNr;
    }

    public void setProtocolNr(String protocolNr) {
        this.protocolNr = protocolNr;
    }

    public AutocompleteResult getSubject() {
        return subject;
    }

    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }

    public AutocompleteResult getStudyPeriod() {
        return studyPeriod;
    }

    public void setStudyPeriod(AutocompleteResult studyPeriod) {
        this.studyPeriod = studyPeriod;
    }

    public Set<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<String> teachers) {
        this.teachers = teachers;
    }
}
