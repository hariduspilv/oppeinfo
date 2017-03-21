package ee.hitsa.ois.web.dto;

import java.util.stream.Collectors;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.ApplicationForm;

public class ApplicationDto extends ApplicationForm {

    private Long id;
    private Boolean needsRepresentativeConfirm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getNeedsRepresentativeConfirm() {
        return needsRepresentativeConfirm;
    }

    public void setNeedsRepresentativeConfirm(Boolean needsRepresentativeConfirm) {
        this.needsRepresentativeConfirm = needsRepresentativeConfirm;
    }

    public static ApplicationDto of(Application application) {
        if (application == null) {
            return null;
        }

        ApplicationDto dto = EntityUtil.bindToDto(application, new ApplicationDto(), "files", "plannedSubjects");
        if (application.getFiles() != null) {
            dto.setFiles(application.getFiles().stream().map(ApplicationFileDto::of).collect(Collectors.toSet()));
        }
        if (application.getPlannedSubjects() != null) {
            dto.setPlannedSubjects(application.getPlannedSubjects().stream()
                    .map(ApplicationPlannedSubjectDto::of).collect(Collectors.toSet()));
        }
        return dto;
    }

}
