package ee.hitsa.ois.report;

import ee.hitsa.ois.domain.protocol.Protocol;
import ee.hitsa.ois.enums.Language;

public class ModuleProtocolReport {

    public static final String TEMPLATE_NAME = "module.protocol.xhtml";

    public ModuleProtocolReport(Protocol protocol) {
        this(protocol, Language.ET);
    }

    public ModuleProtocolReport(Protocol protocol, Language lang) {
        //TODO
    }

}
