package ee.hitsa.ois.web.commandobject;

import ee.hitsa.ois.util.Versioned;

public class VersionedCommand implements Versioned {

    private Long version;

    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
