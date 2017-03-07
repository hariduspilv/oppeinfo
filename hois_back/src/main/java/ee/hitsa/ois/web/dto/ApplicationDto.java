package ee.hitsa.ois.web.dto;

import java.util.stream.Collectors;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.ApplicationForm;

public class ApplicationDto extends ApplicationForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static ApplicationDto of(Application application) {
        ApplicationDto dto = EntityUtil.bindToDto(application, new ApplicationDto(), "files");
        if (application.getFiles() != null) {
            dto.setFiles(application.getFiles().stream().map(ApplicationFileDto::of).collect(Collectors.toSet()));
        }
        return dto;
    }

}
