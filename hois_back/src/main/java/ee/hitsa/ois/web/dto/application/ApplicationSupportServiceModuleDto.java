package ee.hitsa.ois.web.dto.application;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import ee.hitsa.ois.domain.application.ApplicationSupportServiceModule;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class ApplicationSupportServiceModuleDto {
    
    private Long id;
    @NotNull
    private AutocompleteResult module;
    @NotBlank
    @Size(max=4000)
    private String addInfo;
    
    public static ApplicationSupportServiceModuleDto of(ApplicationSupportServiceModule serviceModule) {
        ApplicationSupportServiceModuleDto dto = new ApplicationSupportServiceModuleDto();
        dto.setId(serviceModule.getId());
        dto.setModule(AutocompleteResult.of(serviceModule.getModule(), false));
        dto.setAddInfo(serviceModule.getAddInfo());
        return dto;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public AutocompleteResult getModule() {
        return module;
    }
    
    public void setModule(AutocompleteResult module) {
        this.module = module;
    }
    
    public String getAddInfo() {
        return addInfo;
    }
    
    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
    
}
