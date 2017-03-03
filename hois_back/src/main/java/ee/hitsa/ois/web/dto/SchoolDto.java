package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.OisFileCommand;
import ee.hitsa.ois.web.commandobject.SchoolForm;

public class SchoolDto extends SchoolForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static SchoolDto of(School school) {
        return EntityUtil.bindToDto(school, new SchoolDto());
    }

    public static SchoolDto ofWithLogo(School school) {
        SchoolDto dto = of(school);
        OisFile logo = school.getLogo();
        if(logo != null) {
            dto.setLogo(EntityUtil.bindToDto(logo, new OisFileCommand()));
        }
        return dto;
    }
}
