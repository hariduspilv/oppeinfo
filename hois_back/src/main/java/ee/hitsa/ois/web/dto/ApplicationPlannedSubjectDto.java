package ee.hitsa.ois.web.dto;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Size;

import ee.hitsa.ois.domain.application.ApplicationPlannedSubject;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class ApplicationPlannedSubjectDto extends VersionedCommand {

    private Long id;

    @Size(max = 1000)
    private String name;

    private Set<ApplicationPlannedSubjectEquivalentDto> equivalents;

    public static ApplicationPlannedSubjectDto of(ApplicationPlannedSubject plannedSubject) {
        ApplicationPlannedSubjectDto dto = EntityUtil.bindToDto(plannedSubject, new ApplicationPlannedSubjectDto(), "equivalents");
        if (plannedSubject.getEquivalents() != null) {
            dto.setEquivalents(plannedSubject.getEquivalents().stream()
                    .map(ApplicationPlannedSubjectEquivalentDto::of).collect(Collectors.toSet()));
        }
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ApplicationPlannedSubjectEquivalentDto> getEquivalents() {
        return equivalents;
    }

    public void setEquivalents(Set<ApplicationPlannedSubjectEquivalentDto> equivalents) {
        this.equivalents = equivalents;
    }

}
