package ee.hitsa.ois.web.dto.curriculum;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class CurriculumModuleOutcomeResult extends AutocompleteResult {

    private final Long orderNr;
    
    public CurriculumModuleOutcomeResult(Long id, String nameEt, String nameEn, Long orderNr) {
        super(id, nameEt, nameEn);

        this.orderNr = orderNr;
    }

    public Long getOrderNr() {
        return orderNr;
    }
    
}
