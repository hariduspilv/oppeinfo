package ee.hitsa.ois.web.dto;

import java.util.stream.Collectors;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.commandobject.ApplicationForm;

public class ApplicationDto extends ApplicationForm {

    private Long id;
    private Boolean needsRepresentativeConfirm;
    private Boolean isAdult;

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

    public Boolean getIsAdult() {
        return isAdult;
    }

    public void setIsAdult(Boolean isAdult) {
        this.isAdult = isAdult;
    }

    public static ApplicationDto of(Application application) {
        if (application == null) {
            return null;
        }

        ApplicationDto dto = EntityUtil.bindToDto(application, new ApplicationDto(), "files", "plannedSubjects", "academicApplication");
        if (application.getFiles() != null) {
            dto.setFiles(application.getFiles().stream().map(ApplicationFileDto::of).collect(Collectors.toSet()));
        }
        if (application.getPlannedSubjects() != null) {
            dto.setPlannedSubjects(application.getPlannedSubjects().stream()
                    .map(ApplicationPlannedSubjectDto::of).collect(Collectors.toSet()));
        }
        if (application.getAcademicApplication() != null) {
            dto.setAcademicApplication(ApplicationDto.of(application.getAcademicApplication()));
        }
        dto.setIsAdult(Boolean.valueOf(StudentUtil.isAdult(application.getStudent())));
        return dto;
    }

}
