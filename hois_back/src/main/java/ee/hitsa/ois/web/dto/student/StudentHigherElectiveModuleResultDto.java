package ee.hitsa.ois.web.dto.student;

import ee.hitsa.ois.domain.curriculum.CurriculumVersionElectiveModule;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.dto.curriculum.CurriculumVersionElectiveModuleDto;

public class StudentHigherElectiveModuleResultDto extends CurriculumVersionElectiveModuleDto {

    private Boolean isOk;
    
    public static StudentHigherElectiveModuleResultDto of(CurriculumVersionElectiveModule electiveModule) {
        StudentHigherElectiveModuleResultDto dto = EntityUtil.bindToDto(electiveModule, new StudentHigherElectiveModuleResultDto(),
                "subjects", "referenceNumber");
        return dto;
    }

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }
}
