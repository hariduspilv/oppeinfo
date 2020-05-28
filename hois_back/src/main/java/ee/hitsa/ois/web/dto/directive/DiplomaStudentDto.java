package ee.hitsa.ois.web.dto.directive;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ee.hitsa.ois.domain.directive.DirectiveStudent;
import ee.hitsa.ois.domain.directive.DirectiveStudentDuplicateForm;
import ee.hitsa.ois.util.EntityUtil;

public class DiplomaStudentDto {
    
    private Long student;

    private Long diploma;
    private Long diplomaForm;
    private String diplomaFullCode;
    private Boolean duplicate;

    private Long diplomaSupplement;
    private Set<Long> diplomaSupplementForms;
    private String diplomaSupplementFullCode;
    private Boolean supplementDuplicate;
    private Long diplomaSupplementEn;
    private Set<Long> diplomaSupplementFormsEn;
    private String diplomaSupplementFullCodeEn;
    private Boolean supplementEnDuplicate;

    /**
     * Create DiplomaStudentDto for directive student depending on inserted data
     * 
     * @param ds DirectiveStudent
     * @return DiplomaStudentDto
     */
    public static DiplomaStudentDto fill(DirectiveStudent ds) {
        DiplomaStudentDto dto = new DiplomaStudentDto();
        if (ds.getDiploma() != null) {
            dto.setDiploma(EntityUtil.getId(ds.getDiploma()));
            dto.setDuplicate(ds.getDiploma().getDuplicate());
        }
        if (ds.getDiplomaForm() != null) {
            dto.setDiplomaForm(ds.getDiplomaForm().getId());
            dto.setDiplomaFullCode(ds.getDiplomaForm().getFullCode());
        }

        List<DirectiveStudentDuplicateForm> forms = new ArrayList<>();
        List<DirectiveStudentDuplicateForm> formsEn = new ArrayList<>();
        
        ds.getForms().stream()
                .sorted(Comparator
                        .comparing((DirectiveStudentDuplicateForm dsForm) -> EntityUtil.getNullableCode(dsForm.getForm().getType()),
                                Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(dsForm -> dsForm.getForm().getNumeral(),
                                Comparator.nullsLast(Comparator.naturalOrder())))
                .forEach(f -> (Boolean.TRUE.equals(f.getEn()) ? formsEn : forms).add(f));

        if (ds.getDiplomaSupplement() != null) {
            dto.setDiplomaSupplement(EntityUtil.getId(ds.getDiplomaSupplement()));
            dto.setDiplomaSupplementFullCode(forms.stream().map(dsForm -> dsForm.getForm().getFullCode()).collect(Collectors.joining(", ")));
            dto.setSupplementDuplicate(ds.getDiplomaSupplement().getDuplicate());
        }
        if (ds.getDiplomaSupplementEn() != null) {
            dto.setDiplomaSupplementEn(EntityUtil.getId(ds.getDiplomaSupplementEn()));
            dto.setDiplomaSupplementFullCodeEn(formsEn.stream().map(dsForm -> dsForm.getForm().getFullCode()).collect(Collectors.joining(", ")));
            dto.setSupplementEnDuplicate(ds.getDiplomaSupplementEn().getDuplicateEn());
        }
        return dto;
    }
    
    public void fill(DiplomaStudentDto from) {
        if (this.diploma == null) {
            this.diploma = from.diploma;
            this.duplicate = from.duplicate;
        }
        if (this.diplomaForm == null) {
            this.diplomaForm = from.diplomaForm;
            this.diplomaFullCode = from.diplomaFullCode;
        }
        if (this.diplomaSupplement == null) {
            this.diplomaSupplement = from.diplomaSupplement;
            this.diplomaSupplementFullCode = from.diplomaSupplementFullCode;
            this.supplementDuplicate = from.supplementDuplicate;
        }
        if (this.diplomaSupplementEn == null) {
            this.diplomaSupplementEn = from.diplomaSupplementEn;
            this.diplomaSupplementFullCodeEn = from.diplomaSupplementFullCodeEn;
            this.supplementEnDuplicate = from.supplementEnDuplicate;
        }
    }
    
    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public Long getDiploma() {
        return diploma;
    }

    public void setDiploma(Long diploma) {
        this.diploma = diploma;
    }

    public Long getDiplomaForm() {
        return diplomaForm;
    }

    public void setDiplomaForm(Long diplomaForm) {
        this.diplomaForm = diplomaForm;
    }

    public String getDiplomaFullCode() {
        return diplomaFullCode;
    }

    public void setDiplomaFullCode(String diplomaFullCode) {
        this.diplomaFullCode = diplomaFullCode;
    }

    public Long getDiplomaSupplement() {
        return diplomaSupplement;
    }

    public void setDiplomaSupplement(Long diplomaSupplement) {
        this.diplomaSupplement = diplomaSupplement;
    }

    public Set<Long> getDiplomaSupplementForms() {
        return diplomaSupplementForms;
    }

    public void setDiplomaSupplementForms(Set<Long> diplomaSupplementForms) {
        this.diplomaSupplementForms = diplomaSupplementForms;
    }

    public String getDiplomaSupplementFullCode() {
        return diplomaSupplementFullCode;
    }

    public void setDiplomaSupplementFullCode(String diplomaSupplementFullCode) {
        this.diplomaSupplementFullCode = diplomaSupplementFullCode;
    }

    public Long getDiplomaSupplementEn() {
        return diplomaSupplementEn;
    }

    public void setDiplomaSupplementEn(Long diplomaSupplementEn) {
        this.diplomaSupplementEn = diplomaSupplementEn;
    }

    public Set<Long> getDiplomaSupplementFormsEn() {
        return diplomaSupplementFormsEn;
    }

    public void setDiplomaSupplementFormsEn(Set<Long> diplomaSupplementFormsEn) {
        this.diplomaSupplementFormsEn = diplomaSupplementFormsEn;
    }

    public String getDiplomaSupplementFullCodeEn() {
        return diplomaSupplementFullCodeEn;
    }

    public void setDiplomaSupplementFullCodeEn(String diplomaSupplementFullCodeEn) {
        this.diplomaSupplementFullCodeEn = diplomaSupplementFullCodeEn;
    }

    public Boolean getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Boolean duplicate) {
        this.duplicate = duplicate;
    }

    public Boolean getSupplementDuplicate() {
        return supplementDuplicate;
    }

    public void setSupplementDuplicate(Boolean supplementDuplicate) {
        this.supplementDuplicate = supplementDuplicate;
    }

    public Boolean getSupplementEnDuplicate() {
        return supplementEnDuplicate;
    }

    public void setSupplementEnDuplicate(Boolean supplementEnDuplicate) {
        this.supplementEnDuplicate = supplementEnDuplicate;
    }

}
