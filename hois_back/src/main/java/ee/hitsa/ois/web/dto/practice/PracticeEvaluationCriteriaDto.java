package ee.hitsa.ois.web.dto.practice;

import ee.hitsa.ois.domain.enterprise.PracticeEvaluationCriteria;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.VersionedCommand;

public class PracticeEvaluationCriteriaDto extends VersionedCommand {

    private Long id;
    private String nameEt;
    private String addInfo;
    private String type;
    private Long orderNr;
    
    public static PracticeEvaluationCriteriaDto of(PracticeEvaluationCriteria practiceEvaluationCriteria) {
        PracticeEvaluationCriteriaDto dto = new PracticeEvaluationCriteriaDto();
        dto.setId(EntityUtil.getId(practiceEvaluationCriteria));
        dto.setNameEt(practiceEvaluationCriteria.getNameEt());
        dto.setAddInfo(practiceEvaluationCriteria.getAddInfo());
        dto.setType(EntityUtil.getCode(practiceEvaluationCriteria.getType()));
        dto.setOrderNr(practiceEvaluationCriteria.getOrderNr());
        dto.setVersion(practiceEvaluationCriteria.getVersion());
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNameEt() {
        return nameEt;
    }
    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }
    
    public String getAddInfo() {
        return addInfo;
    }
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Long getOrderNr() {
        return orderNr;
    }
    public void setOrderNr(Long orderNr) {
        this.orderNr = orderNr;
    }
    
}
