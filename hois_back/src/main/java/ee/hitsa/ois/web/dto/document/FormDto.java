package ee.hitsa.ois.web.dto.document;

import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.util.EntityUtil;

public class FormDto {

    private Long id;
    private String type;
    private Long numeral;
    private String fullCode;
    
    public static FormDto of(Form form) {
        FormDto dto = new FormDto();
        dto.setId(EntityUtil.getId(form));
        dto.setType(EntityUtil.getCode(form.getType()));
        dto.setNumeral(form.getNumeral());
        dto.setFullCode(form.getFullCode());
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public Long getNumeral() {
        return numeral;
    }
    public void setNumeral(Long numeral) {
        this.numeral = numeral;
    }
    
    public String getFullCode() {
        return fullCode;
    }
    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }
    
}
