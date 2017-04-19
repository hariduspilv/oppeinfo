package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
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
        dto.setFiles(StreamUtil.toMappedSet(ApplicationFileDto::of, application.getFiles()));
        dto.setPlannedSubjects(StreamUtil.toMappedSet(ApplicationPlannedSubjectDto::of, application.getPlannedSubjects()));
        if (application.getAcademicApplication() != null) {
            dto.setAcademicApplication(ApplicationDto.of(application.getAcademicApplication()));
        }
        dto.setIsAdult(Boolean.valueOf(StudentUtil.isAdult(application.getStudent())));
        return dto;
    }
}
