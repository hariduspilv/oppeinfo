package ee.hitsa.ois.web.dto;

import java.time.LocalDate;
import java.util.Set;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.domain.subject.studyperiod.SubjectStudyPeriod;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.HigherProtocolUtil;
import ee.hitsa.ois.util.PersonUtil;
import ee.hitsa.ois.util.StreamUtil;

public class HigherProtocolSearchDto {

    private Long id;
    private String protocolNr;
    private String protocolType;
    private String status;
    private AutocompleteResult subject;
    private Set<String> teachers;
    private LocalDate inserted;
    private LocalDate confirmDate;
    private String confirmer;
    private Boolean canChange;
    

    public static HigherProtocolSearchDto ofWithUserRithts(Protocol protocol, HoisUserDetails user) {
        HigherProtocolSearchDto dto = new HigherProtocolSearchDto();
        EntityUtil.bindToDto(protocol, dto);
        dto.setProtocolType(EntityUtil.getCode(protocol.getProtocolHdata().getType()));
        dto.setInserted(protocol.getInserted().toLocalDate());
        SubjectStudyPeriod subjectStudyPeriod = protocol.getProtocolHdata().getSubjectStudyPeriod();
        dto.setSubject(AutocompleteResult.of(subjectStudyPeriod.getSubject()));
        dto.setTeachers(StreamUtil.toMappedSet(t -> PersonUtil.fullname(t.getTeacher().getPerson()), 
                subjectStudyPeriod.getTeachers()));
        dto.setCanChange(Boolean.valueOf(HigherProtocolUtil.canChange(user, protocol)));
        return dto;
    }
    
    public Boolean getCanChange() {
        return canChange;
    }
    public void setCanChange(Boolean canChange) {
        this.canChange = canChange;
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
    public AutocompleteResult getSubject() {
        return subject;
    }
    public void setSubject(AutocompleteResult subject) {
        this.subject = subject;
    }
    public Set<String> getTeachers() {
        return teachers;
    }
    public void setTeachers(Set<String> teachers) {
        this.teachers = teachers;
    }
    public LocalDate getInserted() {
        return inserted;
    }
    public void setInserted(LocalDate inserted) {
        this.inserted = inserted;
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
}
