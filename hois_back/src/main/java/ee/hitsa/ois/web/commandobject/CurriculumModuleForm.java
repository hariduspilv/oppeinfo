package ee.hitsa.ois.web.commandobject;

import java.util.Set;

import javax.validation.Valid;

import ee.hitsa.ois.domain.curriculum.CurriculumModule;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.dto.curriculum.CurriculumModuleDto;
import ee.hitsa.ois.web.dto.curriculum.CurriculumOccupationDto;

public class CurriculumModuleForm {
    @Valid
    private CurriculumModuleDto curriculumModule;
    @Valid
    private Set<CurriculumOccupationDto> curriculumOccupations;
    
    public static CurriculumModuleForm of(CurriculumModule curriculumModule) {
        CurriculumModuleForm form = new CurriculumModuleForm();
        form.setCurriculumModule(CurriculumModuleDto.of(curriculumModule));
        form.setCurriculumOccupations(StreamUtil.toMappedSet(CurriculumOccupationDto::of, 
                curriculumModule.getCurriculum().getOccupations()));
        return form;
    }

    public CurriculumModuleDto getCurriculumModule() {
        return curriculumModule;
    }

    public void setCurriculumModule(CurriculumModuleDto curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public Set<CurriculumOccupationDto> getCurriculumOccupations() {
        return curriculumOccupations;
    }

    public void setCurriculumOccupations(Set<CurriculumOccupationDto> curriculumOccupations) {
        this.curriculumOccupations = curriculumOccupations;
    }
}
