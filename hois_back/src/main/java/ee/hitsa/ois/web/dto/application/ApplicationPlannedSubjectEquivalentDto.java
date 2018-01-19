package ee.hitsa.ois.web.dto.application;

import ee.hitsa.ois.domain.application.ApplicationPlannedSubjectEquivalent;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class ApplicationPlannedSubjectEquivalentDto extends VersionedCommand {

    private Long id;
    private Long subject;

    public static ApplicationPlannedSubjectEquivalentDto of(ApplicationPlannedSubjectEquivalent equivalent) {
        ApplicationPlannedSubjectEquivalentDto dto = EntityUtil.bindToDto(equivalent, new ApplicationPlannedSubjectEquivalentDto(), "subject");
        dto.setSubject(EntityUtil.getId(equivalent.getSubject()));
        return dto;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSubject() {
        return subject;
    }
    public void setSubject(Long subject) {
        this.subject = subject;
    }

}
