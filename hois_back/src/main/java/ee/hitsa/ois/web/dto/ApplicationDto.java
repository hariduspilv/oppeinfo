package ee.hitsa.ois.web.dto;

import java.util.Comparator;

import ee.hitsa.ois.domain.application.Application;
import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.enums.ApplicationType;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.StudentUtil;
import ee.hitsa.ois.web.commandobject.ApplicationForm;
import ee.hitsa.ois.web.dto.directive.DirectiveStudentDto;

public class ApplicationDto extends ApplicationForm {

    private Long id;
    private Boolean needsRepresentativeConfirm;
    private Boolean isAdult;
    private DirectiveStudentDto directiveStudent;

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

    public DirectiveStudentDto getDirectiveStudent() {
        return directiveStudent;
    }

    public void setDirectiveStudent(DirectiveStudentDto directiveStudent) {
        this.directiveStudent = directiveStudent;
    }

    public static ApplicationDto of(Application application) {
        if (application == null) {
            return null;
        }

        ApplicationDto dto = EntityUtil.bindToDto(application, new ApplicationDto(), "files", "plannedSubjects", "academicApplication", "directiveStudent");
        dto.setFiles(StreamUtil.toMappedSet(ApplicationFileDto::of, application.getFiles()));
        dto.setPlannedSubjects(StreamUtil.toMappedSet(ApplicationPlannedSubjectDto::of, application.getPlannedSubjects()));
        dto.setAcademicApplication(ApplicationDto.of(application.getAcademicApplication()));

        if (ClassifierUtil.equals(ApplicationType.AVALDUS_LIIK_AKAD, application.getType())) {
            //lets use the last directive student ?
            application.getDirectiveStudents()
                    .stream().max(Comparator.comparingLong(DirectiveStudent::getId))
                    .ifPresent(it -> dto.setDirectiveStudent(DirectiveStudentDto.of(it)));
        }
        dto.setIsAdult(Boolean.valueOf(StudentUtil.isAdult(application.getStudent())));
        return dto;
    }
}
