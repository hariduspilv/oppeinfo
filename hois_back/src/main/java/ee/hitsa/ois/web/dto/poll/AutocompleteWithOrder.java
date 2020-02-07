package ee.hitsa.ois.web.dto.poll;

import ee.hitsa.ois.web.dto.AutocompleteResult;

public class AutocompleteWithOrder extends AutocompleteResult {
    
    public AutocompleteWithOrder(Long id, String nameEt, String nameEn, Long order) {
        super(id, nameEt, nameEn);
        this.order = order;
    }
    
    public AutocompleteWithOrder() {
        
    }
    
    private Long order;

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

}
