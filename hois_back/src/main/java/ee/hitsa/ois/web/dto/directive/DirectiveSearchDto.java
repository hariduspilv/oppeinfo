package ee.hitsa.ois.web.dto.directive;

import java.time.LocalDate;

import ee.hitsa.ois.domain.directive.Directive;
import ee.hitsa.ois.util.EntityUtil;

public class DirectiveSearchDto {

    private Long id;
    private String headline;
    private String directiveNr;
    private String type;
    private String status;
    private LocalDate inserted;
    private LocalDate confirmDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDirectiveNr() {
        return directiveNr;
    }

    public void setDirectiveNr(String directiveNr) {
        this.directiveNr = directiveNr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public static DirectiveSearchDto of(Directive directive) {
        return EntityUtil.bindToDto(directive, new DirectiveSearchDto());
    }
}
