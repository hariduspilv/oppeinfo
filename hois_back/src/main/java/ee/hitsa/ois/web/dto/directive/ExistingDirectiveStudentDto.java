package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDate;

import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.scholarship.ScholarshipApplication;
import ee.hitsa.ois.util.DateUtils;

public class ExistingDirectiveStudentDto {

    private Long id;
    private String directiveNr;
    private Long scholarshipApplicationId;
    private String scholarshipTermNameEt;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate confirmDate;
    private Long applicationId;

    public static ExistingDirectiveStudentDto of(DirectiveStudent directiveStudent) {
        ExistingDirectiveStudentDto dto = new ExistingDirectiveStudentDto();
        dto.setId(directiveStudent.getId());
        dto.setDirectiveNr(directiveStudent.getDirective().getDirectiveNr());

        ScholarshipApplication application = directiveStudent.getScholarshipApplication();
        if (application != null) {
            dto.setScholarshipApplicationId(application.getId());
            dto.setScholarshipTermNameEt(application.getScholarshipTerm().getNameEt());
        } else {
            dto.setScholarshipTermNameEt("Muu - " + directiveStudent.getDirective().getScholarshipEhis().getNameEt());
        }
        dto.setStartDate(DateUtils.periodStart(directiveStudent));
        dto.setEndDate(DateUtils.periodEnd(directiveStudent));
        dto.setConfirmDate(directiveStudent.getDirective().getConfirmDate());
        return dto; 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDirectiveNr() {
        return directiveNr;
    }

    public void setDirectiveNr(String directiveNr) {
        this.directiveNr = directiveNr;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Long getScholarshipApplicationId() {
        return scholarshipApplicationId;
    }

    public void setScholarshipApplicationId(Long scholarshipApplicationId) {
        this.scholarshipApplicationId = scholarshipApplicationId;
    }

    public String getScholarshipTermNameEt() {
        return scholarshipTermNameEt;
    }

    public void setScholarshipTermNameEt(String scholarshipTermNameEt) {
        this.scholarshipTermNameEt = scholarshipTermNameEt;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

}
