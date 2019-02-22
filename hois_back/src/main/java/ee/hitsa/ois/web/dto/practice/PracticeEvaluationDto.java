package ee.hitsa.ois.web.dto.practice;

import ee.hitsa.ois.domain.enterprise.PracticeEvaluation;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.practice.PracticeEvaluationForm;

public class PracticeEvaluationDto extends PracticeEvaluationForm {

    private Long id;
    private Boolean canDelete;
    
    public static PracticeEvaluationDto of(PracticeEvaluation practiceEvaluation) {
        PracticeEvaluationDto dto = new PracticeEvaluationDto();
        dto.setId(EntityUtil.getId(practiceEvaluation));
        dto.setNameEt(practiceEvaluation.getNameEt());
        dto.setAddInfo(practiceEvaluation.getAddInfo());
        dto.setIsActive(practiceEvaluation.getIsActive());
        dto.setTarget(EntityUtil.getCode(practiceEvaluation.getTarget()));
        dto.setCriteria(StreamUtil.toMappedList(PracticeEvaluationCriteriaDto::of, practiceEvaluation.getCriteria()));
        dto.setVersion(practiceEvaluation.getVersion());
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }
    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }
    
}
