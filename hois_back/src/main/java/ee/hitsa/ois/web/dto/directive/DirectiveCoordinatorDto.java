package ee.hitsa.ois.web.dto.directive;

import ee.hitsa.ois.domain.directive.DirectiveCoordinator;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.directive.DirectiveCoordinatorForm;

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

    public DirectiveCoordinatorDto(Long id, String name, String idcode, Long version, Boolean isDirective, Boolean isCertificate, Boolean isCertificateDefault) {
        this.id = id;
        setName(name);
        setIdcode(idcode);
        setVersion(version);
        setIsDirective(isDirective);
        setIsCertificate(isCertificate);
        setIsCertificateDefault(isCertificateDefault);
    }

    public static DirectiveCoordinatorDto of(DirectiveCoordinator coordinator) {
        return EntityUtil.bindToDto(coordinator, new DirectiveCoordinatorDto());
    }
}
