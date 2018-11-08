package ee.hitsa.ois.web.dto.basemodule;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import ee.hitsa.ois.web.commandobject.basemodule.BaseModuleForm;
import ee.hitsa.ois.web.dto.AutocompleteResult;

public class BaseModuleDto extends BaseModuleForm {
    
    private Long id;
    private Set<AutocompleteResult> curriculums;
    private Map<Long, Set<AutocompleteResult>> curriculumVersions;
    
    public static BaseModuleDto of(BaseModuleForm baseModuleForm) {
        BaseModuleDto dto = new BaseModuleDto();
        BeanUtils.copyProperties(baseModuleForm, dto);
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<AutocompleteResult> getCurriculums() {
        return curriculums;
    }

    public void setCurriculums(Set<AutocompleteResult> curriculums) {
        this.curriculums = curriculums;
    }

    public Map<Long, Set<AutocompleteResult>> getCurriculumVersions() {
        return curriculumVersions;
    }

    public void setCurriculumVersions(Map<Long, Set<AutocompleteResult>> curriculumVersions) {
        this.curriculumVersions = curriculumVersions;
    }
    
}
