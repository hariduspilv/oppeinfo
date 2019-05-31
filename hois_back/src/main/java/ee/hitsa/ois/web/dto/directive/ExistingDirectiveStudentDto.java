package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDate;

import ee.hitsa.ois.domain.directive.DirectiveStudent;

public class ExistingDirectiveStudentDto {

    private Long id;
    private String directiveNr;
    private LocalDate startDate;
    private LocalDate endDate;

    public static ExistingDirectiveStudentDto of(DirectiveStudent directiveStudent) {
        ExistingDirectiveStudentDto dto = new ExistingDirectiveStudentDto();
        dto.setId(directiveStudent.getId());
        dto.setDirectiveNr(directiveStudent.getDirective().getDirectiveNr());
        dto.setStartDate(directiveStudent.getStartDate());
        dto.setEndDate(directiveStudent.getEndDate());
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

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
