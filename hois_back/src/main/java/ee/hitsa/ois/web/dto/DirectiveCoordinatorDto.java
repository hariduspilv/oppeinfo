package ee.hitsa.ois.web.dto;

import ee.hitsa.ois.domain.DirectiveCoordinator;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.DirectiveCoordinatorForm;

public class DirectiveCoordinatorDto extends DirectiveCoordinatorForm {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DirectiveCoordinatorDto() {
    }

    public DirectiveCoordinatorDto(Long id, String name, String idcode, Long version) {
        this.id = id;
        setName(name);
        setIdcode(idcode);
        setVersion(version);
    }

    public static DirectiveCoordinatorDto of(DirectiveCoordinator coordinator) {
        return EntityUtil.bindToDto(coordinator, new DirectiveCoordinatorDto());
    }
}
