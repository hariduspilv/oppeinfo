package ee.hitsa.ois.web.dto.basemodule;

import ee.hitsa.ois.domain.basemodule.BaseModuleTheme;
import ee.hitsa.ois.web.commandobject.BaseModuleThemeForm;

public class BaseModuleThemeDto extends BaseModuleThemeForm {
    
    private Long id;
    
    public static BaseModuleThemeDto ofMin(BaseModuleTheme theme) {
        BaseModuleThemeDto dto = new BaseModuleThemeDto();
        dto.setId(theme.getId());
        dto.setNameEt(theme.getNameEt());
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}
